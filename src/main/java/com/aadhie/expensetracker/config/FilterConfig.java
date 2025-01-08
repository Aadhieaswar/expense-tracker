package com.aadhie.expensetracker.config;

import com.aadhie.expensetracker.filters.FirebaseTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<FirebaseTokenFilter> firebaseFilter() {
        FilterRegistrationBean<FirebaseTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FirebaseTokenFilter());
        registrationBean.addUrlPatterns("/api/*", "/auth/*");
        return registrationBean;
    }
}
