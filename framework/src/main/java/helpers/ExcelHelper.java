package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import context.ArchivoExcel;
import context.Objects;

public class ExcelHelper {

	public static List<Objects> objectsExcel;

	public static void mostrarExcelCargado(String testName) throws IOException {
		List<HashMap<String, String>> datos = cargarExcel(testName);
		for (int i = 0; i < datos.size(); i++) {
			System.out.println(datos.get(i).values());

		}

	}

	public static void leerExcelCargado(String testName) throws IOException {
		List<Objects> lista = new ArrayList<Objects>();
		InputStream fs = new FileInputStream("src\\datapool\\" + testName + ".xls");
		PoijiOptions options = PoijiOptionsBuilder.settings(0).build();
		List<ArchivoExcel> excel = Poiji.fromExcel(fs, PoijiExcelType.XLS, ArchivoExcel.class, options);
		int i = 0;
		for (i = 0; i < excel.size(); i++) {
			Objects obj = new Objects(excel.get(i));
			lista.add(obj);
		}
		objectsExcel = lista;
		fs.close();
	}

	public static List<HashMap<String, String>> cargarExcel(String testName) throws FileNotFoundException, IOException {

		List<HashMap<String, String>> misDatos = new ArrayList<HashMap<String, String>>();
		FileInputStream fs = new FileInputStream("src\\datapool\\" + testName + ".xls");
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet = workbook.getSheet(testName);
		Row HeaderRow = sheet.getRow(0);

		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row CurrentRow = sheet.getRow(i);
			HashMap<String, String> currentHash = new HashMap<String, String>();
			for (int j = 0; j < CurrentRow.getPhysicalNumberOfCells(); j++) {
				Cell currentCell = CurrentRow.getCell(j);
				switch (currentCell.getCellType()) {
				case STRING: {
					currentHash.put(HeaderRow.getCell(j).getStringCellValue(), currentCell.getStringCellValue());
					break;
				}
				default:
					break;
				}
			}
			misDatos.add(currentHash);
		}

		return misDatos;
	}

	public static void EscribirExcel(String testName, HashMap<String, String> contenido) throws IOException {

		ArrayList<String> idsContenidos = new ArrayList<String>();
		ArrayList<String> URLS = new ArrayList<String>();

		for (Map.Entry<String, String> entry : contenido.entrySet()) {
			idsContenidos.add(entry.getKey());
			URLS.add(entry.getValue());
		}

		File file = new File("src\\test\\java\\suiteTest\\" + testName + ".xls");

		FileOutputStream webdata = new FileOutputStream(file);
		HSSFWorkbook workbookOut = new HSSFWorkbook();
		HSSFSheet sheet = workbookOut.createSheet();
		HSSFRow rowHeader = sheet.createRow(0);
		rowHeader.createCell(0).setCellValue("Ids contenidos");
		rowHeader.createCell(1).setCellValue("URLS");
		
		for (int i = 0; i < idsContenidos.size(); i++) {	
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(idsContenidos.get(i));
			row.createCell(1).setCellValue(URLS.get(i));
		}

		workbookOut.write(webdata);
		webdata.close();
	}

}
