package ConversorDual;

public class CalcularTemperatura {
	private double numTemp;
	private String cbxSelectemp1;
	private String cbxSelectemp2;
	
	public double getNumTemp() {
		return numTemp;
	}
	public void setNumTemp(double numTemp) {
		numTemp = numTemp;
	}
	public String getCbxSelectemp1() {
		return cbxSelectemp1;
	}
	public void setCbxSelectemp1(String cbxSelectemp1) {
		this.cbxSelectemp1 = cbxSelectemp1;
	}
	public String getCbxSelectemp2() {
		return cbxSelectemp2;
	}
	public void setCbxSelectemp2(String cbxSelectemp2) {
		this.cbxSelectemp2 = cbxSelectemp2;
	}
	
	public CalcularTemperatura(double numTemp, String comboBoxa, String comboBoxb) {
		this.numTemp = numTemp;
		this.cbxSelectemp1 = comboBoxa;
		this.cbxSelectemp2 = comboBoxb;
	}
	
	public double CalcularTemperatura() {
		double temp = 0;
		if (cbxSelectemp1 == cbxSelectemp2) {
			temp = numTemp;
		} else if (cbxSelectemp1 == "CELCIUS" && cbxSelectemp2 == "KELVIN" ) {
			temp = numTemp + 273.15;
		} else if (cbxSelectemp1 == "CELCIUS" && cbxSelectemp2 == "FAHRENHEIT" ) {
			temp = (numTemp *(9.0/5.0)) +32;
		} else if (cbxSelectemp1 == "KELVIN" && cbxSelectemp2 == "CELCIUS" ) {
			temp = numTemp - 273.15;
		} else if (cbxSelectemp1 == "KELVIN" && cbxSelectemp2 == "FAHRENHEIT" ) {
			temp = (numTemp -273.15) * (9.0/5.0) + 32;
		} else if (cbxSelectemp1 == "FAHRENHEIT" && cbxSelectemp2 == "CELCIUS" ) {
			temp = (numTemp - 32) * (5.0/9.0);
		} else if (cbxSelectemp1 == "FAHRENHEIT" && cbxSelectemp2 == "KELVIN" ) {
			temp = (numTemp - 32) *(5.0/9.0) + 273.15;
		}
		return temp;		
	}
	
	
	


}
