package cn.ghl.scan.controller;

import cn.ghl.scan.types.sdk.wx.MessageTextEntity;
import cn.ghl.scan.types.sdk.wx.SignatureUtil;
import cn.ghl.scan.types.sdk.wx.XmlUtil;
import com.google.common.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用来处理微信验签及微信回调
 * @Author GengHongLiang
 * @Date 2024/5/30 12:26
 * @Version 1.0
 */
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    private Logger logger = LoggerFactory.getLogger(WeixinPortalController.class);
    // 自己的公众号
    @Value("${weixin.config.originalid}")
    private String originalid;
    // token
    @Value("${weixin.config.token}")
    private String token;

    @Resource
    private Cache<String, String> openidToken;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * appid     微信端AppID
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            logger.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check(token, signature, timestamp, nonce);
            logger.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            logger.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }


    /**
     * 回调，接收公众号消息【扫描登录，会接收到消息】
     */
    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("openid") String openid) {
        try {
            logger.info("接收微信公众号扫码信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);

            // 扫码登录【消息类型和事件】
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                // 实际的业务场景，可以生成 jwt 的 token 让前端存储
                openidToken.put(message.getTicket(), openid);
                return buildMessageTextEntity(openid, "登录成功");
            }

            logger.info("接收微信公众号信息请求{}完成 {}", openid, requestBody);
            // 构建返回消息
            return buildMessageTextEntity(openid, "请先扫码登录！");
        } catch (Exception e) {
            logger.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }

}
