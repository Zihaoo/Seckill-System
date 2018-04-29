package org.seckill.exception;

/**
 * 重复秒杀异常
 * 不会运行重复秒杀
 */
public class RepeatKilException extends SeckillException {
    public RepeatKilException(String message) {
        super(message);
    }

    public RepeatKilException(String message, Throwable cause) {
        super(message, cause);
    }
}
