# 开发过程记录
## 一.踩坑
### 框架之坑
#### 1.《架构探险》一书中实现的框架
- 注解：在控制器上使用Controller注解，在控制器类的方法上使用Action注解，在服务类上使用Service注解，在控制器类中可使用Inject注解将服务类依赖注入。
- 类加载器：加载使用某注解的类、实现某个接口的类或者继承某父类的所有子类等。
  - 获取类： `Class.forName(className, isInitialized, getClassLoader())`
  - 获取指定包名下所有类：将包名转换为文件路径，读取class或jar包，获取指定的类名去加载类。
  - 获取使用某注解的类：先自定义注解(`@Controller`,`@Service`等)，可以将带有这些注解的类所产上的对象理解为由框架管理的bean。
  封装助手方法，将上述类获取到一个类集合中。
- Bean容器：获取bean类集合，根据类来实例化对象，再将创建的对象放在一个静态的`Map<Class<?>,Object>`中。
- 依赖注入：获取所有的BeanMap(即实现Bean容器步骤中创建的静态Map)，遍历这个映射关系，分别取出Bean类与实例，进而通过反射获取类中所有的成员变量，继续遍历这些成员变量，判断这些成员变量是否带有注解，若带有DI注解，则从BeanMap中取出Bean实例，修改实例域的值。
- 加载Controller：获取所有定义了Controller注解的类，通过反射获取这些类中所有带有Action注解的方法，获取Action注解中的请求表达式，进而获取请求方法与请求路径。封装一个请求对象(Request)与处理对象(Handler)，最后将Request与Handler建立映射关系，放入一个ActionMap中，并提供一个可根据请求方法与请求路径获取处理对象的方法。
- 初始化：加载上述类的静态模块。
- 请求转发器：创建一个Servlet来处理所有的请求，根据请求信息与请求路径来调用具体的Action方法。
- [代码地址](https://github.com/wxkgh/Ori)
###### 问题(keng)
作者给这个框架起名叫smart，然而这个框架并不smart，~~我认为它很stupid~~因为它使用了太多的静态代码块和静态方法，而且在初始化框架的时候实际的执行顺序和作者预期的不一致，实际上这个框架本身根本不能正常运行，我想这大概也是这本书在网上评价不高的原因吧。  
此外，框架功能的实现代码写的并不好，非常难以看懂，作为初级开发人员，我闻到了熟悉的“硬写”的味道，让我想起了刚入职的时候为了做题写的元素是链表的二维数组，指导我的小哥测试了功能之后拒绝对我充满了List***的代码做出评价。这类代码都通过繁琐的逻辑直接实现功能，难以阅读和维护。当然，对于写出这些代码的人来说，也许他的逻辑思维和hold住的代码量有些许增加。

#### 2.Spring MVC
smart框架由于无法正常运行，被我抛弃了。现在需要拿出一个新的方案，我们可以用最基本的jsp和Servlet来开发一个[基础的web项目](https://github.com/wxkgh/SchoolSystem)，但是这样太繁琐了，无穷无尽的Servlet令人心烦。
而从smart框架的实现来看，它其实是仿照SpringMVC的实现，而正主明显要比模仿者强的多，在对smart框架原理有所了解的情况下，使用SpringMVC是水到渠成的选择。
- 配置方式：使用带有`@EnableWebMvc`注解的配置类  
启用组件扫描，配置静态资源(映射路径)，配置JSP视图解析器
- 应用上下文：DispatcherServlet和ContextLoaderListener

SpringMVC的使用大大简化了开发步骤，在使用它的过程中我也逐渐了解了其他框架和类库，这种便捷让习惯了嵌入式开发自己造轮子的我感觉大开眼界。正好由于“业务需求”，需要支持https，为了配置方便和尝鲜，我大胆将框架换成了更加敏捷的SpringBoot。

#### 3.Spring Boot
Spring的组件代码是轻量级的，但它的配置却是重量级的。我个人非常讨厌XML文件配置~~那不是给人看的~~在前公司的时候我曾被迫对某一模块的大坨XML配置文件写配置恢复，深知XML文件结构复杂之后带来的灾难后果。所幸当我使用Spring时它已经引入了基于注解的组件扫描，在这种简单的项目中不需要显式的XML配置。  
但Spring Boot又一次带来了惊喜，它使得开发者能够不用在Spring的配置上多花功夫，正如Spring实战的作者所说，它改变了游戏规则。  
在换用Spring Boot的过程中我发现IDE并不是想象中的那么可靠，将项目工程从Spring MVC变为Spring Boot导致了严重的依赖问题，Spring Boot无法启动，而在我用Spring Boot新建项目，将原来的文件和配置导入之后，这个问题遍莫名其妙地解决了。

### 数据库访问
#### 1.单纯使用JDBC
```java
try {
        String sql = "select userpri from user where username=? and password=? and usertype=?";
        pstmt = conn.prepareStatement(sql);//实例化操作
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getUsertype());
        ResultSet rSet = pstmt.executeQuery();//取得结果
        if (rSet.next()) {
            //读取用户权限值
            user.setUserpri(rSet.getInt(1));
            flag = true;
        } catch (Exception e) {
            throw e;
        } finally {
            //关闭操作
            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
}
```
最基础的jdbc使用方式，代码繁琐，一点也不优雅。要操作用户，就得有UserDao，要操作顾客，就得有CustomerDao，可以做一个静态代理，然而并没有什么用。

#### 2.JdbcTemplate与泛型
```java
    public <T> T queryEntity(Class<T> entityClass, String sql) {
        try {
            return jdbcTemplate.query(sql, resultSet -> {
                if (!resultSet.next()) return null;
                ResultSetMetaData data = resultSet.getMetaData();
                int count = data.getColumnCount();
                return toPOJO(entityClass, resultSet, data, count);
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
```
到这一步的时候，UserDao和CustomerDao不复存在，只有DAO.class。DAO类已经可以让所有Model共用了。

#### 3.Mybatis
```java
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, password, salt) VALUES (#{username}, #{password}, #{salt})")
    int insertUserByAccount(@Param("username") String username, @Param("password") String password, @Param("salt") String salt);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(@Param("id") long id);

    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{id}")
    int updateUser(@Param("username") String username, @Param("password") String password, @Param("id") long id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findUserByAccount(@Param("username") String username);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") long id);
}
```
如此强大，只需要一个接口？我看着改了三天的DAO.class，一阵无语，把它删除了。

### 定位与功能
一开始，这个项目的定位是学生在校写的非常熟练的xx系统(如学生管理系统，选课系统等)，但是我已经不是学生了，写这样的东西难免感觉沮丧，光看名字就觉得很low了。后来我决定仿照成就与欲望管理的App来做这个项目，在下载了一堆App并逐一体验了它们功能之后，我发现这种思路更适合做移动端的App，和Web项目其实不大相符。望着项目里仅有的用户登录功能，我意识到一个项目并不一定非得是一套完整的功能实现，它可以作为稳定可靠的一部分来为整个系统提供服务，因此我决定专注做用户的注册、认证和登录。

## 二.设计
### 用户名和密码
从开发者的角度，我们应遵循如下原则：
1. 限制用户输入一些非常容易被破解的口令
2. 不要明文保存用户的口令
3. 保证在网上的安全传输

### 用户登录状态
HTTP是无状态的协议，这个协议无法记录用户访问状态，并且每次请求都是独立的无关联的。而我们的网站都是设计成多个页面的，所在页面跳转过程中我们需要知道用户的状态，尤其是用户登录的状态，这样我们在页面跳转后我们才知道是否可以让用户有权限来操作一些功能或是查看一些数据。所以，我们每个页面都需要对用户的身份进行认证。  
可以用Cookie来实现这一功能，而不是每个页面都验证用户的密码。关于Cookie，我们也需要谨慎使用：
1. 不能在Cookie中存放用户密码
2. 正确设计“记住密码”：
  - 在Cookie中，保存用户名、登录序列，登录token  
    用户名：明文存放  
    登录序列：一个被MD5散列过的随机数，仅当强制用户输入口令时更新(如：用户修改了密码)  
    登录token：一个被MD5散列过的随机数，仅一个登录session内有效，新的登录session会更新它  
  - 服务器上存储上述三种数据，验证时与Cookie中的做对比
  - 作用：
    登录token是单实例登录。  
    登录序列检测盗用Cookie的行为
