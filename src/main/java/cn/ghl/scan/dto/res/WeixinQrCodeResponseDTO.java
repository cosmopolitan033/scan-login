package cn.ghl.scan.dto.res;

import lombok.Data;

/**
 * @Author GengHongLiang
 * @Date 2024/5/30 10:56
 * @Version 1.0
 */
@Data
public class WeixinQrCodeResponseDTO {
    // 响应码
    private String errcode;
    // 响应信息
    private String errmsg;
    //获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
    private String ticket;
    // 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
    private Long expire_seconds;
    // 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
    private String url;
}
