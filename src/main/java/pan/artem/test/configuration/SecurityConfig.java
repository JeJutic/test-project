package pan.artem.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pan.artem.test.service.security.UserDetailsServiceImpl;

import java.util.stream.Stream;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder configureGlobal(
            AuthenticationManagerBuilder auth,
            UserDetailsServiceImpl userDetailsService
    ) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        return encoder;
    }

    private RequestMatcher methodMatcher(String method) {
        return request -> request.getMethod().equals(method);
    }

    private final RequestMatcher isView = methodMatcher("GET");
    private final RequestMatcher isEdit = request -> Stream.of("POST", "PUT", "DELETE")
            .anyMatch(method -> methodMatcher(method).matches(request));
    private final String ADMIN_ROLE = "ADMIN";

    private void viewerEditorRole(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz,
            RequestMatcher isPath, String generalRole, String viewerRole, String editorRole
    ) {
        authz.requestMatchers(request -> isPath.matches(request) && isView.matches(request))
                .hasAnyRole(ADMIN_ROLE, generalRole, viewerRole)
                .requestMatchers(request -> isPath.matches(request) && isEdit.matches(request))
                .hasAnyRole(ADMIN_ROLE, generalRole, editorRole);
    }
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        RequestMatcher isPostPath = new AntPathRequestMatcher("/api/posts/**");
        RequestMatcher isAlbumPath = new AntPathRequestMatcher("/api/albums/**");
        RequestMatcher isUserPath = new AntPathRequestMatcher("/api/users/**");
        RequestMatcher isAdminPath = new AntPathRequestMatcher("/admin/**");
        RequestMatcher isWsPath = new AntPathRequestMatcher("/ws/**");

        return httpSecurity.authorizeHttpRequests(authz -> {
                    viewerEditorRole(authz, isPostPath, "POSTS", "POSTS_VIEWER", "POSTS_EDITOR");
                    viewerEditorRole(authz, isAlbumPath, "ALBUMS", "ALBUMS_VIEWER", "ALBUMS_EDITOR");
                    viewerEditorRole(authz, isUserPath, "USERS", "USERS_VIEWER", "USERS_EDITOR");
                    authz.requestMatchers(isAdminPath).hasRole(ADMIN_ROLE);
                    authz.requestMatchers(isWsPath).hasAnyRole(ADMIN_ROLE, "WS");
                    authz.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
