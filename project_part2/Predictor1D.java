package inclass12;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.*;    // all of the Swing objects
import java.awt.*;       // more windowing components, including Container
import java.io.*;
import java.util.Scanner;

public class Predictor1D {
	private static void initAndShowGUI(Double precision,Double accuracy) {
		// A JFrame is a window.
		String label1=Double.toString(precision);
		String label2=Double.toString(accuracy);
		
		JFrame myFrame = new JFrame(label1+" "+label2);
		
		Container contentPane = myFrame.getContentPane();

		
		contentPane.setLayout(new FlowLayout());
		contentPane.setLayout(new GridLayout(2, 2));
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	
	public static Double x1,x2;
	public static void main(String[] args)throws Exception {
		Scanner input =new Scanner(System.in);
		
		System.out.print("please enter K:");
		int k=input.nextInt();
		
		ArrayList<DataPoint> readlist2 =new ArrayList<DataPoint>();
		KNNPredictor knn = new KNNPredictor(k);
		readlist2=knn.readData(("titanic.csv"));
		//System.out.println(readlist2);
		
		x1=knn.getAccuracy(readlist2);
	    x2=knn.getPrecision(readlist2);
		
		System.out.println("Accuracy:"+x1);
		System.out.println("Precision:"+x2);
		
		DataPoint t = new DataPoint(4.3,5.8,"1",true);
		//System.out.print(t.getIsTest());
	    //knn.test(t);
		
		
		
		SwingUtilities.invokeLater(
		            new Runnable() { public void run() { initAndShowGUI(x1,x2); } }
		          );
		
		
		
		/*
		java.io.File file = new java.io.File("scores.txt");
	    
	    DataPoint[] data = new DataPoint[2];
		data[0] = new DataPoint(1.0,1.0, "Good",true);
		data[1] = new DataPoint(2.0,2.0, "Bad", false);

	    try(java.io.PrintWriter output=new java.io.PrintWriter(file)){
	      output.print(data[0]);
	      output.print(data[1]);
	    }

	    ArrayList<DataPoint> readlist =new ArrayList<DataPoint>();
	    DummyPredictor predictor = new DummyPredictor();
	    readlist=predictor.readData("scores.txt");
	    
	    
	    System.out.println(predictor.test(readlist.get(0)));
	    
	    x1=predictor.getAccuracy(readlist);
	    x2=predictor.getPrecision(readlist);
	    System.out.println(x1);
	    System.out.println(x2);
		
	    
	    SwingUtilities.invokeLater(
	            new Runnable() { public void run() { initAndShowGUI(x1,x2); } }
	          );
	          */
	}

}
