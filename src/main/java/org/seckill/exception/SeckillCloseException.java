package org.seckill.exception;

/**
 * 秒杀关闭异常,关闭后,不许在秒杀
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
