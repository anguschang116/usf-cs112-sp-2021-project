
import java.util.ArrayList;

abstract class Predictor {
	
	 abstract ArrayList<DataPoint> readData(String filename);
	
	 abstract String test(DataPoint data);
	
	 abstract  Double getAccuracy(ArrayList<DataPoint> data);
	
	 abstract Double getPrecision(ArrayList<DataPoint> data);
}
