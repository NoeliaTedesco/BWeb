package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.util.Log;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import context.ArchivoExcel;
import context.Objects;

public class CSVHelper {

	public static List<Objects> objectsCSV;
	private static boolean archivoCreado = false;
	private static FileWriter csvWriter;

	public static void crearArchivoCSV(String testName) {
		try {
			csvWriter = new FileWriter("src\\test\\java\\suiteTest\\" + testName + ".csv");
			csvWriter.append("ID");
			//csvWriter.append(",");
			//svWriter.append("URL IMAGEN");
			csvWriter.append("\n");
			archivoCreado = true;
		} catch (Exception e) {
			log.Log.info("Se crea el archivo CSV");
		}
	}

	public static void EscribirCSV(String testName, List<String> contenidos) throws IOException {

		if (!archivoCreado) {
			crearArchivoCSV(testName);
		}
		csvWriter.append(String.join(",", contenidos));
		csvWriter.append("\n");
		csvWriter.flush();

	}
	
	public static void cerrarArchivoCSV( ) {
		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.Log.info("Se cierra archivo");
		}
	}

}
