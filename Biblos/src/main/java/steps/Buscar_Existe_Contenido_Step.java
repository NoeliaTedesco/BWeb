package steps;

import static config.DataSetter.configuration;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jfree.util.Log;
import org.testng.Assert;

import base.BaseStep;
import context.ArchivoExcel;
import context.Usuario;
import delivery.EmailSenderConfiguration;
import helpers.CSVHelper;
import helpers.ExcelHelper;
import helpers.PageHelper;
import helpers.StepHelper;
import helpers.XMLHelper;
import pages.Admin_HomePage;
import pages.Test_IntranetPage;

public class Buscar_Existe_Contenido_Step extends BaseStep {
	public static void Run(String testName) throws IOException {
		String nombrePaso = "";
		List<String> idContenidosConTexto;
		List<String> idContenidosSinTextos;
				
		try {
			log.Log.startTestCase(testName);
			Usuario usr = XMLHelper.object.getUsuario();
			try {
				ExcelHelper.leerExcelCargado(testName);
			} catch (IOException e) {
				log.Log.info(e.getMessage());
				log.Log.FailStep("error al leer el excel");
				Assert.fail("Fallo test - " + testName + " - Step: " + "Carga de datos");
			}
			
			List<context.Objects> listadoExcel =  ExcelHelper.objectsExcel;
			
			NavigateToSite(configuration.geturlTestIntranet());
			CurrentPage = (new Test_IntranetPage().GetInstance(Test_IntranetPage.class));

			nombrePaso = "01_Se ingresa a la test Intranet";
			CurrentPage.As(Test_IntranetPage.class).ingresarTestIntranet(usr.getUsser(), usr.getPassword());
			log.Log.SuccessStep(nombrePaso);

			nombrePaso = "02_Abrir nueva ventana e ingresar al admin de Biblos - Desarrollo";
			openNewTab(configuration.geturlDesarrolloWindow());
			log.Log.SuccessStep(nombrePaso);

			nombrePaso = "03_Se selecciona Host y se hace clic en AdministradorGeneral";
			CurrentPage = (new Admin_HomePage().GetInstance(Admin_HomePage.class));
			CurrentPage.As(Admin_HomePage.class).seleccionarHost(XMLHelper.object.getHost());
			CurrentPage.As(Admin_HomePage.class).clicAdministradorGeneral();
			log.Log.SuccessStep(nombrePaso);
			
			nombrePaso = "04_Ingresa a la opcion del menu Busqueda de contenido";
			CurrentPage.As(Admin_HomePage.class).seleccionarOpcionMenu("Busqueda de contenido");
			log.Log.SuccessStep(nombrePaso);

			nombrePaso = "05_Se busca cada contenido del documento";
			int i = 0;
			String contenidoPresente;
			ArchivoExcel ex = null;
			ArchivoExcel excelId = listadoExcel.get(0).getexcel();
			CurrentPage.As(Admin_HomePage.class).buscarContenido(excelId.getIdContenido());
			
			for (i=0; i < listadoExcel.size(); i++){
				
				 ex = listadoExcel.get(i).getexcel();
				
				 if (!ex.getIdContenido().equals(excelId.getIdContenido())){
					excelId = ex;
					CurrentPage.As(Admin_HomePage.class).cancelarContenido("Busqueda de contenido", configuration.geturlDesarrolloWindow(),XMLHelper.object.getHost());
					CurrentPage.As(Admin_HomePage.class).buscarContenido(excelId.getIdContenido());
				 }
				 				 
				if (CurrentPage.As(Admin_HomePage.class).existeContenido(ex.getIdContenido())) {
					contenidoPresente = "Se encuentra contenido la pagina";
					idContenidosConTexto = Arrays.asList(excelId.getIdContenido()); 
				} else {
					contenidoPresente = "No se encuentra contenido en el TextArea";
					idContenidosSinTextos = Arrays.asList(excelId.getIdContenido());
					StepHelper.takeScreenShot(excelId.getIdContenido() , " -Desarrollo");
					CSVHelper.EscribirCSV("Ids_sin_Contenido", idContenidosSinTextos);

				}
				
				log.Log.info("Para el id:" + excelId.getIdContenido() +  "-" + contenidoPresente);

				
			}
			
			CSVHelper.cerrarArchivoCSV();
			log.Log.SuccessStep(nombrePaso);

		} catch (AssertionError ex) {
			log.Log.info(ex.getMessage());
			log.Log.FailStep(nombrePaso);
			Assert.fail("Fallo test - " + testName + " - Step: " + nombrePaso);
		}

		log.Log.endTestCase(testName);
	}
}
