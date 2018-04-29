package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKilException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.model.Seckill;

import java.util.List;

/**
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 *
 */
public interface SeckillService {

    //查询所有秒杀记录
    List<Seckill> getSeckillList();

    //查询单个秒杀记录
    Seckill getById(long seckillId);

    //秒杀开启时输出秒杀地址,否则输出系统时间和秒杀时间
    Exposer exportSeckillUrl(long seckillId);


    //执行秒杀操作
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKilException,SeckillCloseException;

}
