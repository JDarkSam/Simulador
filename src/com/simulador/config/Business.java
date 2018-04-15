package com.simulador.config;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.simulador.utils.LocatorType;
import com.simulador.utils.WriteToExcel;

public class Business extends Common {
	
	public void addDatosSimulacionConsumo(WebDriver webDriver,String... datosimulacionconsumo) {

		selectFromDropDown(webDriver,LocatorType.Name, "comboTipoSimulacion", datosimulacionconsumo[0]);
		typeInTextBox(webDriver,LocatorType.Name, "dateFechaNacimiento", datosimulacionconsumo[1]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboTipoTasa", datosimulacionconsumo[2]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboTipoProducto","Crédito de Libre Inversión"); // datosimulacionconsumo[3]);
		clickOnButton(webDriver,LocatorType.Name, "checkSeguroDesempleo");
		typeInTextBox(webDriver,LocatorType.Name, "textPlazoInversion", datosimulacionconsumo[4]);
		typeInTextBox(webDriver,LocatorType.Name, "textValorPrestamo", datosimulacionconsumo[5]);
	}

	public String submitSimulacionConsumo() {
		getDriver().findElement(By.cssSelector("button.btn.btn-default.btn-primary")).click();
		String resultado = getDriver().findElement(By.xpath("//div[@id='sim-results']/div/table/tbody/tr[6]/td[2]"))
				.getText();
		return resultado;
	}
	
	public void addDatosSimulacionInmobiliaria(WebDriver webDriver,String... datosimulacioninmobiliaria) {

		selectFromDropDown(webDriver,LocatorType.Name, "combotipoFinanciacion","Crédito Hipotecario");//datosimulacioninmobiliaria[0]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboDestinoCredito",datosimulacioninmobiliaria[1]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboOpcionSimular",datosimulacioninmobiliaria[2]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboPlanAmortizacion",datosimulacioninmobiliaria[3]);
		typeInTextBox(webDriver,LocatorType.Name, "textPlazoAnios", datosimulacioninmobiliaria[4]);
		typeInTextBox(webDriver,LocatorType.Name, "dateFechaNacimiento", datosimulacioninmobiliaria[5]);
		selectFromDropDown(webDriver,LocatorType.Name, "comboDeptoColomnbia",datosimulacioninmobiliaria[6]); 
		typeInTextBox(webDriver,LocatorType.Name, "textValorBien", datosimulacioninmobiliaria[7]);
		typeInTextBox(webDriver,LocatorType.Name, "textValorPrestamo", datosimulacioninmobiliaria[8]);

	}
	
	public String submitSimulacionInmobiliaria() {
		Float resultado;
		getDriverInmobiliaria().findElement(By.cssSelector("button.btn.btn-default.btn-primary")).click();
		String cuotasin = getDriverInmobiliaria().findElement(By.xpath("//*[@id=\"sim-results\"]/div[1]/table/tbody/tr[17]/td[4]"))
				.getText();
		String iva = getDriverInmobiliaria().findElement(By.xpath("//*[@id=\"sim-results\"]/div[1]/table/tbody/tr[21]/td[2]"))
				.getText();
		String seguro = getDriverInmobiliaria().findElement(By.xpath("//*[@id=\"sim-results\"]/div[1]/table/tbody/tr[22]/td[2]"))
				.getText();
		
		cuotasin=cuotasin.replace("$", "").replaceAll(",", "");
		iva=iva.replace("$", "").replaceAll(",", "");
		seguro=seguro.replace("$", "").replaceAll(",", "");
		resultado=Float.parseFloat(cuotasin)+Float.parseFloat(iva)+Float.parseFloat(seguro);
		
		return resultado.toString();
	}
	
	public void ReporteExcel(String cuotaConsumo, String cuotaHipotecario, String mejorCuota) {
		String[] valueToWrite = { "Cuota", cuotaConsumo,cuotaHipotecario,mejorCuota };

		WriteToExcel objExcelFile = new WriteToExcel();
		try {
			objExcelFile.writeExcel(System.getProperty("user.dir") + "\\testData", "ExportExcel.xlsx", "Resultados",
					valueToWrite);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
