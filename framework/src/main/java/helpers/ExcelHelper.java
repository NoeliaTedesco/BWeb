package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
		for (i = 0; i < excel.size() ; i++) {
			Objects obj = new Objects (excel.get(i));
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
}
