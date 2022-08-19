package com.hackerton.tor.gateway;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserFilter extends AbstractGatewayFilterFactory<UserFilter.Config> {
    //Constructor
    public UserFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("UserFilter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPrelogger()) {
                log.info("UserFilter Start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostlogger()) {
                    log.info("UserFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        });
    }


    @Data
    public static class Config{
        private String baseMessage;
        private boolean prelogger;
        private boolean postlogger;
    }
}