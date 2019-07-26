package com.zhou.classload.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例--类加载器工厂
 * 用以保证所有的类加载器由此工厂进行管理
 * 一个模块分配一个类加载器
 */
public class ClassLoaderFactory extends ClassLoader{
    private static ConcurrentHashMap<String,ModuleClassLoader> moduleClassLoaderInstances = null;
    private static ClassLoaderFactory classLoaderFactory = new ClassLoaderFactory();
    private static String rootPath = null;
    private static Map<String,String> moduleList = new ConcurrentHashMap<>();

    private ClassLoaderFactory(){}


    /**
     * 刷新模块类加载器
     * 不覆盖已存在类加载器
     */
    public void flushModule(Map<String,String> modules){
        reloadModule(modules,false);
    }

    /**
     *  重新加载模块列表
     */
    public void  reloadModule(Map<String,String> modules){
        reloadModule(modules,true);
    }

    /**
     *  重新加载模块列表
     *  flag true 重新加载
     */
    private synchronized void  reloadModule(Map<String,String> modules,boolean flag){
        if (null==rootPath || rootPath.length()==0){
            return;//说明没有调用获取实例方法，直接返回
        }
        //重置模块列表
        moduleList = new ConcurrentHashMap<>();
        moduleList.putAll(modules);

        // 去除已经移除模块的的类加载器
        if(flag){//覆盖模式，直接重置
            moduleClassLoaderInstances = new ConcurrentHashMap<>();
        } else {//追加模式，去除移除模块
            List<String> removeList = new ArrayList<>();
            for (String s : moduleClassLoaderInstances.keySet()) {
                if(!moduleList.containsKey(s)){
                    removeList.add(s);
                }
            }
            for (String s : removeList) {
                moduleClassLoaderInstances.remove(s);
            }
        }

        //获取类加载器
        for(String moduleName : moduleList.keySet()){
            ModuleClassLoader mcl = null;
            if (flag || !moduleClassLoaderInstances.containsKey(moduleName)){
                mcl = new ModuleClassLoader(rootPath);
                mcl.setModuleName(moduleName);
                mcl.setModuleFile(moduleList.get(moduleName));
                moduleClassLoaderInstances.put(moduleName,mcl);
            }
        }

    }



    /**
     * 本方法默认使用包名中的module.xx为模块名,约定优先
     * 模块配置化@See ModuleClassLoader.loadClass(String name)
     * @param name  全类名
     * @return Class对象
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        System.out.println("ClassLoaderFactory.loadClass:"+name);
        List<String> list = Arrays.asList(name.split("\\."));
        int idx = list.indexOf("module")+1;
        if(idx != 0 && list.size()>idx){
            ModuleClassLoader mcl = getClassLoader(rootPath,list.get(idx));
            return mcl.loadClass(name);
        } else {
            return super.loadClass(name);
        }
    }

    /**
     * 单例模式获取实例
     * @param moduleRoot 模块根路径
     * @param modules     模块包含文件(xx.jar 或者相对于moduleRoot的目录路径)
     * @return
     */
    public static ClassLoaderFactory getClassLoaderFactory(String moduleRoot, Map<String,String> modules ){
        /*
        *  双重监测模式因JVM 编译器优化和执行问题,会在极个别情况下产生不可测问题，已经不建议使用
        *  此处实际为饿汉式
        *  本方法双重监测为初始化部分属性,非初始化类本身
        */
        if (null == moduleList){
            synchronized (ClassLoaderFactory.class){
                if (null == moduleList){
                    moduleClassLoaderInstances = new ConcurrentHashMap<>();
                    rootPath = moduleRoot;
                    if (null != modules){
                        moduleList.putAll(modules);;
                    }
                }
            }
        }
        return classLoaderFactory;
    }

    /**
     * 获取模块的类加载器
     * @param classPath  文件根，默认为ClassLoader.getSystemResource("").getPath()
     * @param moduleName  模块名
     * @return
     */
    public ModuleClassLoader getClassLoader(String classPath,String moduleName){
        if (null==classPath || classPath.length()==0){
            classPath = ClassLoader.getSystemResource("").getPath();
            if(classPath.charAt(0) == '/'){
                classPath=classPath.substring(1);
            }
        }
        ModuleClassLoader mcl = null;
        if (moduleClassLoaderInstances.containsKey(moduleName)){
            mcl =  moduleClassLoaderInstances.get(moduleName);
        } else{
            synchronized (this){
                if (moduleClassLoaderInstances.containsKey(moduleName)){
                    mcl =  moduleClassLoaderInstances.get(moduleName);
                } else{
                    mcl = new ModuleClassLoader(classPath);
                    mcl.setModuleName(moduleName);
                    mcl.setModuleFile(moduleList.get(moduleName));
                    moduleClassLoaderInstances.put(moduleName,mcl);
                }
            }
        }
//        System.out.println("ModuleClassLoader.getModuleClassLoader:"+mcl.toString());
        return mcl;
    }


    /**
     * 移除已经存在的类加载器
     * @param moduleName
     */
    public void removeClassLoader(String moduleName){
        moduleClassLoaderInstances.remove(moduleName);
    }
}
