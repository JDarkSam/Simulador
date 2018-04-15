package com.simulador.config;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import com.simulador.utils.LocatorType;

public class Common {

	public WebDriver driver;
	public WebDriver driverInmobiliria;

	public WebDriver getDriver() {
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		return driver;
	}

	public WebDriver getDriverInmobiliaria() {
		if (driverInmobiliria == null) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driverInmobiliria = new ChromeDriver();
		}
		return driverInmobiliria;
	}

	// ruta a navegar
	public void navigateTo() {
		getDriver();
		getDriver().manage().window().setPosition(new Point(-10,0));
		getDriver().manage().window().setSize(new Dimension(730,760));
		getDriver().get("http://google.com");
		
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		typeInTextBox(getDriver(),LocatorType.Id, "lst-ib", "Bancolombia");
		clickOnButton(getDriver(),LocatorType.Name, "btnK");
		clickOnLink(getDriver(),LocatorType.LinkText, "Sucursal Virtual Personas");
		getDriver().findElement(By.id("btn_search")).click();
		typeInTextBox(getDriver(),LocatorType.Id, "terminoBusqueda2", "simulador");
		getDriver().findElement(By.cssSelector("input.btn")).click();
		clickOnLink(getDriver(),LocatorType.LinkText, "Simulador Crédito de Consumo");

		getDriverInmobiliaria();
		getDriverInmobiliaria().manage().window().setPosition(new Point(675,0));
		getDriverInmobiliaria().manage().window().setSize(new Dimension(730,760));
		getDriverInmobiliaria().get("http://google.com");
		
		getDriverInmobiliaria().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		typeInTextBox(getDriverInmobiliaria(),LocatorType.Id, "lst-ib", "Bancolombia");
		clickOnButton(getDriverInmobiliaria(),LocatorType.Name, "btnK");
		clickOnLink(getDriverInmobiliaria(),LocatorType.LinkText, "Sucursal Virtual Personas");
		getDriverInmobiliaria().findElement(By.id("btn_search")).click();
		typeInTextBox(getDriverInmobiliaria(),LocatorType.Id, "terminoBusqueda2", "simulador");
		getDriverInmobiliaria().findElement(By.cssSelector("input.btn")).click();
		clickOnLink(getDriverInmobiliaria(),LocatorType.LinkText, "Simulador Solución Inmobiliaria");

	}

	public void typeInTextBox(WebDriver webDriver,LocatorType locatorType, String locator, String textToType) {
		switch (locatorType.toString()) {
		case "Name":
			webDriver.findElement(By.name(locator)).sendKeys(textToType);
			break;
		case "Id":
			webDriver.findElement(By.id(locator)).sendKeys(textToType);
			break;
		}
	}

	public void clickOnLink(WebDriver webDriver,LocatorType locatorType, String locator) {
		switch (locatorType.toString()) {
		case "LinkText":
			webDriver.findElement(By.linkText(locator)).click();
			break;
		}
	}

	public void clickOnButton(WebDriver webDriver,LocatorType locatorType, String locator) {
		switch (locatorType.toString()) {
		case "Name":
			webDriver.findElement(By.name(locator)).click();
			break;
		}
	}

	public String getElementText(WebDriver webDriver,String xpathLoc) {
		return getDriver().findElement(By.xpath(xpathLoc)).getText();
	}

	public void selectFromDropDown(WebDriver webDriver,LocatorType locatorType, String locator, String text) {
		switch (locatorType.toString()) {
		case "Name":
			new Select(webDriver.findElement(By.name(locator))).selectByVisibleText(text);
			break;
		case "Id":
			new Select(webDriver.findElement(By.name(locator))).selectByVisibleText(text);
			break;
		}
	}

}
