package com.nagi.rpc.config.spring;

import com.nagi.rpc.config.annotation.NagiService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NagiBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware,InitializingBean {

    Map<String,String> classnames = new ConcurrentHashMap<String, String>();

    @Autowired
    private ResourcePatternResolver resolver;    //解析器
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(NagiService.class);
        Set<String> ss = beansWithAnnotation.keySet();
        for (String s:ss){
            Object o = beansWithAnnotation.get(s);
            String s1 = o.getClass().getAnnotation(NagiService.class).interfaceName();
            System.out.println(s);
            System.out.println(s1);
            classnames.put(s1,s);
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();

        }
        System.out.println(beansWithAnnotation);
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println(classnames.size());
    }
}
