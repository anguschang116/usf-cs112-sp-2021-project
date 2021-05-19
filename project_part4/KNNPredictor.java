
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
//import java.util.Arrays;

public class KNNPredictor extends Predictor{
	private int K;
	private int p0 = 0;
	private int p1 = 0;
	ArrayList<DataPoint> dataset= new  ArrayList<DataPoint>();
	
	public KNNPredictor (int K) {
		
		if (K%2 == 1) {
			this.K = K;
		}
		else {
			System.out.println("Wrong input");
		}
	}
	
	private static ArrayList<String> getRecordFromLine(String line) {
		ArrayList<String> values = new ArrayList<String>(); 
		try (Scanner rowScanner = new Scanner(line)) {
		rowScanner.useDelimiter(","); while (rowScanner.hasNext()) {
		values.add(rowScanner.next()); }
		}
		    return values;
		}
	
	
	public ArrayList<DataPoint>  readData(String filename) {
		ArrayList<DataPoint> AList =new ArrayList<DataPoint>();
	
		try {
			Scanner scanner = new Scanner(new File(filename));
			String title =scanner.nextLine();
			while (scanner.hasNextLine()) {
			ArrayList<String> records = getRecordFromLine(scanner.nextLine());
				//System.out.println(records);
					if ( records.size()==7 && !((records.get(5).equals("")) || (records.get(6).equals("")))) {
						Double f1 =Double.parseDouble(records.get(5));
						Double f2 =Double.parseDouble(records.get(6));
						
						
						Random rand = new Random();
						double randNum = rand.nextDouble();

						if (randNum <= 0.9) {
							if(records.get(1).equals("0")) {
								this.p0 = this.p0+1;
							}
							else {
								this.p1= this.p1+1;
							}
							DataPoint listObject = new DataPoint(f1,f2,records.get(1),false);
							AList.add(listObject);
							this.dataset.add(listObject);
						} 
							
						else {
							DataPoint listObject = new DataPoint(f1,f2,records.get(1),true);
							AList.add(listObject);
						}
					}
				} 
			System.out.println("The number of 1 is:"+ this.p1);
			System.out.println("The number of 0 is:"+ this.p0);
			}
		catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
		}
		//System.out.print("dataset!!!!!!!!!!!!!!!!!!!");
		//System.out.println(this.dataset);
		//System.out.println(this.dataset.get(0).getF2());
		return AList;
	}
	private double getDistance(DataPoint p1, DataPoint p2) {
		return Math.sqrt(Math.pow(p1.getF1()-p2.getF1(), 2)+ Math.pow(p2.getF2()-p2.getF2(), 2));
	}	
	public String test(DataPoint data) {
		Double [][] arr=new Double[this.dataset.size()][2];
		if (data.getIsTest()) {
			for(int i=0 ;i<this.dataset.size();i++) {
				arr[i][0]=this.getDistance(data, this.dataset.get(i));
				//System.out.println(this.getDistance(data, this.dataset.get(i)));
				Double tmp =Double.parseDouble(this.dataset.get(i).getLabel());
				arr[i][1]=tmp;
			}
		}
		else {
			return "type wrong";
		}
		
		java.util.Arrays.sort(arr, new java.util.Comparator<Double[]>() { 
			public int compare(Double[] a, Double [] b) {
				return a[0].compareTo(b[0]);
			} 
		});
		
		int K0 = 0;
		int K1 =0;
		for(int i=0 ;i< this.K;i++) {
			if (arr [i][1] == 0 ) {
				K0 += 1;
			}
			else {
				K1+= 1;
			}
		}
		
		//System.out.println("K0=" + K0);
		//System.out.println("K1=" + K1);
		if (K0 > K1) {
			return "0";
		}
		else {
			return "1";
		}
		
		/*
		for(int i=0 ;i<this.dataset.size();i++) {
			System.out.println(arr[i][0]+" "+arr[i][1]);
		}*/
	}
	Double getAccuracy(ArrayList<DataPoint> data) {
		double truePositive = 0.0;
		double falsePositive = 0.0;
		double falseNegative = 0.0;
		double trueNegative = 0.0;
		for(int i=0 ;i<data.size();i++) {
			if (data.get(i).getIsTest()) {
			
				if(this.test(data.get(i)).equals("1") && data.get(i).getLabel().equals("1")) {
					truePositive +=1;
				}
				else if (this.test(data.get(i)).equals("1") && data.get(i).getLabel().equals("0")) {
					falsePositive +=1;
				}
				else if (this.test(data.get(i)).equals("0") && data.get(i).getLabel().equals("1")) {
					falseNegative +=1;
				}
				else if (this.test(data.get(i)).equals("0") && data.get(i).getLabel().equals("0")) {
					trueNegative +=1;
				}	
			}
		}
		return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
		
		
	}


	Double getPrecision(ArrayList<DataPoint> data) {
		double truePositive = 0.0;
		double falseNegative = 0.0;
		
		for(int i=0 ;i<data.size();i++) {
			if(this.test(data.get(i)).equals("1") && data.get(i).getLabel().equals("1")) {
				truePositive +=1;
			}
			else if (this.test(data.get(i)).equals("0") && data.get(i).getLabel().equals("1")) {
				falseNegative +=1;
			}
		}
		return truePositive / (truePositive + falseNegative);
	}
	
}
	
	

