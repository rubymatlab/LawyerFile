package com.lyf.utils;

import java.io.File;
import java.util.Date;

public class ExpiredFiles {
	public static void Delete(String localDir) {
		//删除一小时前的文件
		Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60);
		File folder = new File(localDir);
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (new Date(file.lastModified()).before(date)) {
				file.delete();
			}
		}
	}
}
