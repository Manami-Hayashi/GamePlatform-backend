package be.kdg.prog6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/store/games").permitAll()
                        .requestMatchers("/game-statistics/update").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(mgmt -> mgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("http://localhost:5174");
        configuration.addAllowedOrigin("http://localhost:8081");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8181/realms/GamePlatform/protocol/openid-connect/certs").build();
    }
}

class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final Map<String, Object> realmAccess =
                (Map<String, Object>) jwt.getClaims().get("realm_access");
        return ((List<String>) realmAccess.get("roles")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}


//other version which seems to wrok
//package be.kdg.prog6.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//                .sessionManagement(mgmt -> mgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:5173"); // Add your frontend URL here
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
//        return jwtConverter;
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8181/realms/GamePlatform/protocol/openid-connect/certs").build();
//    }
//}
//
//class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        final Map<String, Object> realmAccess =
//                (Map<String, Object>) jwt.getClaims().get("realm_access");
//        return ((List<String>) realmAccess.get("roles")).stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//}