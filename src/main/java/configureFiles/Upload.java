package configureFiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import rows.AllDataRow;

public class Upload {
	private HashMap<String, AllDataRow> allDataMap;
	private String finalData;
	private String fileName;
	private static final String n = "\r\n";
	private static final String semilicon = ";";
	private static final String replaceWith = "";
	private final Pattern p = Pattern.compile("( )*$");

	public Upload() {
		super();
	}

	public Upload(HashMap<String, AllDataRow> allDataMap, String fileName) {
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
		if (allDataMap != null) {

			allDataMap.forEach((k, v) -> finalData += k + semilicon + v.getPrice() + semilicon + v.getLeftOvers() + n);
			FileOutputStream outputStream = new FileOutputStream(fileName);

			byte[] strToBytes = finalData.getBytes();

			outputStream.write(strToBytes);

			outputStream.close();
		}
	}

	@Override
	public String toString() {
		return "Upload [allDataMap=" + allDataMap + ", finalData=" + finalData + ", fileName=" + fileName + ", p=" + p
				+ "]";
	}

}
