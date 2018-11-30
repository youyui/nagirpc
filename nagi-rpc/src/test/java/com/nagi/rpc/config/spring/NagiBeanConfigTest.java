package com.nagi.rpc.config.spring; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** 
* NagiBeanConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 30, 2018</pre> 
* @version 1.0 
*/ 
public class NagiBeanConfigTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}
@Test
public void testBeanConfig(){
    AnnotationConfigApplicationContext context =  new AnnotationConfigApplicationContext(NagiBeanConfig.class);

    String[] beanDefinitionNames = context.getBeanDefinitionNames();

    for (String beananame:beanDefinitionNames
         ) {
        System.out.println(beananame);
    }
}


} 
