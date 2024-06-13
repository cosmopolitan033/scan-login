package cn.ghl.scan.service;

/**
 * 微信登录服务：负责获取Ticket 和 check 登录
 * @Author GengHongLiang
 * @Date 2024/5/30 9:56
 * @Version 1.0
 */
public interface ILoginService {
    /**
     * 取二维码Ticket 需要通过AccessToken 来获取Ticket，然后根据Ticket获取二维码
     * @return
     */
    String createQrCodeTicket();

    /**
     * 检查是否登录
     * @param ticket
     * @return
     */
    String checkLogin(String ticket);


}
