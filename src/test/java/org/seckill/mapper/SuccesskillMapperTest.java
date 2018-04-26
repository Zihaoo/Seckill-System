package org.seckill.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.model.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccesskillMapperTest {

    @Resource
    private SuccesskillMapper successkillMapper;

    @Test
    public void insertSuccessKilled() {
        long id = 1000L;
        long phone = 34131241L;
        int count = successkillMapper.insertSuccessKilled(id, phone);
        System.out.println("count:" + count);
    }

    @Test
    public void queryByIdWithSeckill() {
        long id = 1000L;
        long phone = 34131241L;

        SuccessKilled successKilled = successkillMapper.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
