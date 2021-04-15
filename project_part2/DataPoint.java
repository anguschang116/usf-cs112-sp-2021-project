package inclass12;

public class DataPoint {
	private Double f1;
	private Double f2
	;
	private String label;
	private Boolean isTest;
	
	public DataPoint(Double f1,Double f2, String label, Boolean isTest) {
		this.f1 =f1;
		this.f2 =f2;
		this.label=label;
		this.isTest = isTest;
	}
	
	public DataPoint() {
		this.f1 =0.0;
		this.f2 =0.0;
		this.label = null;
		this.isTest = false;
	}
	
	public Double getF1() {
		return this.f1;
	}
	public Double getF2() {
		return this.f2;
	}
	public String getLabel() {
		return this.label;
	}
	public Boolean getIsTest() {
		return this.isTest;
	}
	
	
	public void setF1(Double f1) {
		if (f1 <0) {
			return;
		}
		this.f1 =f1;
	}
	public void setF2(Double f2) {
		if (f2 <0) {
			return;
		}
		this.f2 =f2;
	}
	
	public void setLabel(String label) {
		if (!(label.equals("Good")|| label.equals("Bad"))) {
			return;
		}
		this.label = label;
	}
	public void setisTest(Boolean isTest) {
		this.isTest = isTest;
	}
	public String toString() {
		return Double.toString(this.getF1()) +" "+ Double.toString(this.getF2()) +" "+this.getLabel()+" "+ this.getIsTest()+"\n";
	}
}
