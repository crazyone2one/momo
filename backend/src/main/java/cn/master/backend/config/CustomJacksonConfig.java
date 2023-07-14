package cn.master.backend.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author create by 11's papa on 2023/7/14-14:26
 */
@Configuration
public class CustomJacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return builder -> builder.serializationInclusion(JsonInclude.Include.ALWAYS)
                .failOnUnknownProperties(false);
    }
}
