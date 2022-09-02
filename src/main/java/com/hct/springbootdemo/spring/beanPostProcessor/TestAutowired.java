package com.hct.springbootdemo.spring.beanPostProcessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月02日 10:00
 * @Description: Autowired运行分析
 */
public class TestAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 创建过程，依赖注入，初始化
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());
        // @Value的解析，不加不能解析这个注解，会报错
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 解析${}
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        /**
         * 1.查找哪些属性、方法加了 @Autowired,这称之为 InjectionMetadata
         * 自己新建 AutowiredAnnotationBeanPostProcessor 后处理器
         */
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        System.out.println(bean1);
//
//        // 重要！！！！postProcessProperties()   执行依赖注入 @Autowired @Value
//        processor.postProcessProperties(null,bean1,"bean1");


        Method method = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        method.setAccessible(true);
        // 获取 Bean1 中加了 @Autowired 和 @Value的成员变量，包括方法参数
        InjectionMetadata metadata = (InjectionMetadata) method.invoke(processor, "bean1", Bean1.class, null);
        System.out.println(metadata);

        // 调用 InjectionMetadata 来进行依赖注入，注入时按类型查找值
        metadata.inject(bean1,"bean1",null);
        System.out.println(bean1);

        // 3、如何按类型查找值
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        // 内部把bean3封装成 DependencyDescriptor 对象,false表示找不到返回null，true表示找不到抛出异常
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3, false);
        // 根据成员变量找要注入谁
        Object o1 = beanFactory.doResolveDependency(dd1, null, null, null);
        System.out.println(o1);


        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        // false表示找不到返回null，true表示找不到抛出异常
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2, 0), false);
        Object o2 = beanFactory.doResolveDependency(dd2, null, null, null);
        System.out.println(o2);

        Method setHome = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor dd3 = new DependencyDescriptor(new MethodParameter(setHome, 0), true);
        Object o3 = beanFactory.doResolveDependency(dd3, null, null, null);
        System.out.println(o3);

    }
}
