package com.demoSpringSecurity.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.demoSpringSecurity.filter.AuthoritiesLoggingAfterFilter;
import com.demoSpringSecurity.filter.AuthoritiesLoggingAtFilter;
import com.demoSpringSecurity.filter.CsrfCookieFilter;
import com.demoSpringSecurity.filter.JWTTokenGeneratorFilter;
import com.demoSpringSecurity.filter.JWTTokenValidatorFilter;
import com.demoSpringSecurity.filter.RequestValidationBeforeFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class Projectsecurityconfig{
    
	
	
	
	   @Bean
	    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
	        requestHandler.setCsrfRequestAttributeName("_csrf");

	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                .cors().configurationSource(new CorsConfigurationSource() {
	                    @Override
	                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	                        CorsConfiguration config = new CorsConfiguration();
	                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	                        config.setAllowedMethods(Collections.singletonList("*"));
	                        config.setAllowCredentials(true);
	                        config.setAllowedHeaders(Collections.singletonList("*"));
	                        config.setExposedHeaders(Arrays.asList("Authorization"));
	                        config.setMaxAge(3600L);
	                        return config;
	                    }
	                }).and()
	                        .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
	                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
	                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
	                        .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
	                        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
	                        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
	                        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
	                        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
	                .authorizeHttpRequests()
//	                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//	                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT","VIEWBALANCE")
//	                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//	                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS") 
//	                        .requestMatchers("/user").authenticated()
	                .requestMatchers("/myAccount").hasRole("USER")
                    .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myLoans").authenticated()
                    .requestMatchers("/myCards").hasRole("USER") 
                    .requestMatchers("/user").authenticated()
	                        .requestMatchers("/notices", "/contact", "/register").permitAll()
	                .and().formLogin()
	                .and().httpBasic();
	        return http.build();
	    }

	

//	@Bean
//    SecurityFilterChain defaultsecurityFilterChain(HttpSecurity http) throws Exception
//    { 
//		http.csrf().disable();
//    	http.authorizeHttpRequests()
//    	.requestMatchers("/secured").authenticated()
//    	.requestMatchers("/unsecured","/register").permitAll()
//    	.and().formLogin()
//    	.and().httpBasic();
//    	return http.build();
//    }
	
/*	@Bean
	public InMemoryUserDetailsManager userDetailsService()
	{
	//UserDetails admin=User.withDefaultPasswordEncoder()
	//			.username("joseph")
	//	        .password("jose12345")
	//	        .authorities("admin")
	//	        .build();
		
	//	UserDetails user=User.withDefaultPasswordEncoder()
	//			.username("john")
	//	        .password("luke")
	//	        .authorities("user")
	//	        .build();
	
		
		UserDetails admin=User.withUsername("joseph")
		        .password("jose12345")
		        .authorities("admin")
		        .build();
		
		UserDetails user=User.withUsername("john")
		        .password("luke")
		        .authorities("user")
		        .build();
		
		return new InMemoryUserDetailsManager(admin,user);
	}
	

*/
	
/*	@Bean
	public UserDetailsService userdetailservice(DataSource datasource)
	{
		
		return new JdbcUserDetailsManager(datasource);
	}
	*/
	
/*	@Bean
	public PasswordEncoder passwordencoder()
	{
	   return NoOpPasswordEncoder.getInstance();	
	}
*/	
	@Bean
	public PasswordEncoder passwordencoder()
	{
	   return new BCryptPasswordEncoder();	
	}
	
}