package com.zhou.spring.configuration.filter;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义bean选择器
 */
public class MyImportSelector implements ImportSelector {
    /**
     *
     * @param importingClassMetadata 标注@Import类的所有注解信息
     * @return 导入到容器中的全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //可以在此处直接写全类名数组，也可以写方法遍历某个Jar或者某个包下的所有类
        return new String[0];
    }
}
