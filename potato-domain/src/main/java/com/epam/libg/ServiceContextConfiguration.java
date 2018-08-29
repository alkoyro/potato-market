package com.epam.libg;

import com.epam.libg.dao.PotatoBagDao;
import com.epam.libg.dao.impl.InMemoryPotatoBagDao;
import com.epam.libg.service.PotatoBagService;
import com.epam.libg.service.impl.PotatoBagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * service configuration for domain module
 */
@Configuration
public class ServiceContextConfiguration {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public PotatoBagService potatoBagService() {
        return new PotatoBagServiceImpl(potatoBagDao());
    }

    @Bean
    public PotatoBagDao potatoBagDao() {
        return new InMemoryPotatoBagDao();
    }
}
