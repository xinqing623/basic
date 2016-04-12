package com.xinqing.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

	public static File createNewFile(String fileName, boolean isCoverOldFile) throws IOException {
		File f = new File(fileName);
		if (!f.isDirectory()) {
			File parent = f.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			if (isCoverOldFile) {
				if (f.exists()) {
					f.delete();
				}
				f.createNewFile();
			} else {
				if (!f.exists()) {
					f.createNewFile();
				}
			}
		}
		return f;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		StringBuffer sb = null;
		BufferedReader reader = null;
		try {
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(read);
			String tempString = null;
			sb = new StringBuffer();
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString).append("\r\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	public static void writeFile(String content,String filePath) throws IOException {
		File file = createNewFile(filePath, true);
		try (FileOutputStream fop = new FileOutputStream(file)) {
			byte[] contentInBytes = content.getBytes("UTF-8");
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		String content = readFileByLines("D:\\develop\\workspace\\ssh\\src\\template\\controller.ftl");
		writeFile(content, "c:/test/my/ttt.ftl");
		
	}

}
