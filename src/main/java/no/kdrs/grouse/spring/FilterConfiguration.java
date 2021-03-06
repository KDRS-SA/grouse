package no.kdrs.grouse.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter()
    {
        FilterRegistrationBean<ForwardedHeaderFilter> bean =
                new FilterRegistrationBean<>();
        bean.setFilter(new ForwardedHeaderFilter());
        return bean;
    }

}
