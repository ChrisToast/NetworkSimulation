import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
 
 
public class Grapher {

	public Grapher(){
		 mySeries = new XYChart.Series();
		 mySeries2 = new XYChart.Series();
		 mySeries3 = new XYChart.Series();
		 mySeries4 = new XYChart.Series();
		 mySeries5 = new XYChart.Series();
		 
		 mySeriesMap.put(0, mySeries);
		 mySeriesMap.put(1, mySeries2);
		 mySeriesMap.put(2, mySeries3);
		 mySeriesMap.put(3, mySeries4);
		 mySeriesMap.put(4, mySeries5);
	}
	
	private XYChart.Series mySeries;
	private XYChart.Series mySeries2;
	private XYChart.Series mySeries3;
	private XYChart.Series mySeries4;
	private XYChart.Series mySeries5;
	
	private Map<Integer, XYChart.Series> mySeriesMap = new HashMap<Integer, XYChart.Series>();
	
    public Scene graph() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis(-30,15,5);
        final NumberAxis yAxis = new NumberAxis(0,1,.1);
//        xAxis.setLabel("Update #");
        xAxis.setLabel("b");
        yAxis.setLabel("Avg Proportion of agents playing 1 after step 1000");
    	
//        final NumberAxis xAxis = new NumberAxis(0,1,.10);
//        final NumberAxis yAxis = new NumberAxis(300,500,10);
//        xAxis.setLabel("Gamma");
//        yAxis.setLabel("Average convergence step");

        
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        //lineChart.setTitle("Title");
        lineChart.setCreateSymbols(false);
        //lineChart.setStyle("-fx-stroke: rgba(100,100,100)");
        
        
        //mySeries.setName("Convergence vs Discount Value");
        //populating the series with data

        
        Scene scene  = new Scene(lineChart,800,600);
        
        for(Integer i : mySeriesMap.keySet()){
        	 lineChart.getData().add(mySeriesMap.get(i));
        }
        
       
        return scene;
    }
 
    public void addDataPoint(double x, double y, int which){
    	XYChart.Series cur = mySeriesMap.get(which);
    	cur.getData().add(new XYChart.Data(x, y));
    	mySeriesMap.put(which, cur);
    }
    
    private void setUpChart(XYChart.Series series){
    	
    }
    
}