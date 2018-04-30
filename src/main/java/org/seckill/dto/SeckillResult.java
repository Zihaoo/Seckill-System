package org.seckill.dto;

/**
 * 分装json结果
 * @param <T>
 */
//所有ajax请求放回的类型,分装json结果
public class SeckillResult<T> {

    private boolean success;

    private T data;
    private String error;
    //成
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
    //败
    public SeckillResult(boolean success, T data) {

        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
