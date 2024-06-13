package cn.ghl.scan.service.api;

import cn.ghl.scan.dto.req.WeixinQrCodeRequestDTO;
import cn.ghl.scan.dto.res.WeixinQrCodeResponseDTO;
import cn.ghl.scan.dto.res.WeixinTokenResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 微信API服务 调用微信公众接口  不需要实现类 直接使用 Retrofit 基于接口的封装来发送请求
 * @Author GengHongLiang
 * @Date 2024/5/30 10:46
 * @Version 1.0
 */
public interface IWxApiService {

    /**
     * 获取 Access token
     * 文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     *
     * @param grantType 获取access_token填写client_credential
     * @param appId     第三方用户唯一凭证
     * @param appSecret 第三方用户唯一凭证密钥，即appsecret
     * @return 响应结果
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getAccessToken(@Query("grant_type") String grantType,@Query("appid") String appId,@Query("secret") String appSecret);

    /**
     * 获取凭据ticket
     * 文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
     * 前端根据凭证展示二维码："https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET
     * @param accessToken   getAccessToken 获取的 token 信息
     * @param weixinQrCodeRequestDTO 入参对象
     * @return 应答结果
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrCode(@Query("access_token") String accessToken, @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO);
}
