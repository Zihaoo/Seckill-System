package org.seckill.mapper.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.mapper.SeckillMapper;
import org.seckill.model.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private long id = 1001;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillMapper seckillMapper;

    @Test
    public void getSeckill() {
        // get and put
        Seckill seckill = redisDao.getSeckill(id);
        if(seckill != null) {
            String result = redisDao.putSeckill(seckill);
            System.out.println(result);
            seckill = redisDao.getSeckill(id);
            System.out.println(seckill);
        }
    }
    //说明
    //如果测试通过了，会输出result={}OK以及id为1001的商品信息，
    // 如果输出的都是null，那说明你没有开启Redis服务器，所以在内存中没有存取到缓存。


}