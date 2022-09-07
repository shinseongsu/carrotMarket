package com.carret.market.config.openfeign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.carret.market.application.api")
public class OpenFeignConfig {

}
