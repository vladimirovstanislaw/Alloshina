package conf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import get.data.GetApiData;
import parsers.RawDataParser;
import rows.AllDataRow;

public class Runable {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static File logFile;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if (args.length != 0) {
			String rootDirectory = args[0]; // ���� ������ .csv - �������� � �����������
			String fileNameUpload = args[1]; // ��� ������������� �����
			String linkToData = args[2]; // ������ �� ������ ��������
			String rawDataFile="Raw.csv";
			
			logFile = new File(rootDirectory + "\\log.txt");

			HashMap<String, AllDataRow> allDataMap = new HashMap<>();

			// ������� ������ �� api
			GetApiData getData = new GetApiData(allDataMap, linkToData);
			String rawData = getData.getData();
			if (rawData == null) {
				logSth("Exception: ApiData unreached " + sdf.format(new Date()));
				throw new Exception("Old data from 1C");
			}

			// ���������� ������ � ����
			BufferedWriter writer = new BufferedWriter(new FileWriter(rootDirectory + "\\" + rawDataFile));
			writer.write(rawData);
			writer.close();

			// ������ ������
			File rawFile = new File(rootDirectory + "\\" + rawDataFile);

			RawDataParser parser = RawDataParser.getInstance();

			parser.setAllDataMap(allDataMap);
			parser.setFilenameFrom(rootDirectory + "\\" + rawDataFile);

			allDataMap = parser.Parse();

			allDataMap.forEach((k, v) -> {
				System.out.println("Id= \"" + v.getId() + "\" Name=\"" + v.getName() + "\" Leftovers=\""
						+ v.getLeftOvers() + "\" Price=\"" + v.getPrice() + "\"");
			});

		}

	}

	public static void logSth(String log) throws IOException {
		if (!logFile.canRead()) {
			logFile.createNewFile();
			FileWriter fr = new FileWriter(logFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);
			pr.println(log);
			pr.close();
			br.close();
			fr.close();
		} else {
			FileWriter fr = new FileWriter(logFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);
			pr.println(log);
			pr.close();
			br.close();
			fr.close();
		}
	}

}
