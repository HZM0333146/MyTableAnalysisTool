package hzm.table.analysis.util.universal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	/**
	 * create a file
	 * 
	 * @param path
	 * @throws IOException
	 */
	static public void createFile(String path) throws IOException {
		// TODO
		File fe = new File(path);
		if (!fe.exists()) {
			fe.createNewFile();
		}
	}

	/**
	 * Write data to file
	 */
	static public void inDataToFile(String filePath, List<String> lsData) throws IOException {
		// TODO
		FileOutputStream fos = null;
		fos = new FileOutputStream(filePath);
		for (String data : lsData) {
			StringBuffer sb = new StringBuffer(data);
			sb.append("\n");
			byte[] dataByte = sb.toString().getBytes();
			fos.write(dataByte);
		}
		fos.close();
	}

	/**
	 * Read file content
	 */
	static public List<String> readFileData(String filePath) throws IOException {
		List<String> lsString = new ArrayList<>();
		FileReader fr = new FileReader(filePath);
		BufferedReader reader = new BufferedReader(fr);
		String line = "";
		while ((line = reader.readLine()) != null) {
			lsString.add(line);
		}
		fr.close();
		return lsString;
	}

}
