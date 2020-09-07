package webapp.grizzlyrestaurant.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import webapp.grizzlyrestaurant.Service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/fonts/**"
    };
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/index", "/client-register", "/registerclient").permitAll()
	        .antMatchers("/admin*").access("hasRole('ADMIN')")
	        .antMatchers("/user*").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout")
            .and()
            .exceptionHandling().accessDeniedPage("/accessdenied");
        	
    		
    }
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
    	return new CustomAccessDeniedHandler();
    }
    
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
}