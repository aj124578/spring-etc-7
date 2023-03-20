package shop.mtcoding.springetc7.config.filter;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterRegisterConfig {

    @Bean // return을 해주면 IOC에 저장을 시켜준다 내가 만든게 아닌 것은 Bean으로 해주는게 좋다
    public FilterRegistrationBean<?> jwtVerifyFilterAdd() {
        FilterRegistrationBean<JwtVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtVerifyFilter());
        registration.addUrlPatterns("/user");
        registration.setOrder(1);
        return registration; // registraion 이 객체를 IOC에 띄우면 등록이 된다

    }
}

