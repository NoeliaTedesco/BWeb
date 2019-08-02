package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;
import base.BaseStep;
import helpers.PageHelper;
import log.Log;

public class Admin_HomePage extends BasePage {

	@FindBy(css = "#selectedSite > span > input")
	private WebElement campoHost;

	@FindBy(css = "#accordion-title-main-nav-administrador-general")
	private WebElement menuAdministradorGeneral;

	@FindBy(id = "main-nav-item-site-browser")
	private WebElement menuNavegadorDeCarpetas;

	@FindBy(id = "main-nav-item-content")
	private WebElement menuBusquedaContenido;

	@FindBy(id = "main-nav-item-workflow")
	private WebElement menuTareas;

	@FindBy(id = "main-nav-item-users")
	private WebElement menuUsuarios;

	@FindBy(id = "main-nav-item-templates")
	private WebElement menuPlantillas;

	@FindBy(id = "main-nav-item-roles")
	private WebElement menuRolesYHerramientas;

	@FindBy(id = "main-nav-item-dashboard")
	private WebElement menuDashboard;

	@FindBy(id = "main-nav-item-containers")
	private WebElement menuContenedores;

	@FindBy(id = "main-nav-item-workflow-schemes")
	private WebElement menuEsquemasFlujos;

	@FindBy(id = "main-nav-item-maintenance")
	private WebElement menuMantenimiento;

	@FindBy(id = "main-nav-item-OSDE_ADMIN_PANEL")
	private WebElement menuOsdeAdminPanel;

	@FindBy(id = "main-nav-item-OSDE_INDEX_PANEL")
	private WebElement menuOsdeIndexPanel;

	@FindBy(className = "portlet-sidebar")
	private WebElement panelLateral;

	@FindBy(css = "#allFieldTB")
	private WebElement campoBuscar;

	@FindBy(id = "searchButton")
	private WebElement btnBuscar;

	@FindBy(id = "contentWrapper")
	private WebElement frameContenido;

	@FindBy(id = "results_table")
	private WebElement tablaResultados;

	@FindBy(id = "mainTabContainer_tablist")
	private WebElement menuContainer;

	@FindBy(className = "loading-wrapper")
	private WebElement loadingWrapper;

	@FindBy(className = "loader__overlay")
	private WebElement loader;

	@FindBy (id = "fm")
	private WebElement formContenido;
	
	@FindBy (className ="editContentletButtonRow")
	private WebElement panelOpcionesEdicion;
	
	@FindBy (xpath = "//a[@onclick = 'cancelEdit();']")
	private WebElement btnCancelar;
	
	@FindBy (className = "wysiwyg-wrapper")
	private WebElement textAreaWrapper;
	
	@FindBy (id = "tinymce")
	private WebElement textArea;
	
	public boolean verificarCargaCorrecta() {
		boolean estaCargado = false;
		try {
			PageHelper.WaitForPageLoading();
			while (PageHelper.elementStillPresent(loadingWrapper)){
				PageHelper.WaitForPageLoading();
			}
			waitFluent.until(ExpectedConditions.elementToBeClickable(campoHost));
			Log.info("Se encuentra cargado correctamente la pagina de admin de Biblos");
			estaCargado = true;
		} catch (Exception e) {
			Log.info("No se encuentra cargado correctamente el admin de Biblos");
			PageHelper.refreshBrowser(driver);
		}
		return estaCargado;
	}

	public void seleccionarHost(String host) {
		try {
			while (!verificarCargaCorrecta()) {
				PageHelper.WaitForPageLoading();
			}
			campoHost.click();
			campoHost.clear();
			campoHost.sendKeys(host);
			WebElement opcionAutocomplete = driver.findElement(By.xpath("//*[@id='selectedSite']/span/div/ul/li[1]"));
			wait.until(ExpectedConditions.elementToBeClickable(opcionAutocomplete));
			opcionAutocomplete.click();
			PageHelper.WaitForPageLoading();
			Log.info("Se selecciona el host de prueba");
		} catch (Exception e) {
			Log.info("Fallo el seleccionar el host de la prueba");
			e.printStackTrace();
		}
	}

	public void clicAdministradorGeneral() {
		try {
			waitFluent.until(ExpectedConditions.elementToBeClickable(menuAdministradorGeneral));
			menuAdministradorGeneral.click();
			Log.info("Se logra hacer clic en el administrador general");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Falla el hacer clic en el administrador general");
		}

	}

