# Spring 学习笔记
[源代码导入步骤](https://github.com/wxkgh/SignOn/blob/master/Review/import-spring-source.md)
## IoC容器的实现
### IoC容器的架构
![Ioc images](https://github.com/wxkgh/SignOn/blob/master/Review/images/Ioc%20Interface.png)
基本IoC容器：BeanFactory -> HierarchicalBeanFactory -> ConfigurableBeanFactory
  - BeanFactory：定义了基本的IoC容器的规范
  - HierarchicalBeanFactory：增加了双亲IoC容器的管理功能。
  
高级IoC特性：以ApplicationContext应用上下文接口为核心，一方面ListableBeanFactory和HierarchicalBeanFactory连接BeanFactory接口定义和ApplicationContext应用上下文的接口定义，另一方面ApplicationContext接口通过继承MessageSource，ResourcesLoader，ApplicationEventPublisher接口，在BeanFactory简单IoC容器的基础上添加了许多对高级容器的特性的支持。
### IoC容器的使用步骤
#### 创建BeanFactory容器
1. 创建IoC配置文件的抽象资源，这个抽象资源包含了BeanDefinition的定义信息
2. 创建BeanFactory(实现)
3. 创建一个载入BeanDefinition的读取器，载入BeanDefinition，通过一个回调配置给BeanFactory
4. 从定义好的资源位置读入配置信息，具体的解析过程由BeanDefinitionReader来完成。
完成整个载入和注册Bean定义之后，需要的IoC容器就建立起来了。
```java
public class ProgramBeanFactory {
	public static void main(String[] args) {
		ClassPathResource res = new ClassPathResource("test.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);
	}
}
```
此处的Resource接口关系：
ClassPathResource -> AbstractFileResolvingResource -> AbstractResource -> Resource -> InputStreamSource
Resource是资源的初始接口，它继承InputStreamSource，实现其getInputStream方法，因此所有的资源都是通过该方法获取输入流的。
1. ClassPathResource()
```java
	public ClassPathResource(String path, @Nullable ClassLoader classLoader) {
		Assert.notNull(path, "Path must not be null");
		String pathToUse = StringUtils.cleanPath(path);
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		this.path = pathToUse;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}
```
2. DefaultListableBeanFactory()
```java
	public DefaultListableBeanFactory() {
		super();
	}
    /* -------- */
    /**
	 * Create a new AbstractAutowireCapableBeanFactory.
	 */
	public AbstractAutowireCapableBeanFactory() {
		super();
		ignoreDependencyInterface(BeanNameAware.class);
		ignoreDependencyInterface(BeanFactoryAware.class);
		ignoreDependencyInterface(BeanClassLoaderAware.class);
	}
	/* -------- */
	public AbstractBeanFactory(@Nullable BeanFactory parentBeanFactory) {
		this.parentBeanFactory = parentBeanFactory;
	}	
```
这里DefaultListableBeanFactory所起到的作用就是忽略给定接口的自动装配功能。简单来说，一般bean中的功能A如果没有初始化，那么Spring会自动初始化A，这是Spring的一个特性。但当某些特殊情况时，B不会初始化，比如：B已经实现了BeanNameAware接口。可以说，就是通过其他方式来解析依赖，类似于BeanFactory的BeanFactoryAware。

3. XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
   reader.loadBeanDefinitions(res);
```java
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}
	/* -------- */
	protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		this.registry = registry;

		// Determine ResourceLoader to use.
		if (this.registry instanceof ResourceLoader) {
			this.resourceLoader = (ResourceLoader) this.registry;
		}
		else {
			this.resourceLoader = new PathMatchingResourcePatternResolver();
		}

		// Inherit Environment if possible
		if (this.registry instanceof EnvironmentCapable) {
			this.environment = ((EnvironmentCapable) this.registry).getEnvironment();
		}
		else {
			this.environment = new StandardEnvironment();
		}
	}
```