package com.ifreegroup.classremover

import com.android.SdkConstants
import com.android.build.api.transform.TransformInput
import com.sun.org.apache.xpath.internal.operations.Bool
import javassist.ClassPool
import javassist.CtClass
import javassist.NotFoundException
import org.apache.commons.io.FileUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.regex.Matcher

/**
 * Created by Jaminchanks on 2018/8/27.
 */
class ConvertUtils {

    private static String PACKAGE_BOXING = "com.bilibili.boxing.AbsBoxingActivity"

    private static String PACKAGE_MQ = "com.meiqia.meiqiasdk.activity.MQBaseActivity"

    /**
     * @param inputs
     * @param classPool
     * @param exceptClassList 排除在外的类名
     * @return
     */
    static List<CtClass> toCtClasses(Collection<TransformInput> inputs, ClassPool classPool) {
        List<String> classNames = new ArrayList<>()
        List<CtClass> allClass = new ArrayList<>()

        def startTime = System.currentTimeMillis()
        inputs.each {
            it.directoryInputs.each {
                def dirPath = it.file.absolutePath
                classPool.insertClassPath(it.file.absolutePath)
                FileUtils.listFiles(it.file, null, true).each {
                    if (it.absolutePath.endsWith(SdkConstants.DOT_CLASS)) {
                        def className = it.absolutePath.substring(dirPath.length() + 1, it.absolutePath.length() - SdkConstants.DOT_CLASS.length()).replaceAll(Matcher.quoteReplacement(File.separator), '.')
                        if(classNames.contains(className)){
                            throw new RuntimeException("You have duplicate classes with the same name : "+className+" please remove duplicate classes..1 ")
                        }
                        classNames.add(className)
                    }
                }
            }

            it.jarInputs.each {
                classPool.insertClassPath(it.file.absolutePath)
                def jarFile = new JarFile(it.file)
                Enumeration<JarEntry> classes = jarFile.entries()
                while (classes.hasMoreElements()) {
                    JarEntry libClass = classes.nextElement()
                    String className = libClass.getName()
                    if (className.endsWith(SdkConstants.DOT_CLASS)) {
                        className = className.substring(0, className.length() - SdkConstants.DOT_CLASS.length()).replaceAll('/', '.')
                        if (className.startsWith(PACKAGE_BOXING) || className.startsWith(PACKAGE_MQ)) { //如果jar包中包含了重复的类，剔除
                            println "find duplicate class : ${className}"
                        } else {
                            classNames.add(className)
                        }
                    }
                }
            }
        }
        def cost = (System.currentTimeMillis() - startTime) / 1000
        println "read all class file cost $cost second"
        classNames.each {
            try {
                allClass.add(classPool.get(it))
            } catch (NotFoundException e) {
                println "class not found exception class name:  $it "
            }
        }

        Collections.sort(allClass) { class1, class2->
            class1.getName() <=> class2.getName()
        }

        return allClass
    }

}