package com.nagi.rpc.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.nagi.rpc.config.spring.NagiConstant.nagi_service_prex;

public class ServiceBean<T> implements InitializingBean,ApplicationContextAware,BeanNameAware {

    List<String> nagiServices = Collections.synchronizedList(new ArrayList<String>());

    private ApplicationContext context;

    public void afterPropertiesSet() throws Exception {



        /*检查是否有Nagi:Serive的bean,如果有则发布到注册中心*/
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanName:beanDefinitionNames
             ) {
            if(beanName.startsWith(nagi_service_prex)){
                nagiServices.add(beanName);
            }
        }





    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void setBeanName(String s) {
        
    }

}
