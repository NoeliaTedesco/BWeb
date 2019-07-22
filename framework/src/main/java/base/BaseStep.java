package base;

import java.util.ArrayList;

import org.jfree.util.Log;
import org.openqa.selenium.JavascriptExecutor;

import helpers.PageHelper;

public class BaseStep extends Base {

	private static ArrayList<String> tabs;

	public static void NavigateToSite(String urlsite) {
		driver.navigate().to(urlsite);
	}

	public static void openNewTab(String newPage) {
		try {
		((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));
		NavigateToSite(newPage);
			PageHelper.WaitForPageLoading();
		} catch (InterruptedException e) {
			Log.info("Falla el abrir una nueva pestana");
			e.printStackTrace();
		}
	}

	public static void switchToTab(String url) {
		tabs = new ArrayList<String>(driver.getWindowHandles());
		try {
			for (String tab : tabs) {
				driver.switchTo().window(tab);
				PageHelper.WaitForPageLoading();
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl.contains(url)) {
					Log.info("Se cambia el foco a la ventana correspondiente");
					break;
				}
			}
		} catch (Exception e) {
			Log.info("Falla cambiar el foco de la ventana");
		}

	}

}
