package cn.lastwhisper.sorm.utils;

import cn.lastwhisper.sorm.core.DBManager;

public class PathUtils {
	
	// 在srcPath后添加"\\"
	public static String GetSrcPath() {
		String srcPath = DBManager.getConf().getSrcPath();
		if (srcPath.split("src").length == 1) {
			srcPath = srcPath + "\\";
		}
		return srcPath;
	}
	
	/**
	 * 	将cn.lastwhisper.po 转为cn/lastwhisper/po
	 * @return
	 */
	public static String GetPoPackagePath() {
		String poPackage = DBManager.getConf().getPoPackage();
		String[] poPackages = poPackage.split("\\.");
		StringBuffer poPackagePath = new StringBuffer();
		for (int i=0;i<poPackages.length;i++) {
			poPackagePath.append(poPackages[i]+"\\");
		}
		return poPackagePath.toString();
		 //return DBManager.getConf().getPoPackage().replaceAll("\\.", "\\\\");
	}
}
