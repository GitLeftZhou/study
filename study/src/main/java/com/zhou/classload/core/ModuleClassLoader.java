package com.zhou.classload.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义模块加载器
 * 模块内的class由自定义类加载器加载，其他由父加载器加载
 */
public class ModuleClassLoader extends ClassLoader {

    private String moduleName = "";
    private String moduleFile = "";
    private String classPath = "";
    private List<String> classFiles = null;

    public ModuleClassLoader(String classPath){
        this.classPath = classPath;
    }

    private boolean isModuleFile(String name){
        boolean flag = false;
        if(name.endsWith(".class") && this.classFiles.contains(name)){
            flag = true;
        } else if (this.classFiles.contains(name+".class")){
            flag = true;
        }
        return flag;
    }
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        System.out.println("使用了自定义类加载器");
        Class<?> clazz = findLoadedClass(name);
        if (null == clazz){
            if (isModuleFile(name)){
                byte [] classData = loadClassData(name);
                if (null == classData){
                    throw new ClassNotFoundException();
                } else{
                    clazz = defineClass(name,classData,0,classData.length);
                }
            }else{//不是本模块文件调用父类加载器
                clazz = this.getParent().loadClass(name);
            }
        }
        return clazz;
    }

    private byte[] loadClassData(String name) {
        String path = classPath + "/"+name.replace('.','/');
        if(!name.endsWith(".class")){
            path+=".class";
        }
        try {
            try (InputStream is = new FileInputStream(new File(path));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
                byte [] b = new byte[1024];
                int len =0;
                while (-1!=(len=is.read(b))){
                    bos.write(b,0,len);
                }
                bos.flush();
                return bos.toByteArray();
            }
        } catch (IOException e) {
            return null;
        }
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setModuleFile(String moduleFile) {
        this.moduleFile = moduleFile;
        classFiles = new ArrayList<>();
        parseModuleFile();
//        for (int i = 0; i < this.classFiles.size(); i++) {
//            System.out.println(classFiles.get(i));
//        }
    }

    /**
     * 根据传入的模块文件定义
     * 解析class清单存入内存
     */
    private void parseModuleFile(){
        //模块只允许 jar 或者目录
        try {
            String fileName = this.classPath+"/"+this.moduleFile;
            if (moduleFile.endsWith(".jar")){
                JarFile jarFile = jarFile = new JarFile(fileName);
                Enumeration enu = jarFile.entries();
                while (enu.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry) enu.nextElement();
                    String name = jarEntry.getName();
                    classFiles.add(name);
                }
            } else {//文件夹
                File file = new File(fileName);
                readFileList(file,classFiles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 迭代获取class 清单
     * @param file
     * @param classFiles
     */
    private void readFileList(File file ,List<String> classFiles){
        if (file.isDirectory()){
            String [] files = file.list();
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                File tempFile = new File(file,fileName);
                if (tempFile.isDirectory()){
                    readFileList(tempFile,classFiles);
                } else{
                    if(fileName.endsWith(".class")){
                        String tmp = tempFile.getAbsolutePath().substring(this.classPath.length()+1);
//                        System.out.println("tmp="+tmp);
                        String tmp1=tmp.replace("/",".")
                                .replace("\\",".");
//                        System.out.println("tmp1="+tmp1);
                        classFiles.add(tmp1);
                    }
                }
            }
        }
    }

}
