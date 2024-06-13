package cn.ghl.scan.dto.res;

import lombok.Data;

/**
 * 获取AccessToken 接口返回的结果封装
 * @Author GengHongLiang
 * @Date 2024/5/30 10:47
 * @Version 1.0
 */@Data
public class WeixinTokenResponseDTO {
    // AccessToken
    private String access_token;
    // 过期时间
    private int expires_in;
    // 响应码
    private String errcode;
    // 响应信息
    private String errmsg;
}
