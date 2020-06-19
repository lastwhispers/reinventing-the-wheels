package cn.lastwhisper.sorm.core;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import cn.lastwhisper.sorm.bean.ColumnInfo;
import cn.lastwhisper.sorm.bean.TableInfo;
import cn.lastwhisper.sorm.utils.JavaFileUtils;
import cn.lastwhisper.sorm.utils.PathUtils;
import cn.lastwhisper.sorm.utils.StringUtils;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构。
 *
 */
public class TableContext {

	/**
	 * 表名为key，表信息对象为value
	 */
	public static Map<String, TableInfo> tables = new HashMap<String, TableInfo>();

	/**
	 * 将po的class对象和表信息对象关联起来，便于重用！
	 */
	public static Map<Class<? extends Object>, TableInfo> poClassTableMap = new HashMap<Class<? extends Object>, TableInfo>();

	private TableContext() {
	}

	static {
		try {
			// 初始化获得表的信息
			Connection con = DBManager.getConn();
			DatabaseMetaData dbmd = con.getMetaData();

			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });

			while (tableRet.next()) {
				String tableName = (String) tableRet.getObject("TABLE_NAME");

				TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), new HashMap<String, ColumnInfo>());
				tables.put(tableName, ti);
				// 查询表中的所有字段
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
				while (set.next()) {
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				// 查询表中的主键
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
				while (set2.next()) {
					ColumnInfo ci2 = (ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					// 设置为主键类型
					ci2.setKeyType(1);
					ti.getPriKeys().add(ci2);
				}

				if (ti.getPriKeys().size() > 0) { // 取唯一主键。。方便使用。如果是联合主键。则为空！
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 更新类结构
		updateJavaPOFile();
		
		// 加载po包下面所有的类，便于重用，提高效率！
		loadPOTables();
	}

	/**
	 * 根据表结构，更新配置的po包下面的java类 实现了从表结构转化到类结构
	 */
	public static void updateJavaPOFile() {
		Map<String, TableInfo> map = TableContext.tables;
		// 获取po的路径
		String poPackagePath = PathUtils.GetPoPackagePath();
		// 获取srcPath
		String srcPath = PathUtils.GetSrcPath();
		for (TableInfo t : map.values()) {
			// 获取表对应的po得绝对路径
			String poFile = srcPath + poPackagePath + StringUtils.firstChar2UpperCase(t.getTname())+ ".java";
			boolean exists = new File(poFile).exists();
			// po存在
			if (!exists) {
				JavaFileUtils.createJavaPOFile(t, new MySqlTypeConvertor());
			}
		}
	}

	/**
	 * 加载po包下面的类
	 */
	public static void loadPOTables() {

		for (TableInfo tableInfo : TableContext.tables.values()) {
			try {
				// 包名.表名
				Class<? extends Object> c = Class.forName(DBManager.getConf().getPoPackage() + "."
						+ StringUtils.firstChar2UpperCase(tableInfo.getTname()));
				poClassTableMap.put(c, tableInfo);
			} catch (ClassNotFoundException e) {
			}
		}

	}

}
