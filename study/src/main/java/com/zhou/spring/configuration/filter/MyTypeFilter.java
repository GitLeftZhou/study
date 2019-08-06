package com.zhou.spring.configuration.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {

    /**
     * 在这个实现类中实现过滤的逻辑规则
     *
     * @param metadataReader the metadata reader for the target class
     *                       读取到的当前正在扫描的类的信息
     * @param metadataReaderFactory a factory for obtaining metadata reader
     *                              for other classes (such as superclasses and interfaces)
     *                              可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前类的定义信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        System.out.println("MyTypeFilter.match : " + classMetadata.getClassName());
        //获取当前类的资源信息(e.g 类的路径)
        Resource resource = metadataReader.getResource();

        // 类名包含service
        if (classMetadata.getClassName().toLowerCase().contains("service")) {
            return true;
        }

        return false;
    }
}
