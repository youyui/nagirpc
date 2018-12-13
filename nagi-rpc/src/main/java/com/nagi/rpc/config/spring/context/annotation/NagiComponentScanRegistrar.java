package com.nagi.rpc.config.spring.context.annotation;

import com.nagi.rpc.config.spring.beans.annotation.NagiServiceAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.*;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * 搜索相关注解，@NagiService和@NagiReference等 并注册到IOC中
 */
public class NagiComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        /*从已经扫描到的annotationMetadata中找到你想要的注解，*/
        Set<String> packagesToScan = getPackagesToScan(annotationMetadata);

        registerServiceAnnotationBeanPostProcessor(packagesToScan, registry);

        registerReferenceAnnotationBeanPostProcessor(registry);
    }


    /**扫描@Service注解并注册到IOC**/
    private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder definitionBuilder = rootBeanDefinition(NagiServiceAnnotationBeanPostProcessor.class);
        definitionBuilder.addConstructorArgValue(packagesToScan);
        definitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();

        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }
    private void registerReferenceAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {

    }
    /**
     * 扫描注解@NagiComponentScan中的packages
     * @param annotationMetadata
     * @return
     */
    private Set<String> getPackagesToScan(AnnotationMetadata annotationMetadata) {
        /*找NagiComponentScan注解*/
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(NagiComponentScan.class.getName()));
        /*获得注解中的参数 packages，*/
        String[] packages = annotationAttributes.getStringArray("packages");

        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(packages));

        System.out.println(packagesToScan);
        return packagesToScan;
    }
}
