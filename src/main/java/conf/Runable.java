package conf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import configureFiles.Nomenclature;
import configureFiles.Upload;
import get.data.GetApiData;
import parsers.RawDataParser;
import rows.AllDataRow;
import sendData.Sender;

public class Runable {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static File logFile;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if (args.length != 0) {
			String rootDirectory = args[0]; // ���� ������ .csv - �������� � �����������
			String fileNameUpload = args[1]; // ��� ������������� �����
			String linkToData = args[2]; // ������ �� ������ ��������
			String rawDataFile = "Raw.csv";

			logFile = new File(rootDirectory + "\\log.txt");

			HashMap<String, AllDataRow> allDataMap = new HashMap<>();

			// ������� ������ �� api

			System.out.println("Get data from API..");
			GetApiData getData = new GetApiData(allDataMap, linkToData);
			String rawData = getData.getData();
			if (rawData == null) {
				logSth("Exception: ApiData unreached " + sdf.format(new Date()));
				throw new Exception("Old data from 1C");
			}

			File rawFile = new File(rootDirectory + "\\" + rawDataFile);

//			 ���������� ������ � ����
			BufferedWriter writer = new BufferedWriter(new FileWriter(rootDirectory + "\\" + rawDataFile));
			writer.write(rawData);
			writer.close();

			System.out.println("Parsing..");
			// ������ ������
			RawDataParser parser = RawDataParser.getInstance();

			parser.setAllDataMap(allDataMap);
			parser.setFilenameFrom(rootDirectory + "\\" + rawDataFile);

			allDataMap = parser.plainParse();

			System.out.println("Creating nomenclture file..");
			// ������� ���� �����������
			Date date = new Date();

			String nomenclatureFileName = rootDirectory + "\\Nomenclature_" + date.getDate() + "_" + date.getMonth()
					+ "_" + (date.getYear() + 1900) + ".csv";
			Nomenclature nom = new Nomenclature(allDataMap, nomenclatureFileName);
			nom.writeFile();

			System.out.println("Configure final data file..");
			// ������� ���� ������, �� ����� ����� ���������� �� ��� sftp

			Upload up = new Upload(allDataMap, (rootDirectory + "\\" + fileNameUpload));
			up.writeFile();

			System.out.println("Sending data..");
			// ���������� ������
			Sender sender = new Sender();
			sender.setData(rootDirectory, fileNameUpload);
			sender.send();
			System.out.println("Done. \u203E\\( \u25CF , \u25CF)/\u203E");

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
