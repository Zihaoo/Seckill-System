package org.seckill.mapper;
import org.seckill.model.Seckill;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合,junit启动时记载springioc容器
 */
//@RunWith(JUnit4ClassRunner.class)    *这个已经不再支持
//告诉junit spring的配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillMapperTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillMapper seckillMapper;

    @Test
    public void queryById() throws Exception {
        long id = 1001;
        Seckill seckill = seckillMapper.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    /**
     * java.lang.NullPointerException
     * 	at org.seckill.mapper.SeckillMapperTest.queryAll(SeckillMapperTest.java:42)
     * @throws Exception
     */
    @Test
    public void queryAll() throws Exception {

        List<Seckill> seckills = seckillMapper.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {

        long seckillId = 1000;
        Date date = new Date();
        int updateCount = seckillMapper.reduceNumber(seckillId, date);
        System.out.println(updateCount);
    }
}
