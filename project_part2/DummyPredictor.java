package inclass12;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class DummyPredictor extends Predictor{
	
	private double F1Avg;
	private double F2Avg;
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
		if(Math.abs(data.getF1() - this.F1Avg) > Math.abs(data.getF2() - this.F2Avg)) {
			
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
}
