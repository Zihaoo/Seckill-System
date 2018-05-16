###开发化境

IDEA+SSM+Maven

#####启动
1.下载,从Maven中打开导入IDE.
2.打开jdbc.properties文件，修改属性
3.部署到Tomcat启动
4.在浏览器上访问：http://localhost:8080/seckill/list   
----

###业务介绍

>核心功能

* 秒杀接口暴露
* 执行秒杀
* 相关查询

>秒杀业务的核心逻辑是对库存处理

* 商家-->添加商品加库存
* 用户-->秒杀减库存
>针对库存分析

**当用户秒杀时,库存做两件事**
1. 减商品库存
2. 添加一条购买明细

>如何保证数据一致性(多用户并发)

**事务+行级锁操作**
start transaction（开启事务）→ update库存数量 → insert购买明细 → commit（提交事务）
----
###前端控制逻辑
![前端控制流程](http://upload-images.jianshu.io/upload_images/2155796-181404e42446fc0e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###SpringMVC运行流程
![](https://upload-images.jianshu.io/upload_images/2155796-04839abc19841dfd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



----
###5.12一些优化改动
1. **使用redis对暴露接口这一热点事件进行优化**
分析:地址暴露接口是根据秒杀单的时间来计算是否开启秒杀、是否在秒杀中、是否结束秒杀。通过服务器端的逻辑去控制秒杀地址，并且暴露地址接口频繁，不希望客户端频繁的访问数据库，所以用Redis去优化地址暴露接口。Java访问Redis的客户端，seckillId设置Redis键，秒杀的对象设置为Redis值，用Google的Protostaff实现内部序列化，比原生的序列化压缩空间和压缩速度都有很大提升，尽可能的降低网络延迟。

2. **执行秒杀降低行级锁的占有时间，先insert购买明细，再update减库存**
分析:Update减库存操作，当开启一个事务的时候，通过主键拿到行级锁，需要返回到客户端，这期间有网络延迟或者GC操作。 insert购买明细也会有网络延迟和GC，最后才commit/rollback事务，释放行级锁，这对于库存秒杀单来说，是一个阻塞状态。 MySQL和Java在本地执行时速度很快，瓶颈主要出现在网络延迟和GC操作上。








