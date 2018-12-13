package com.nagi.rpc.config.spring.beans.annotation;

import com.nagi.rpc.config.annotation.NagiService;
import com.nagi.rpc.config.spring.context.annotation.NagiComponentScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.nagi.rpc.config.spring.NagiConstant.nagi_service_prex;

public class NagiServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor,EnvironmentAware {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public static final  String resourcePattern = "**/*.class";

    private final Set<String> packagesToScan;

    private Environment env;

    @Nullable
    private ResourcePatternResolver resourcePatternResolver;
    @Nullable
    private MetadataReaderFactory metadataReaderFactory;

    /*写一个构造器用于上一层BeanDefinitionBuilder传入的参数*/
    public NagiServiceAnnotationBeanPostProcessor(Set<String> packages){
        packagesToScan = packages;
    }
    /*注册Bean，扫描@NagiService注解*/
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

        if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {
            registerServiceBeans(resolvedPackagesToScan, registry);
        }
    }
    /*将扫描到的@Service类注册到IOC*/
   private void registerServiceBeans(Set<String> resolvedPackagesToScan, BeanDefinitionRegistry registry) {
        Map<String,AbstractBeanDefinition> candidates = new HashMap<String, AbstractBeanDefinition>();
        /*找到所有@Nagiservice的类，转换成BeanDefinition*/
        for (String packagetoScan:resolvedPackagesToScan) {
            Map<String, AbstractBeanDefinition> stringAbstractBeanDefinitionMap = scanNagiService(packagetoScan);
            candidates.putAll(stringAbstractBeanDefinitionMap);
        }

       Set<String> keys = candidates.keySet();
       /*注册到IOC*/
       for (String bd:keys
            ) {
           //registerNagiService(bd,registry);
           registry.registerBeanDefinition(bd,candidates.get(bd));
           //addRegister(bd);
       }
    }

    private void registerNagiService(AbstractBeanDefinition bd, BeanDefinitionRegistry registry) {
        //bd.get

    }

    /*添加到注册中心*/
    private void addRegister(AbstractBeanDefinition bd) {
        
    }

    private Map<String,AbstractBeanDefinition> scanNagiService(String path){
        String packageSearchPath = "classpath*:" + this.resolveBasePackage(path) + '/' + resourcePattern;
        Set<AbstractBeanDefinition> candidates = new LinkedHashSet<AbstractBeanDefinition>();
        Map<String,AbstractBeanDefinition> candiMap = new HashMap<String, AbstractBeanDefinition>();
        try {
            Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);

            int var1 = resources.length;

            for (int i = 0; i < var1; i++) {
                Resource r1 = resources[i];

                if(r1.isReadable()){
                    MetadataReader metadataReader = this.getMetadataReaderFactory().getMetadataReader(r1);
                    /*对有@NagiService的类做处理*/
                    if(metadataReader.getAnnotationMetadata().getAnnotationTypes().contains(NagiService.class.getName())){
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        //sbd.setBeanClassName(metadataReader.getAnnotationMetadata().get);
                        //Map<String, Object> interfaceName = metadataReader.getAnnotationMetadata().getAnnotationAttributes("interfaceName");
                        AnnotationAttributes annotationAttributes = AnnotationAttributes
                                .fromMap(metadataReader.getAnnotationMetadata().getAnnotationAttributes(NagiService.class.getName()));
                        String interfaceName = annotationAttributes.getString("interfaceName");

                        if(StringUtils.isEmpty(interfaceName)){
                            interfaceName = sbd.getBeanClassName();
                        }
                        interfaceName = nagi_service_prex+interfaceName;
                        sbd.setResource(r1);
                        sbd.setSource(r1);
                        candidates.add(sbd);
                        candiMap.put(interfaceName,sbd);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return candiMap;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void setEnvironment(Environment environment) {
        env = environment;
    }

    /*处理占位符*/
    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        Set<String> resolvedPackagesToScan = new LinkedHashSet<String>(packagesToScan.size());
        for (String packageToScan : packagesToScan) {
            if (StringUtils.hasText(packageToScan)) {
                String resolvedPackageToScan = env.resolvePlaceholders(packageToScan.trim());
                resolvedPackagesToScan.add(resolvedPackageToScan);
            }
        }
        return resolvedPackagesToScan;
    }
    /*将com.nagi.rpc转变成com/nagi/rpc */
    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(env.resolveRequiredPlaceholders(basePackage));
    }

    /**
     * Spring提供了一个ResourcePatternResolver实现PathMatchingResourcePatternResolver，它是基于模式匹配的，
     * 默认使用AntPathMatcher进行路径匹配，它除了支持ResourceLoader支持的前缀外，还额外支持“classpath*:”
     * 用于加载所有匹配的类路径Resource，ResourceLoader不支持前缀“classpath*:”：
     * @return
     */
    private ResourcePatternResolver getResourcePatternResolver(){
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return resourcePatternResolver;
    }

    /**
     *
     * @return
     */
    MetadataReaderFactory getMetadataReaderFactory(){
        if (this.metadataReaderFactory == null) {
            this.metadataReaderFactory = new CachingMetadataReaderFactory();
        }
        return metadataReaderFactory;
    }
}
