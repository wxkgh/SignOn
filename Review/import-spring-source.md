# Import Spring framework Source Code
####1. Precompile `spring-oxm` with `./gradlew :spring-oxm:compileTestJava`
- 发现无论系统中是否安装gradle，都会去下载，预计时间100年，索性自己安装一个。
- 需要执行的命令变成了`gradle :spring-oxm:compileTestJava`
- 报错: "C:/xxxxx"(某jre路径)找不到tools.jar
- 设置`%JAVA_HOME%`和`%PATH%`环境变量，无效
- 在build.gradle里指定:
    ```
    dependencies {
        compile files("${System.properties['java.home']}/../lib/tools.jar"
    }
    ```
    无效。
- 怀疑安装的gradle设置的JDK路径不对，寻找更改路径的方法，发现运行build的project路径下如果有gradle.properties，会使用这个独立的配置
- 在源代码文件中的gradle.properties里指定了JDK的绝对路径  
  BUILD SUCCESSFUL

####2. 其余步骤按照import-into-idea.md里记载的进行