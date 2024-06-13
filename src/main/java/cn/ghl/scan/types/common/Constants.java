package cn.ghl.scan.types.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 通用枚举值 及常量
 * @Author GengHongLiang
 * @Date 2024/5/30 9:12
 * @Version 1.0
 */
public class Constants {
    public final static String SPLIT = ",";

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ResponseCode {
        SUCCESS("0000", "调用成功"),
        UN_ERROR("0001", "调用失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        NO_LOGIN("0002", "未登录"),
        ;

        private String code;
        private String info;

    }
}
