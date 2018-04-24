package org.seckill.mapper;

import org.seckill.model.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillMapper {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return  如果影响行数>1,表示跟新的记录行数
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offet,int limit);


}
