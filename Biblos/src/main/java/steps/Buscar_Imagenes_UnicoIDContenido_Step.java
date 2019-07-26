package steps;

import static config.DataSetter.configuration;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
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
import helpers.ExcelHelper;
import helpers.PageHelper;
import helpers.StepHelper;
import helpers.XMLHelper;
import pages.Admin_HomePage;
import pages.Test_IntranetPage;

public class Buscar_Imagenes_UnicoIDContenido_Step extends BaseStep {
	public static void Run(String testName) throws IOException {
		String nombrePaso = "";
		HashMap<String, String> idContenidosConImagenes = new HashMap<String, String>();
		HashMap<String, String> idContenidosSinImagenes = new HashMap<String, String>();
				
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

			nombrePaso = "04_Ingresa a la opcion del menu Busqueda de contenido";
			CurrentPage.As(Admin_HomePage.class).seleccionarOpcionMenu("Busqueda de contenido");
			log.Log.SuccessStep(nombrePaso);

			nombrePaso = "05_Se busca cada contenido del documento";
			int i = 0;
			String imagenPresente = "No se encuentra imagen presente";
			ArchivoExcel ex = null;
			ArchivoExcel excelId = listadoExcel.get(0).getexcel();
			CurrentPage.As(Admin_HomePage.class).buscarContenido(excelId.getIdContenido());
			
			for (i=0; i < listadoExcel.size(); i++){
				
				 ex = listadoExcel.get(i).getexcel();
				 System.out.println(ex.getUrl());
				 
				if (CurrentPage.As(Admin_HomePage.class).esVisibleURLimagen(ex.getUrl())) {
					imagenPresente = "Se encuentra la imagen en la pagina";
					idContenidosConImagenes.put(excelId.getIdContenido(), ex.getUrl());
					StepHelper.takeScreenShot(excelId.getIdContenido(), "Desarrollo_" + i);
				} else {
					imagenPresente = "No se encuentra imagen presente";
					idContenidosSinImagenes.put(excelId.getIdContenido(), ex.getUrl());
					//StepHelper.takeScreenShot(excelId.getIdContenido(), "Desarrollo_" + i);
				}
				
				log.Log.info("Para el id:" + excelId.getIdContenido() + "_" +  ex.getUrl() + "-" + imagenPresente );
				//CurrentPage.As(Admin_HomePage.class).cancelarContenido("Busqueda de contenido", configuration.geturlDesarrolloWindow(),XMLHelper.object.getHost());
			}
			
			ExcelHelper.EscribirExcel("Imagenes_Presentes", idContenidosConImagenes);
			ExcelHelper.EscribirExcel("Imagenes_NO_Presentes", idContenidosSinImagenes);

			
			log.Log.SuccessStep(nombrePaso);

		} catch (AssertionError ex) {
			log.Log.info(ex.getMessage());
			log.Log.FailStep(nombrePaso);
			Assert.fail("Fallo test - " + testName + " - Step: " + nombrePaso);
		}

		log.Log.endTestCase(testName);
	}
}
