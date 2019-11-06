package parsers;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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

	public HashMap<String, AllDataRow> Parse() throws IOException {
		int count = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(filenameFrom));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader());) {
			for (CSVRecord csvRecord : csvParser) {

				// Accessing Values by Column Index
				String id = csvRecord.get(0); // это код производителя
				String name = csvRecord.get(1);
				String leftOver = csvRecord.get(2);
				String priceString = csvRecord.get(3);

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
		}
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
		return false;
	}
}
