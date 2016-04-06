import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class Grapher {

	public Grapher(){
		 mySeries = new XYChart.Series();
	}
	
	private XYChart.Series mySeries;
	
    public Scene graph() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis(0,10000,500);
        final NumberAxis yAxis = new NumberAxis(0,1,.1);
        xAxis.setLabel("Update #");
        yAxis.setLabel("Proportion of agents playing 1");

        
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Title");
        lineChart.setCreateSymbols(false);
     
        mySeries.setName("Convergence vs Discount Value");
        //populating the series with data

        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(mySeries);
       
        return scene;
    }
 
    public void addDataPoint(double x, double y){
    	mySeries.getData().add(new XYChart.Data(x, y));
    }
    
    private void setUpChart(XYChart.Series series){
    	
    }
    
}