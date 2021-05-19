import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;


import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Graph extends JPanel {
    public JButton b1;

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color red = new Color(255, 0, 0);
    private Color blue = new Color(0,0,255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255,255,0);
    
    
    

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private List<DataPoint> data;

    // TODO: Add a private KNNPredictor variable
    private KNNPredictor k1 ;
    
    
    
    
    
    
    
	/**
	 * Constructor method
	 */
    public Graph(int K, String fileName) {
    	this.k1=new KNNPredictor(K);
    	
    	
        // Generate random data point
        List<DataPoint> data = new ArrayList<>();
        
        ArrayList<DataPoint> readlist2 =new ArrayList<DataPoint>();
        /*
        Random random = new Random();
        int maxDataPoints = 20;
        // Max value of f1 and f2 that is arbitrarily set
        int maxF1 = 8;
        int maxF2 = 300;

        // Generates random DataPoints
        for (int i = 0; i < maxDataPoints; i++) {
            double f1 = (random.nextDouble() * maxF1);
            double f2 = (random.nextDouble() * maxF2);
            data.add(new DataPoint(f1, f2, "Random", "Random"));
        }
        this.data = data;*/

        
        // Generate random data point
        List<DataPoint> train_data = new ArrayList<>();
        List<DataPoint> test_data = new ArrayList<>();
        
        /*Random random = new Random();
        int maxDataPoints = 20;
        // Max value of f1 and f2 that is arbitrarily set
        int maxF1 = 8;
        int maxF2 = 300;*/
        
        
        readlist2=this.k1.readData(("titanic.csv"));
		
		
		for(int i=0;i<readlist2.size();i++) {
			if(readlist2.get(i).getIsTest()) {
				test_data.add(readlist2.get(i));
			}
			else {
				train_data.add(readlist2.get(i));
			}
		}
		
		this.data=test_data;
		
		for(int i=0;i<this.data.size();i++) {
			System.out.println(this.data.get(i));
		}
		
        // TODO: Remove the above logic where random data is generated
        // TODO: instantiate the KNNPredictor variable
        // TODO: Run readData using input filename to split the data to test and training
        // TODO: Set this.data as the output of readData
		
		
		
		

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;

            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            // Note that depending on how you implemented "test" method, you may need to 
            // modify KNNPredictor to store the output from readData.
            // You can also optimize further to compute accuracy and precision in a single
            // iteration.
            
            for(int j=0;j<this.data.size();j++) {
            	if(this.k1.test(this.data.get(i)).equals("1") && this.data.get(i).getLabel().equals("1")) {
            		g2.setColor(blue);
            	}
            	else if(this.k1.test(this.data.get(i)).equals("1") && this.data.get(i).getLabel().equals("0")) {
            		g2.setColor(cyan);
            	}
            	else if(this.k1.test(this.data.get(i)).equals("0") && this.data.get(i).getLabel().equals("1")) {
            		g2.setColor(yellow);
            	}
            	else if(this.k1.test(this.data.get(i)).equals("0") && this.data.get(i).getLabel().equals("0")) {
            		g2.setColor(red);
            	}
            }
            
            
            
            /*
            if (i % 2 == 0) {
                g2.setColor(pointColor);
            } else {
                g2.setColor(red);
            }*/
            g2.fillOval(x, y, ovalW, ovalH);
        }

    }

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(List<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(int K, String fileName,Double precision,Double accuracy) {
    	
    	String label1=Double.toString(precision);
		String label2=Double.toString(accuracy);
		
		//JFrame myFrame = new JFrame(label1+" "+label2);
		
		JButton b1 = new JButton("Run Test");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");
       

	    /* Main panel */
        Graph mainPanel = new Graph(K, fileName);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3"+" "+label1+" "+label2);
        
        Container contentPane = frame.getContentPane();

		//Create the label.
        JLabel sliderLabel = new JLabel("Choose the majority value", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        
        
        //Create the slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
                                              2, 25, 5);
        b1.addActionListener(this);
        
        mainPanel.add(framesPerSecond);
        mainPanel.add(sliderLabel);
        mainPanel.add(b1);
        //framesPerSecond.addChangeListener(this);
 
        //Turn on labels at major tick marks.
 
        framesPerSecond.setMajorTickSpacing(5);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);

        
        
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
    }

    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
 
            b1.setEnabled(false);
           
        } else {
            b1.setEnabled(true);
        
        }
    }
    public static Double x1,x2;
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
        int K = 7; // A value of K selected    
        String fileName = "titanic.csv"; // TODO: Change this to titanic.csv
        
        KNNPredictor knn=new KNNPredictor(K);
        
        ArrayList<DataPoint> readlist =new ArrayList<DataPoint>();
		readlist=knn.readData(("titanic.csv"));
        
        x1=knn.getAccuracy(readlist);
	    x2=knn.getPrecision(readlist);
		
		System.out.println("Accuracy:"+x1);
		System.out.println("Precision:"+x2);
        
        
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(K, fileName,x1,x2);
            }
        });
    }
}
