# Spring 学习笔记
源代码导入步骤
## IoC容器的实现
### IoC容器的设计
![Ioc images](https://github.com/wxkgh/SignOn/blob/master/Review/images/Ioc%20Interface.png)
基本IoC容器：BeanFactory -> HierarchicalBeanFactory -> ConfigurableBeanFactory
  - BeanFactory：定义了基本的IoC容器的规范
  - HierarchicalBeanFactory：增加了双亲IoC容器的管理功能。
  
高级IoC特性：以ApplicationContext应用上下文接口为核心，一方面ListableBeanFactory和HierarchicalBeanFactory连接BeanFactory接口定义和ApplicationContext应用上下文的接口定义，另一方面ApplicationContext接口通过继承MessageSource，ResourcesLoader，ApplicationEventPublisher接口，在BeanFactory简单IoC容器的基础上添加了许多对高级容器的特性的支持。

