package com.nagi.rpc.config.spring;

import org.omg.CORBA.Environment;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 扫描参考：
 * org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#scanCandidateComponents(java.lang.String)
 */

@Component
public class NagiBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /*存当扫描到的classname*/
    List<String> classNames = Collections.synchronizedList(new ArrayList<String>());


    @Autowired
    private ResourcePatternResolver resolver;    //解析器


    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        System.out.println("开始打印注册类");
        for (String classname:beanDefinitionNames
             ) {
            System.out.println(classname);
        }




    }



    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    /*private void scanpackage(String s) {
        URL resource1 = this.getClass().getClassLoader().getResource("");
        System.out.println(resource1);

        URL resource = this.getClass().getClassLoader().getResource("/" + replaceTo(s));
        String filestr = resource.getFile();
        File file = new File(filestr);

        String[] filenames = file.list();

        for (String path:filenames
             ) {
            File filePath = new File(s+"."+path);

            if(filePath.isDirectory()){
                scanpackage(path);
            }else{
                classNames.add(s + "." + filePath.getName());
            }
        }
    }

    private String replaceTo(String s) {
        return s.replaceAll("\\.", "/");
    }*/
}
