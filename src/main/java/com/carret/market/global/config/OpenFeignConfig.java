package com.carret.market.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.carret.market.infrastructure")
public class OpenFeignConfig {
}
