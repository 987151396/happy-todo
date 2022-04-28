package com.ifreegroup.classremover

import com.android.build.api.transform.Context
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.CannotCompileException
import javassist.ClassPool
import javassist.CtClass
import javassist.bytecode.AccessFlag
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger

import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 该插件用于去除第三方库中的重复类
 * 当需要手动替换第三方库中的某个类时，可以在项目中编写同名类，并使用该插件排除第三方库中的同名类
 * Created by Jaminchanks on 2018/8/27.
 */
public class RemoverPlugin extends Transform implements Plugin<Project> {
    Project project
    static Logger logger

    @Override
    void apply(Project project) {
        this.project = project
        logger = project.logger

        def taskNames = project.gradle.startParameter.taskNames
        def isDebugTask = false
        for (int index = 0; index < taskNames.size(); ++index) {
            def taskName = taskNames[index]
            logger.debug "input start parameter task is ${taskName}"
            //FIXME: assembleRelease下屏蔽Prepare，这里因为还没有执行Task，没法直接通过当前的BuildType来判断，所以直接分析当前的startParameter中的taskname，
            //另外这里有一个小坑task的名字不能是缩写必须是全称 例如assembleDebug不能是任何形式的缩写输入
            if (taskName.endsWith("Debug") && taskName.contains("Debug")) {
                isDebugTask = true
                break
            }
        }

        //仅在release模式下去除同名类
        if (!isDebugTask) {
            project.android.registerTransform(this)
        }
    }


    @Override
    String getName() {
        return "classremover"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {

        logger.quiet '============================= start remove class ==========================='

        def startTime = System.nanoTime()

        outputProvider.deleteAll()

        File jarFile = outputProvider.getContentLocation("main", getOutputTypes(), getScopes(),
                Format.JAR)
        if(!jarFile.getParentFile().exists()){
            jarFile.getParentFile().mkdirs()
        }
        if(jarFile.exists()){
            jarFile.delete()
        }

        logger.quiet "${jarFile.name} ==> ${jarFile.absolutePath}"

        ClassPool classPool = new ClassPool()
        project.android.bootClasspath.each {
            logger.quiet "file classpath ==> ${it.absolutePath}"
            classPool.appendClassPath((String) it.absolutePath)
        }

        def ctClasses = ConvertUtils.toCtClasses(inputs, classPool)

        rewriteJarFile(ctClasses, jarFile)

        def endTime = System.nanoTime()

        def costTime = (endTime - startTime) / 1000_000_000

        logger.quiet "${costTime}s has gone"

        logger.quiet '=========================== end remove class =============================='
    }

    /**
     * 重写jar文件
     * @param ctClasses
     * @param jarFile
     * @throws IOException
     * @throws CannotCompileException
     */
    private void rewriteJarFile(List<CtClass> ctClasses, File jarFile) throws IOException, CannotCompileException {
        ZipOutputStream outStream = new JarOutputStream(new FileOutputStream(jarFile))
        for (CtClass ctClass : ctClasses) {
            ctClass.setModifiers(AccessFlag.setPublic(ctClass.getModifiers()))
            zipFile(ctClass.toBytecode(), outStream, ctClass.getName().replaceAll("\\.", "/") + ".class")
        }

        outStream.close()
    }

    /**
     * 重新打包jar文件里的文件
     * @param classBytesArray
     * @param zos
     * @param entryName
     */
    private void zipFile(byte[] classBytesArray, ZipOutputStream zos, String entryName) {
        try {
            ZipEntry entry = new ZipEntry(entryName)
            zos.putNextEntry(entry)
            zos.write(classBytesArray, 0, classBytesArray.length)
            zos.closeEntry()
            zos.flush()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

}
