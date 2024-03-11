package pan.artem.test.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import pan.artem.test.service.handler.ExternalServiceResponseErrorHandler;
import pan.artem.test.properties.AppProperties;

@Configuration
@EnableConfigurationProperties({
        AppProperties.class,
        AppProperties.ResourceServiceProperties.class,
        AppProperties.EchoServiceProperties.class
})
@EnableCaching
public class AppConfig {
    @Bean
    public ResponseErrorHandler externalErrorHandler() {
        return new ExternalServiceResponseErrorHandler();
    }

    @Bean
    public RestTemplate albumRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            AppProperties.ResourceServiceProperties properties,
            ResponseErrorHandler externalErrorHandler
    ) {
        return restTemplateBuilder
                .rootUri(properties.getAlbumsUrl())
                .errorHandler(externalErrorHandler)
                .build();
    }

    @Bean
    public RestTemplate postRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            AppProperties.ResourceServiceProperties properties,
            ResponseErrorHandler externalErrorHandler
    ) {
        return restTemplateBuilder
                .rootUri(properties.getPostsUrl())
                .errorHandler(externalErrorHandler)
                .build();
    }

    @Bean
    public RestTemplate userRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            AppProperties.ResourceServiceProperties properties,
            ResponseErrorHandler externalErrorHandler
    ) {
        return restTemplateBuilder
                .rootUri(properties.getUsersUrl())
                .errorHandler(externalErrorHandler)
                .build();
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }
}
