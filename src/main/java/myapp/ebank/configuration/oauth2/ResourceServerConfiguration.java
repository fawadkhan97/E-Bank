package myapp.ebank.configuration.oauth2;

import myapp.ebank.util.exceptionshandling.AuthenticationExceptionHandling;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    final AuthenticationExceptionHandling authenticationExceptionHandling;

    private static final String[] AUTH_WHITELIST = {
            //login
            "/user/login",
            "/oauth/token",
            //Sign up url
            "/user/add",
            "/organization/add",

            //other url
            "/**/today",
            "/**/dailyRates",
            "/**/getByDate",
            "/**/getByDateBetween",
            "/**/getByStartDate",

            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    public ResourceServerConfiguration(AuthenticationExceptionHandling authenticationExceptionHandling) {
        this.authenticationExceptionHandling = authenticationExceptionHandling;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("ebank");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/user/all", "/**/delete/**", "/role/**", "/permission/**", "/organization/**").hasAuthority("admin")
                .antMatchers("/{userid}/**", "/user/update", "/user/get/**").hasAuthority("user")
                .antMatchers("/**/add", "/**/update", "/**/all", "/**/get/**").hasAuthority("employee")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationExceptionHandling)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //  http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
