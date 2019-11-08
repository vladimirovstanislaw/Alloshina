package parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import rows.AllDataRow;

public class RawDataParser {
	private String filenameFrom = null;
	private HashMap<String, AllDataRow> allDataMap;

	private static RawDataParser parser = new RawDataParser();

	private RawDataParser() {
		super();
		allDataMap = new HashMap<String, AllDataRow>();
	}

	public static RawDataParser getInstance() {
		return parser;
	}

	public String getFilenameFrom() {
		return filenameFrom;
	}

	public void setFilenameFrom(String filenameFrom) {
		this.filenameFrom = filenameFrom;
	}

	public HashMap<String, AllDataRow> getAllDataMap() {
		return allDataMap;
	}

	public void setAllDataMap(HashMap<String, AllDataRow> allDataMap) {
		this.allDataMap = allDataMap;
	}

	public HashMap<String, AllDataRow> plainParse() throws IOException {

		BufferedReader csvReader = new BufferedReader(new FileReader(filenameFrom));
		String row;
		int count = 0;

		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(";");

			String id = data[0]; // это код производителя
			String name = data[1];
			String leftOver = data[2];
			String priceString = data[3];
			int price;

			if (isNullString(id)) {
				continue;
			}
			if (isNullString(leftOver)) {
				continue;
			}
			if (isNullString(priceString)) {
				continue;
			}
			if (isNullString(name)) {
				continue;
			}
			if (leftOver.contains(" ")) {
				continue;
			}

			try {
				price = Integer.valueOf(priceString);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				continue;
			}

			AllDataRow tmpRow = new AllDataRow();
			tmpRow.setId(id);
			tmpRow.setLeftOvers(leftOver);
			tmpRow.setName(name);
			tmpRow.setPrice(price);

			allDataMap.put(id, tmpRow);
			count++;

		}
		csvReader.close();
		System.out.println("The number of RawData rows = " + count);
		return allDataMap;
	}

	@Override
	public String toString() {
		return "RawDataParser [filenameFrom=" + filenameFrom + ", allDataMap=" + allDataMap + "]";
	}

	public boolean isNullString(String string) {
		if (string == null) {
			return true;
		}

		if (string.equals("")) {
			return true;
		}
		if (string.isEmpty()) {
			return true;
		}
		if (("\"" + string + "\"").equals("\"\"")) {
			return true;
		}
		if (string.length() == 0) {
			return true;
		}
		if (string.contains("?")) {
			return true;
		}
		return false;
	}
}
