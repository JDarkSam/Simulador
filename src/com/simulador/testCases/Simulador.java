package com.simulador.testCases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import com.simulador.config.Business;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.simulador.utils.ReadToExcel;

public class Simulador extends Business {

	String valorConsumo, valorHipotecario, mejorCuota;
	Float fvalorConsumo, fvalorHipotecario;

	Object[][] testObjArray;
	String testCaseWorkBook = System.getProperty("user.dir") + "\\testData\\DatosSimulacion.xlsx";

	@DataProvider(name = "DatosSimulacionConsumo")
	public Object[][] datosConsumo(Method m) throws Exception {
		return (testObjArray = ReadToExcel.getTableArray(testCaseWorkBook, "Consumo"));
	}

	@DataProvider(name = "DatosSimulacionInmobiliaria")
	public Object[][] datosInmobiliaria(Method m) throws Exception {
		return (testObjArray = ReadToExcel.getTableArray(testCaseWorkBook, "Inmobiliaria"));
	}

	@BeforeTest
	public void setUp() {
		navigateTo();
	}

	@AfterTest
	public void tearDown() {
		fvalorConsumo = Float.parseFloat(valorConsumo.replace("$", "").replaceAll(",", ""));
		fvalorHipotecario = Float.parseFloat(valorHipotecario.replace("$", "").replaceAll(",", ""));
		
		if (fvalorConsumo > fvalorHipotecario) {
			mejorCuota = valorHipotecario;
		} else {
			mejorCuota = valorConsumo;
		}
		ReporteExcel(valorConsumo, valorHipotecario, mejorCuota);
		getDriver().quit();
		getDriverInmobiliaria().quit();
	}

	@BeforeMethod
	public void clickRegister() {
		getDriver().navigate().refresh();
		getDriverInmobiliaria().navigate().refresh();
	}

	@Test(dataProvider = "DatosSimulacionConsumo")
	public void infoSimulacionConsumo(String... dataProvider) {
		String[] datoSimulaConsumo = new String[6];

		for (int i = 0; i < 6; i++) {
			datoSimulaConsumo[i] = dataProvider[i];
		}

		addDatosSimulacionConsumo(getDriver(), datoSimulaConsumo);
		
		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "\\testData\\screenshot_cargadatosconsumo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		valorConsumo = submitSimulacionConsumo();

		scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "\\testData\\screenshot_simuladoconsumo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test(dataProvider = "DatosSimulacionInmobiliaria")
	public void infoSimulacionInmobiliaria(String... dataProvider) {
		String[] datoSimulaInmobiliaria = new String[9];

		for (int i = 0; i < 9; i++) {
			datoSimulaInmobiliaria[i] = dataProvider[i];
		}

		addDatosSimulacionInmobiliaria(getDriverInmobiliaria(), datoSimulaInmobiliaria);

		File scrFile = ((TakesScreenshot) getDriverInmobiliaria()).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "\\testData\\screenshot_cargadatoshipotecario.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		valorHipotecario = submitSimulacionInmobiliaria();
		scrFile = ((TakesScreenshot) getDriverInmobiliaria()).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile,
					new File(System.getProperty("user.dir") + "\\testData\\screenshot_simuladohipotecario.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
