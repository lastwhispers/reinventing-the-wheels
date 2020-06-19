package cn.sormdemo.test;

import java.util.List;

import org.junit.Test;

import cn.lastwhisper.sorm.core.Query;
import cn.lastwhisper.sorm.core.QueryFactory;
import cn.sormdemo.po.Emp;
import cn.sormdemo.vo.EmpVO;

public class ApiExample {

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

	@Test
	public void deleteTest() {
		// 删
		Emp e = new Emp();
		e.setId(9);
		QueryFactory.createQuery().delete(e);
	}

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

	@Test
	public void queryPojoTest() {
		// pojo查
		List<Emp> pojo = QueryFactory.createQuery().queryRows(
				"select id,empname,salary,birthday,age,deptId,bonus from emp where empname=?", Emp.class,
				new String[] { "tom" });
		for (Emp e : pojo) {
			System.out.println("empname:" + e.getEmpname());
		}
	}

	@Test
	public void queryVoTest() {
		// vo查
		List<EmpVO> vo = QueryFactory.createQuery().queryRows(
				"select e.id,e.empname,e.salary+e.bonus 'totalSalary',age,d.dname deptName from emp e join dept d on e.deptId=d.id;",
				EmpVO.class, null);
		for (EmpVO empVO : vo) {
			System.out.println(empVO.getEmpname() + "-" + empVO.getTotalSalary());
		}
	}

	@Test
	public void queryUniqueRowTest() {
		// 一行多列 查询
		Emp queryUniqueRow = (Emp) QueryFactory.createQuery().queryUniqueRow(
				"select id,empname,salary,birthday,age,deptId,bonus from emp where empname=?", Emp.class,
				new String[] { "tom" });
		System.out.println(queryUniqueRow.getEmpname() + "-" + queryUniqueRow.getBirthday());
	}

	@Test
	public void queryNumberTest() {
		// 数字 查询
		Number count = QueryFactory.createQuery().queryNumber("select count(*) from emp where empname=?",
				new String[] { "tom" });
		System.out.println(count);
	}
	
	@Test
	public void queryObjectTest() {
		// 普通对象 查询
		String empname = (String) QueryFactory.createQuery().queryValue("select empname from emp where empname=?",
				new String[] { "tom" });
		System.out.println(empname);
	}

	@Test
	public void queryIdTest() {
		// 根据id查询对象
		Query query = QueryFactory.createQuery();
		Emp emp = (Emp) query.queryById(Emp.class, 2);
		System.out.println(emp);
	}

}
