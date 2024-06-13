package cn.ghl.scan.config;

import cn.ghl.scan.service.api.IWxApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Http客户端 对OKHttp的封装 可以基于接口的方式通信
 * @Author GengHongLiang
 * @Date 2024/5/30 9:07
 * @Version 1.0
 */
@Configuration
public class Retrofit2Config {
    private static final String BASE_URL = "https://api.weixin.qq.com/";

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * 注入微信 API Service 因为这块不复杂 所以可以采用这种注入的方式  如果是基于企微对接 可以采用 Retrofit 提供的请求拦截器 创建对象
     * @param retrofit
     * @return
     */
    @Bean
    public IWxApiService weixinApiService(Retrofit retrofit) {
        return retrofit.create(IWxApiService.class);
    }


}
