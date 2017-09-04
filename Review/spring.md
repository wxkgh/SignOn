# Spring 学习笔记
## IoC容器的实现
### IoC容器的设计
![Ioc images](https://github.com/wxkgh/SignOn/blob/master/Review/images/Ioc%20Interface.png)
基本IoC容器：BeanFactory -> HierarchicalBeanFactory -> ConfigurableBeanFactory
  - BeanFactory：定义了基本的IoC容器的规范
  - HierarchicalBeanFactory：增加了双亲IoC容器的管理功能。
  - 
高级IoC特性：