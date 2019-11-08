package configureFiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;
import rows.AllDataRow;

public class Nomenclature {
	private static final String n = "\r\n";
	private static final String colon = ";";
	private static final String replaceWith = "";
	private static final Pattern p = Pattern.compile("( )*$");

	HashMap<String, AllDataRow> allDataMap = null;

	String finalData = null;
	String fileName = null;

	public Nomenclature() {
		super();
	}

	public Nomenclature(HashMap<String, AllDataRow> allDataMap, String fileName) {
		super();
		this.allDataMap = allDataMap;

		this.fileName = fileName;
	}

	public HashMap<String, AllDataRow> getAllDataMap() {
		return allDataMap;
	}

	public void setAllDataMap(HashMap<String, AllDataRow> allDataMap) {
		this.allDataMap = allDataMap;
	}

	public String getFinalData() {
		return finalData;
	}

	public void setFinalData(String finalData) {
		this.finalData = finalData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void writeFile() throws IOException {
		if (allDataMap != null && fileName != null) {

			allDataMap.forEach((k, v) -> finalData += k + colon + v.getName() + n);
			FileOutputStream outputStream = new FileOutputStream(fileName);

			byte[] strToBytes = finalData.getBytes();

			outputStream.write(strToBytes);

			outputStream.close();
		}
	}

	@Override
	public String toString() {
		return "Nomenclature [allDataMap=" + allDataMap + ", finalData=" + finalData + ", fileName=" + fileName + "]";
	}

}
