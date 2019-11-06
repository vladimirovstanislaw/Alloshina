package conf;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import get.data.GetApiData;
import rows.AllDataRow;

public class Runable {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		if (args.length != 0) {
			String rootDirectory = args[0]; // ���� ������ .csv - �������� � �����������
			String fileNameUpload = args[1]; // ��� ������������� �����
			String linkToData = args[2]; // ������ �� ������ ��������
			File logFile = new File(rootDirectory + "\\log.txt");

			HashMap<String, AllDataRow> allDataMap = new HashMap<>();

			GetApiData getData = new GetApiData(allDataMap, linkToData);
			getData.getData();
		}

	}

}
