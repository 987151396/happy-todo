# 说明
该插件作用，用于在编译过程去除第三方库中与项目相同全限定名的类
具体实现原理为读取编译期第三方库的jar文件内的类，去除与项目中重复的类剔除后再重新打包入jar包中

## 插件版本更新操作
更新版本时请修改 build.gradle中的
```groovy
group="com.ifreegroup"
version= ${PLUGIN_VERSION}
```
将`version`修改为新的版本，
执行task `build` 和 `uploadArchives`，
执行操作后将在`repo`目录下生成对应的插件

## 使用
project目录下的build.gradle
```groovy
buildscript {
    //......
    repositories {
        //......
        maven {url uri("./dependencies/classremover/repo")}
    }
    //......
    
      dependencies {
            //......
            classpath "com.ifreegroup:classremover:${PLUGIN_VERSION}"
        }
}
```


