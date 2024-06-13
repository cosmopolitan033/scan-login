package cn.ghl.scan.service.impl;

import cn.ghl.scan.dto.req.WeixinQrCodeRequestDTO;
import cn.ghl.scan.dto.res.WeixinQrCodeResponseDTO;
import cn.ghl.scan.dto.res.WeixinTokenResponseDTO;
import cn.ghl.scan.service.ILoginService;
import cn.ghl.scan.service.api.IWxApiService;
import cn.ghl.scan.types.exception.AppException;
import com.google.common.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;

/**
 *
 * @Author GengHongLiang
 * @Date 2024/5/30 10:08
 * @Version 1.0
 */
@Service
public class WxLoginService implements ILoginService {
    // appID
    @Value("${weixin.config.app-id}")
    private String appid;
    // 公众号配置
    @Value("${weixin.config.app-secret}")
    private String appSecret;

    // 缓存 AccessToken
    @Resource
    private Cache<String, String> wxAccessToken;

    // 注入wx API
    @Resource
    private IWxApiService wxApiService;

    @Resource
    private Cache<String, String> openidToken;

    /**
     * 获取ticket
     * 1、access_token 是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存 ，--- 摘自官方文档
     *      既然access_token是全局都需要 我们就不能放到某个具体的Service中 需要单独管理起来
     * @return
     */
    @Override
    public String createQrCodeTicket() {
        try{
            // 1. 获取 accessToken
            String accessToken = wxAccessToken.getIfPresent(appid);
            if(StringUtils.isEmpty(accessToken)){
                // 远程调用微信接口 获取AccessToken
                Call<WeixinTokenResponseDTO> call = wxApiService.getAccessToken("client_credential", appid, appSecret);
                WeixinTokenResponseDTO weixinTokenResponseDTO = call.execute().body();
                accessToken = weixinTokenResponseDTO.getAccess_token();
                // accessToken  针对整个公众号生效  所以采用 appId 作为唯一标识
                wxAccessToken.put(appid, accessToken);
            }

            // 2. 生成 ticket 请求参数封装
            // 文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
            // 生成带参数的二维码 有两种 临时二维码(30天) 和永久二维码  我们这里使用的是 临时二维码
            WeixinQrCodeRequestDTO request = WeixinQrCodeRequestDTO.builder()
                    .expire_seconds(2592000) // 过期时间单位为秒 2592000 = 30天
                    .action_name(WeixinQrCodeRequestDTO.ActionNameTypeVO.QR_SCENE.getCode())
                    .action_info(WeixinQrCodeRequestDTO.ActionInfo.builder()
                            .scene(WeixinQrCodeRequestDTO.ActionInfo.Scene.builder()
                                    // 用户扫描带场景值二维码时，可能推送以下两种事件
                                    // 每次创建二维码ticket需要提供一个开发者自行设定的参数（scene_id），分别介绍临时二维码和永久二维码的创建二维码ticket过程。
                                    .scene_id(1014) // 场景ID
//                                     .scene_str("test")  // 配合 ActionNameTypeVO.QR_STR_SCENE
                                    .build())
                            .build())
                    .build();
            // 3. 发送请求 获取ticket
            Call<WeixinQrCodeResponseDTO> qrCodeCall = wxApiService.createQrCode(accessToken, request);
            WeixinQrCodeResponseDTO weixinQrCodeResponseDTO = qrCodeCall.execute().body();
            return weixinQrCodeResponseDTO.getTicket();

        } catch (Exception e){
            e.printStackTrace();
            throw new AppException(e.getMessage());
        }

    }

    @Override
    public String checkLogin(String ticket) {
        // 通过 ticket 判断，用户是否登录。如果登录了，会在内存里写入信息。
        return openidToken.getIfPresent(ticket);
    }
}
