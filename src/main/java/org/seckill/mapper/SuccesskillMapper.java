package org.seckill.mapper;

import org.seckill.model.SuccessKilled;

public interface SuccesskillMapper {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     *  返回插入的行数
     */
    int insertSuccessKilled(long seckillId,long userPhone);

    /**
     * 根据id查询SucessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);

}
