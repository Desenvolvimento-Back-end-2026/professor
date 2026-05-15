package br.uni.ms.libtec.borrowTec.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        RestTemplate rt = new RestTemplate();
        rt.setRequestFactory(new org.springframework.http.client.JdkClientHttpRequestFactory());
        return rt;
    }

}
