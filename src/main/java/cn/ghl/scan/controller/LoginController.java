package cn.ghl.scan.controller;

import cn.ghl.scan.service.ILoginService;
import cn.ghl.scan.types.common.Constants;
import cn.ghl.scan.types.res.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网页登录
 * @Author GengHongLiang
 * @Date 2024/5/30 11:20
 * @Version 1.0
 */
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class LoginController {
    @Resource
    private ILoginService loginService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    /**
     * 获取微信 ticket 凭证
     * 测试地址：http://内网穿透/api/v1/login/wxQrcodeTicket
     * return 前端获取到Ticket后 可以直接通过Ticket 换去二维码 HTTP GET请求（请使用https协议）https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET 提醒：TICKET记得进行UrlEncode
     */
    @RequestMapping(value = "wxQrcodeTicket", method = RequestMethod.GET)
    public Response<String> weixinQrCodeTicket() {
        try {
            String qrCodeTicket = loginService.createQrCodeTicket();
            logger.info("生成微信扫码登录 ticket {}", qrCodeTicket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(qrCodeTicket)
                    .build();
        } catch (Exception e) {
            logger.info("生成微信扫码登录 ticket 失败", e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 检查是否登录
     * @param ticket
     * @return
     */
    @RequestMapping(value = "checkLogin", method = RequestMethod.GET)
    public Response<String> checkLogin(@RequestParam String ticket) {
        try {
            String openidToken = loginService.checkLogin(ticket);
            logger.info("扫描检测登录结果 ticket:{} openidToken:{}", ticket, openidToken);
            if (StringUtils.isNotBlank(openidToken)) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openidToken)
                        .build();
            } else {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.NO_LOGIN.getCode())
                        .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                        .build();
            }
        } catch (Exception e) {
            logger.info("扫描检测登录结果失败 ticket:{}", ticket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
