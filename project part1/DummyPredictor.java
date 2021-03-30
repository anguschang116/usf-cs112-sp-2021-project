package inclass12;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class DummyPredictor extends Predictor{
	

	@Override
	public ArrayList<DataPoint> readData(String filename) {
		ArrayList<DataPoint> AList =new ArrayList<DataPoint>();
		
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNext()) {
				String a=scanner.next();
				String b=scanner.next();
				String c=scanner.next();
				String d=scanner.next();
				
				Double f1 =Double.parseDouble(a);
				Double f2 =Double.parseDouble(b);
				Boolean isTest =Boolean.parseBoolean(d);
				
				DataPoint listObject = new DataPoint(f1,f2,c,isTest);
				AList.add(listObject);
				
				//System.out.println(listObject);
				
			}
		}
		
		catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
		}
		
		return AList;
	}
	
	@Override
	String test(DataPoint data) {
		// TODO Auto-generated method stub
		double a = Math.abs(data.getF1()-data.getF2());
		if (a>0) {
			return "Good";
		}
		else {
			return "Bad";
		}
		
	}

	
	@Override
	Double getAccuracy(ArrayList<DataPoint> data) {
		// TODO Auto-generated method stub
		return 2.2;
	}

	@Override
	Double getPrecision(ArrayList<DataPoint> data) {
		// TODO Auto-generated method stub
		return 3.3;
	}
	
	
	
	
	/*private Double greenAvg;
	private Double blueAvg;
	public void readData(DataPoint[] data) {
		Double greenAvg = 0.0;
		Double blueAvg = 0.0;
		for (int i=0; i < data.length; i++) {
			DataPoint d =data[i];
			Double f =d.getF1();
			String label = d.getLabel();
		}
		this.greenAvg =greenAvg;
		this.blueAvg =blueAvg;
	}
	
	public String test(DataPoint data) {
		Math.abs(data.getF1()-this.greenAvg);
		Math.abs(data.getF1()-this.blueAvg);
		return "Something...";
	}*/
}
