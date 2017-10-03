package org.skynet.web.Model;

/**
 * 返回结果
 */
public class Result<T> implements ResultCode {
    // 结果对象
    private T data;
    // 状态码
    private Integer code;
    // 信息
    private String message;

    private Result(Integer code) {
        this.code = code;
    }

    private static Result create(Integer code) {
        return new Result(code);
    }

    public static Result createSuccessResult() {
        return create(ResultCode.SUCCESS);
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}
