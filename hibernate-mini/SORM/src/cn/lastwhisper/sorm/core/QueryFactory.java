package cn.lastwhisper.sorm.core;

/**
 * 创建Query对象的工厂类
 * 
 * @author eval
 *
 */
public class QueryFactory {

	private static Query prototypeObj; // 原型对象

	static {
		try {
			Class<?> c = Class.forName(DBManager.getConf().getQueryClass());
			prototypeObj = (Query) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 加载po包下面所有的类，便于重用，提高效率！
//		TableContext.loadPOTables();
	}

	public static Query createQuery() {
		try {
			return (Query) prototypeObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
