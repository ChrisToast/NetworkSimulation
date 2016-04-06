
import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	private final static int NUM_AGENTS = 100;
	private final static int NUM_GENS = 1500;
	private final static int NUM_PAIRINGS = 3;
	
	public static void main(String[] args){
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Random r = new Random();
		Simulation runner = new Simulation();
		runner.go();
	
	}
	
}
