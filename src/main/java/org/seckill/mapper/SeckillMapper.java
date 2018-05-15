package org.seckill.mapper;

import org.apache.ibatis.annotations.Param;
import org.seckill.model.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillMapper {

    /**
     * 减库存
     * @param seckillId
     * @param killTime  秒杀执行时间
     * @return  如果影响行数>1,表示跟新的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);


}
