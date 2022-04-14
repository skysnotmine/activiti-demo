package per.porco.demo.activitidemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityJwtConfig extends WebSecurityConfigurerAdapter {


  /**
   * ignore和permitall相比, ignore是完全绕过了spring security的所有filter，相当于不走spring security，
   * @param web
   * @throws Exception
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
            .antMatchers("/css/**")
            .antMatchers("*.js")
            .antMatchers("*.css")
            .antMatchers("/doc.html")
            .antMatchers("**/*.jpg")
            .antMatchers("**/*.png")
            //放开swagger-ui地址
            .antMatchers("/swagger/**")
            .antMatchers("/v2/api-docs")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/webjars/**")
            .antMatchers("/druid/**")
            .antMatchers("/favicon.ico")
            .antMatchers("/captcha.jpg")
            .antMatchers("/csrf")
            .antMatchers("/images/**")
            .antMatchers("/js/**")
            .antMatchers("/layui/**")
            .antMatchers("/css/**")
            .antMatchers("/treetable-lay/**")
            .antMatchers("/404")

            //ignore 开头不会做任何校验
            //测试接口不会被拦截
            .antMatchers("/test/**")
            .antMatchers("/**");
  }



}

