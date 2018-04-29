package org.seckill.service.impl;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKilException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.mapper.SeckillMapper;
import org.seckill.mapper.SuccesskillMapper;
import org.seckill.model.Seckill;
import org.seckill.model.SuccessKilled;
import org.seckill.service.SeckillService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

//@Component @Service @Dao @controller
@Service
public class SeckillServiceImpl implements SeckillService {


    private  Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SuccesskillMapper successkillMapper;

    //md5盐字符,混淆
    private final String salt = "shsdssljdd'l.";


    //查询所有秒杀记录
    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0, 4);
    }

    //查询单个
    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    //秒杀开始输出地址
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillMapper.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);

    }

    private String getMD5(long seckllId) {
        String base = seckllId + "/" + salt ;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    //执行秒杀方法
    @Transactional
    /**
     * 使用注解控制事务的优点: 1.明确标注事务方法的编程风格 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作.RPC/HTTP
     * 3.不是所有方法需要事务,只有一条修改操作,只读操作不需要事务控制.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKilException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //秒杀逻辑:减库存 + 记录购买行为
        Date nowTime = new Date();
        //减库存
        int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);

        try {
            if (updateCount <= 0) {
                //没有跟新记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                // 记录购买行为
                int insertCount = successkillMapper.insertSuccessKilled(seckillId, userPhone);
                //唯一:seckill,userPhone
                if (insertCount <= 0) {
                    throw new RepeatKilException("seckill repated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successkillMapper.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);

                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKilException e2) {
            throw e2;
        } catch (Exception e) {
            //所有编译期异常,转换为运行异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }


    }
}