	public void seleccionarOpcionMenu(String opcionMenu) {
		try {
			switch (opcionMenu) {
			case "Osde Index Panel":
				wait.until(ExpectedConditions.elementToBeClickable(menuOsdeIndexPanel));
				menuOsdeIndexPanel.click();
				break;

			case "Osde Admin Panel":
				wait.until(ExpectedConditions.elementToBeClickable(menuOsdeAdminPanel));
				menuOsdeAdminPanel.click();
				break;
			case "Mantenimiento":
				wait.until(ExpectedConditions.elementToBeClickable(menuMantenimiento));
				menuMantenimiento.click();
				break;
			case "Esquemas de flujo de trabajo":
				wait.until(ExpectedConditions.elementToBeClickable(menuEsquemasFlujos));
				menuEsquemasFlujos.click();
				break;
			case "Contenedores":
				wait.until(ExpectedConditions.elementToBeClickable(menuContenedores));
				menuContenedores.click();
				break;
			case "Dashboard":
				wait.until(ExpectedConditions.elementToBeClickable(menuDashboard));
				menuContenedores.click();
				break;
			case "Roles y Herramientas":
				wait.until(ExpectedConditions.elementToBeClickable(menuRolesYHerramientas));
				menuRolesYHerramientas.click();
				break;
			case "Plantillas":
				wait.until(ExpectedConditions.elementToBeClickable(menuPlantillas));
				menuPlantillas.click();
				break;
			case "Usuarios":
				wait.until(ExpectedConditions.elementToBeClickable(menuUsuarios));
				menuUsuarios.click();
				break;
			case "Tareas":
				wait.until(ExpectedConditions.elementToBeClickable(menuTareas));
				menuTareas.click();
				break;
			case "Busqueda de contenido":
				wait.until(ExpectedConditions.elementToBeClickable(menuBusquedaContenido));
				menuBusquedaContenido.click();
				break;
			case "Navegador de Carpetas":
				wait.until(ExpectedConditions.elementToBeClickable(menuNavegadorDeCarpetas));
				menuNavegadorDeCarpetas.click();
				break;
			}
			waitFluent.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("detailFrame")));
			Log.info("Se logra seleccionar la opcion del menu");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Falla el seleccionar la opcion de menu");
		}
	}

	public void buscarContenido(String idContenido) {
		try {
			waitFluent.until(ExpectedConditions.elementToBeClickable(panelLateral));
			campoBuscar.click();
			campoBuscar.sendKeys(idContenido);
			wait.until(ExpectedConditions.elementToBeClickable(btnBuscar));
			while (btnBuscar.getAttribute("className").contains("dijitDisabled")) {
				PageHelper.waitImplicit();
			}
			campoBuscar.click();
			PageHelper.waitImplicit();
			seleccionarContenido();
			Log.info("Se logra buscar el contenido");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("Falla el buscar por el contenido");
		}
	}

	//TO DO: Realizar un metodo para encontrar el contenido buscado por algun valor
	//Este metodo siempre selecciona el primer contenido devuelto como resultado de la busqueda
	public void seleccionarContenido() {
		try {
			waitFluent.until(ExpectedConditions.visibilityOf(frameContenido));
			waitFluent.until(ExpectedConditions.visibilityOf(tablaResultados));
			WebElement el = tablaResultados.findElement(By.xpath("./tbody/tr[2]/td[3]"));
			wait.until(ExpectedConditions.elementToBeClickable(el));
			while (el.isDisplayed()) {
				el.click();
				PageHelper.waitImplicit();
			}
			waitFluent.until(ExpectedConditions.elementToBeClickable(menuContainer));
			Log.info("Se logra seleccionar el contenido");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info(e.getMessage());
			Log.info("Falla el seleccionar el contenido");
		}
	}

	public boolean esVisibleURLimagen(String url) {
		Boolean estaPresente = false;
		try {
			waitFluent.until(ExpectedConditions.visibilityOf(textAreaWrapper));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("body_ifr")));
			WebElement imagen = textArea.findElement(By.xpath("//img[contains(@src, '"+url+"')]"));
			estaPresente = imagen.isDisplayed();
			PageHelper.ScrollingToElement(imagen);
		} catch (Exception e) {
			Log.info(PageHelper.checkForError(url));
			Log.info("La URL de la imagen no aparece en la pagina ");
		}
		driver.switchTo().parentFrame();
		return estaPresente;
	}
	
	
	public boolean existeContenido(String idContenido) {
		boolean tieneContenido = false;
		try {
			waitFluent.until(ExpectedConditions.visibilityOf(textAreaWrapper));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("body_ifr")));
			WebElement contenido = textArea.findElement(By.xpath("./table"));
			if (!contenido.getText().isEmpty()) {
			tieneContenido = true;
			PageHelper.ScrollingToElement(contenido);
			Log.info("Se encuentra contenido");
			}
		}catch(Exception e) {
			Log.info(PageHelper.checkForError(idContenido));
			Log.info("No se encuentra contenido");
		}
		return tieneContenido;
	}
	
	
	public void cancelarContenido (String opcionMenu, String urlsite, String host) {
		try {
			driver.switchTo().parentFrame();
			wait.until(ExpectedConditions.elementToBeClickable(btnCancelar));
			btnCancelar.click();
			PageHelper.waitImplicit();
			if (PageHelper.elementStillPresent(btnCancelar)) {
				BaseStep.NavigateToSite(urlsite);
				seleccionarHost(host);
				clicAdministradorGeneral();
				seleccionarOpcionMenu(opcionMenu);
				Log.info("Se vuelve a ingresar a Biblos");
			}
			waitFluent.until(ExpectedConditions.elementToBeClickable(campoBuscar));
			campoBuscar.clear();
		} catch (Exception e) {
			BaseStep.NavigateToSite(urlsite);
			seleccionarHost(host);
			clicAdministradorGeneral();
			seleccionarOpcionMenu(opcionMenu);
			waitFluent.until(ExpectedConditions.elementToBeClickable(campoBuscar));
			campoBuscar.clear();
			Log.info("Se vuelve a ingresar a Biblos");		
		}
	}

}
