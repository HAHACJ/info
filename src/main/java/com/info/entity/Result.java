package com.info.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;


/**
 * 统一信息返回类
 *
 * @param <T>
 */
@Getter
@Setter
@ToString
public class Result<T> implements Serializable {

    /**
     * 返回数据
     */
    private T data;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回描述
     */
    private String msg;

    public Result() {
    }

    /**
     * @param code 状态码
     * @param msg  信息
     * @param data 数据
     */
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回结果对象
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public Result<T> responseResult(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
