package pan.artem.test.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    @Getter
    @Setter
    @Validated
    @ConfigurationProperties(prefix = "app.resource-service")
    public static class ResourceServiceProperties {
        @NotEmpty
        private String albumsUrl;

        @NotEmpty
        private String postsUrl;

        @NotEmpty
        private String usersUrl;
    }

    @Getter
    @Setter
    @Validated
    @ConfigurationProperties(prefix = "app.echo-service")
    public static class EchoServiceProperties {
        @NotEmpty
        private String url;

        @NotNull
        private Long waitTimeout;
    }
}
