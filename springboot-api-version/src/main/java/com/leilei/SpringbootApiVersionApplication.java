package com.leilei;

import com.leilei.version.ApiVersionHandlerMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class SpringbootApiVersionApplication implements WebMvcRegistrations {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiVersionApplication.class, args);
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping();
    }

}
