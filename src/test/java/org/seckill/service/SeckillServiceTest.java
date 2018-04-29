package org.seckill.service;

import jdk.Exported;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKilException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.model.Seckill;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
//service依赖dao,所以加两个
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={ }", list);
    }

    //    @Test
//    public void getById() throws Exception {
//        long id = 1000;
//        Seckill seckill = seckillService.getById(id);
//        logger.info("seckill={}",seckill);
//    }
    @Test
    public void getById() throws Exception {

        long seckillId = 1000;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}", seckill);
    }

    //测试代码完整逻辑,先给地址->执行秒杀,注意可重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 232446522L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            }catch (RepeatKilException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }

        } else {
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }
//        System.out.println(exposer);

        //{exposed=true, md5='bf204e2683e7452aa7db1a50b5713bae', seckillId=1000, now=0, start=0, end=0}
    }


}