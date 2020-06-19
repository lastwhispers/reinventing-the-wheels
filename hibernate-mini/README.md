>该项目源码地址：https://github.com/ggb2312/sorm
配套demo项目：https://github.com/ggb2312/sorm/tree/master/sormdemo
jar包地址：https://github.com/ggb2312/sorm/tree/master/jar

# 1. 框架简介

S意味着super、smart、simple，orm即Object Relational Mapping。  
Sorm是一款可以实现对象和SQL自动映射的Java框架  。

**核心功能:**

根据表生成实体类

根据实体类生成对应sql语句

# 2. 依赖

将jar包引入即可

# 3. 快速使用

>1. 在src下建立db.properties
>2. 每张表只能有一个主键。不能处理多个主键的情况。
>3. po尽量使用包装类，不要使用基本数据类型。

下面介绍一些使用示例

## 3.1 环境准备

**（1）创建表**

```sql
CREATE TABLE `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

insert  into `dept`(`id`,`dname`,`address`) values (1,'财务部','金融街'),(2,'技术部','四三旗');

CREATE TABLE `emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empname` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `deptId` int(11) DEFAULT NULL,
  `bonus` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

insert  into `emp`(`id`,`empname`,`salary`,`birthday`,`age`,`deptId`,`bonus`) values (2,'tom',2500.4,'2019-04-01',25,1,200),(3,'kangkang',3000.8,'2019-04-01',30,2,100),(4,'mali',3000.8,'2019-04-01',30,1,300),(5,'lily',3000.8,'2019-04-01',30,1,121),(6,'lily',3000.8,'2019-04-01',30,1,123),(7,'lily',3000.8,'2019-04-01',30,2,123),(8,'lily',3000.8,'2019-04-01',30,2,123);
```

**（2）创建db.properties文件**

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/sorm
user=root
pwd=root
# 使用的数据库名
usingDB=mysql
# src路径  需要修改
srcPath=D:\\code\\GitRepository\\SORM\\sormdemo\\src
# sorm生成pojo的路径  需要修改
poPackage=cn.sormdemo.po
# QueryFactory的实现 
queryClass=cn.lastwhisper.sorm.core.MySqlQuery
# 连接池参数
poolMinSize=5
poolMaxSize=100
```

**（3）生成对应实体类**

```java
import cn.lastwhisper.sorm.core.TableContext;

public class Generate {
	public static void main(String[] args) throws ClassNotFoundException {
		// 初始化po
		TableContext.updateJavaPOFile();
	}
}
```



## 3.1 增加

将对象对应成sql语句，执行sql，插入数据库中 

```java
@Test
public void addTest() {
	// 增
	Emp e = new Emp();
	e.setEmpname("lily");
	e.setBirthday(new java.sql.Date(System.currentTimeMillis()));
	e.setAge(30);
	e.setSalary(3000.8);
	QueryFactory.createQuery().insert(e);
}
```

## 3.2 删除

根据对象主键的值，生成sql，执行，从库中删除 

```java
@Test
public void deleteTest() {
	// 删
	Emp e = new Emp();
	e.setId(9);
	QueryFactory.createQuery().delete(e);
}
```

## 3.3 修改

根据对象需要修改属性的值，生成sql，执行 

```java
@Test
public void updateTest() {
	// 改
	Emp e = new Emp();
	e.setId(5);
	e.setEmpname("tom");
	e.setAge(25);
	e.setSalary(2500.4);
	QueryFactory.createQuery().update(e, new String[] { "empname", "age", "salary" });
}
```

## 3.4 查询

###  3.4.1 多行多列

**（1）pojo查询**

```java
@Test
public void queryPojoTest() {
	// pojo查
	List<Emp> pojo = QueryFactory.createQuery().queryRows(
				"select id,empname,salary,birthday,age,deptId,bonus from emp where empname=?", Emp.class, new String[] { "tom" });
	for (Emp e : pojo) {
		System.out.println("empname:" + e.getEmpname());
	}
}
```

