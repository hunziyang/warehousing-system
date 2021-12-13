package com.yang.warehousing.web.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger 配置文件
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "swagger")
@EnableSwagger2
public class SwaggerConfig {

    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String name;
    private String url;
    private String email;
    @Value("${apiKey.name}")
    private String apiKeyName;
    @Value("${apiKey.keyName}")
    private String apiKeyKeyName;
    @Value("${apiKey.passAs}")
    private String passAs;

    @Bean
    @DependsOn({"apiInfo", "apiKeyList", "securityContexts"})
    public Docket docket(ApiInfo apiInfo, List<ApiKey> apiKeyList, List<SecurityContext> securityContexts) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .securitySchemes(apiKeyList)
                .securityContexts(securityContexts)
                // 配置扫描接口
                .select()
                // 扫描类上的注解
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 扫描方法上的注解
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    @Bean
    public List<ApiKey> apiKeyList() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(apiKeyName, apiKeyKeyName, passAs));
        return apiKeyList;
    }

    /**
     * 在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用认证参数的接口），
     * 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。
     * 即不需要使用上文中设置的名为“Authorization”，type为“header”的认证参数。
     * 通俗讲，就是能匹配上的就使用默认认证，就不使用header里面的Authorization认证参数
     */
    @Bean
    public List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return securityContexts;
    }

    /**
     * 认证范围
     * @return
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = {authorizationScope};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }


    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact(name, url, email);
        return new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
