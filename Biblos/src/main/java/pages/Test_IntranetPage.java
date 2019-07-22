package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
import helpers.PageHelper;
import log.Log;

public class Test_IntranetPage extends BasePage {

	@FindBy(name = "sUsername")
	private WebElement campoUsuario;

	@FindBy(name = "sPassword")
	private WebElement campoContrasenia;

	@FindBy(name = "submit")
	private WebElement btnIngresar;

	public void ingresarTestIntranet(String usuario, String contrasenia) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(campoUsuario));
			campoUsuario.click();
			campoUsuario.sendKeys(usuario);
			campoContrasenia.click();
			campoContrasenia.sendKeys(contrasenia);
			btnIngresar.click();
			PageHelper.WaitForPageLoading();
			Log.info("Se ingresa a la testIntranet");
		} catch (Exception e) {
			Log.info("Fallo al ingresar a la testIntranet");
			e.printStackTrace();
		}
	}
}
