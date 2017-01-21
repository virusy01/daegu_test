package snp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Collections;

/**
 * <br/>개정이력<br/>
 * @author
 */
@Configuration
public class SecurityConfig {
    @EnableGlobalMethodSecurity(securedEnabled = true)
    static public class MethodSecurityConfigurer extends GlobalMethodSecurityConfiguration {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider authenticationProvider) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }

        /*@Override
        protected AccessDecisionManager accessDecisionManager() {
            return new UnanimousBased(Collections.singletonList(new AuthenticatedVoter()));
        }*/
    }

    @EnableWebSecurity
    static public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
          [주의] 이 @Autowired 는 생성자로 옮기지 말 것.
          SpringSecurity는 Spring 내에서도 특수하게 동작함.
          ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         */
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        Environment env;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .headers().frameOptions().sameOrigin().and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/**/**/*.html",
                            "/**/**/*.css",
                            "/**/**/*.js",
                            "/**/**/*.png",
                            "/example/**",
                            "/app/**/**",
                            "/api/auth/**",
                            "/api/languages/**",
                            "/api/words/**"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
        }
    }
}
