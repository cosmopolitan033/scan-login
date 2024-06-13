package cn.ghl.scan.types.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义运行时异常
 * @Author GengHongLiang
 * @Date 2024/5/30 9:13
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 8653090271840061986L;

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String info;

    public AppException(String code) {
        this.code = code;
    }

    public AppException(String code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public AppException(String code, String message) {
        this.code = code;
        this.info = message;
    }

    public AppException(String code, String message, Throwable cause) {
        this.code = code;
        this.info = message;
        super.initCause(cause);
    }

}
