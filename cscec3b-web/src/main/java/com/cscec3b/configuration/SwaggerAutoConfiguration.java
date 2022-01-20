package com.cscec3b.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerAutoConfiguration{
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cscec3b"))
                .paths(PathSelectors.any())
                .build();
    }

//    http://localhost:8899/swagger-ui/index.html
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("平台对外接口")
                .description("1.提供后台使用的接口 2.提供对其他服务调用的服务")
                .contact(new Contact("anyedapinguo", "https://github.com/anyedapingguo/cscec3b-api", "530353249@qq.com"))
                .version("1.0")
                .build();
    }


}