**（2）vo查询**

```java
package cn.sormdemo.vo;

public class EmpVO {
	
	private Integer id;
	private String empname;
	private Double totalSalary; 
	private Integer age;
	private String deptName;
	private String deptAddr;
	
	
	public EmpVO(Integer id, String empname, Double totalSalary, Integer age,
			String deptName, String deptAddr) {
		super();
		this.id = id;
		this.empname = empname;
		this.totalSalary = totalSalary;
		this.age = age;
		this.deptName = deptName;
		this.deptAddr = deptAddr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public Double getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptAddr() {
		return deptAddr;
	}
	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}
	
	public EmpVO() {
	}

}
```

```java
@Test
public void queryVoTest() {
		// vo查
	List<EmpVO> vo = QueryFactory.createQuery().queryRows(
				"select e.id,e.empname,e.salary+e.bonus 'totalSalary',age,d.dname deptName from emp e join dept d on e.deptId=d.id;", EmpVO.class, null);
	for (EmpVO empVO : vo) {
		System.out.println(empVO.getEmpname() + "-" + empVO.getTotalSalary());
	}
}
```

### 3.4.2 一行多列

```java
@Test
public void queryUniqueRowTest() {
	// 一行多列 查询
	Emp queryUniqueRow = (Emp) QueryFactory.createQuery().queryUniqueRow(
				"select id,empname,salary,birthday,age,deptId,bonus from emp where empname=?", Emp.class, new String[] { "tom" });
	System.out.println(queryUniqueRow.getEmpname() + "-" + queryUniqueRow.getBirthday());
}
```

### 3.4.3 一行一列

**（1）number**

```java
@Test
public void queryNumberTest() {
	// 数字 查询
	Number count = QueryFactory.createQuery().queryNumber("select count(*) from emp where empname=?",new String[] { "tom" });
	System.out.println(count);
}
```

**（2）普通对象**

```java
@Test
public void queryObjectTest() {
	// 普通对象 查询
	String empname = (String) QueryFactory.createQuery().queryValue("select empname from emp where empname=?",new String[] { "tom" });
	System.out.println(empname);
}
```

# 4. 核心架构

- **Query接口：**
  - 负责查询（对外提供服务的核心类）
- **QueryFactory类：**
  - 负责根据配置信息创建query对象
- **TypeConvertor接口：**
  - 负责类型转换
- **TableContext类：**
  - 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构。  
- **DBManager类：**
  - 根据配置信息，维持连接对象的管理(对接连接池功能)
- **核心bean**
  - ColumnInfo 封装表中一个字段的信息(字段类型、字段名、键类型)
  - Configuration 封装配置文件信息
  - TableInfo 封装一张表的信息
- **工具类：**
  - JDBCUtils封装常用JDBC操作 
  - StringUtils封装常用字符串操作
  - JavaFileUtils封装java文件操作
  - ReflectUtils封装常用反射操作 
  - PathUtils封装获取路径的操作

Sorm架构图：

![Sorm架构图](https://upload-images.jianshu.io/upload_images/5336514-1e7e868a9141fada.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 5. 设计模式

- 使用工厂模式统一管理Query的创建
- 使用原型模式提高Query对象的创建效率

# 6. 待优化

- 目前使用的是list集合维护的一个连接池，性能太差，将来会整合druid等连接池。
- 目前不支持特殊的字段名和表名，将来会添加@TableName（表名注解）、@TableId（主键注解）、@TableField（非主键注解）等注解使实体字段名更加灵活。
- 目前只支持单实体类、单主键，将来会添加一对多、多对多。

# 7.更新日志
```
v0.1
	1.完成基本功能
v0.2
	1.修改query接口（模板方法executeQueryTemplate）
	2.新增queryFactory统一管理query对象（原型模式）
	3.新增连接池
v0.3
	1.已存在的po不生成
```
​	
