import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class Simulation {
	
	private double last = 0;
	
	private final static int NUM_STEPS = 10000;
	private static final int NUM_AGENTS = 100;
	private static final int NUM_SIMS = 1;
	
	private static final double CONNECT_PROB_INFO = 0.2;
	private static final double CONNECT_PROB_INTER = 0.2;
	
	private static final double PROB_ONE_START = 0.5;
	
	private double discount_factor = 0.0;
	
	private Random r = new Random(1234);
	
	
	private static final String FILENAME_INFO = "infoRandom.txt";
	private static final String FILENAME_INTER = "interRandom.txt";
	private List<Agent> myAgentList;
	private Grapher myGrapher;
	
	public void go(){
		myGrapher = new Grapher();
		generateRandomGraph(FILENAME_INFO, NUM_AGENTS, true);
		generateRandomGraph(FILENAME_INTER, NUM_AGENTS, false);
		
//		for(discount_factor = 0; discount_factor <= 1.0; discount_factor += 0.01){
//			run();
//		}
//		discount_factor = 1.0;
//		run();

		
		//TODO graph individual simulations
		run();
		
		
		Stage stage = new Stage();
		Scene scene = myGrapher.graph();
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	private void setup(){
		myAgentList = createNetwork(FILENAME_INFO, FILENAME_INTER);
		
//		for(Agent a : myAgentList){
//			System.out.println(a.myInformationSet + " --- " + a.myInteractionSet);
//		}
	}
	
	public List<Agent> createNetwork(String informationNetwork, String interactionNetwork){ 
		
		List<String[]> adjacencies = readFile(informationNetwork);
		
//		for(String[] s : adjacencies){
//			System.out.println(Arrays.toString(s));
//		}
		
		int numAgents = adjacencies.size();
		
		
		
		List<Agent> agents = new ArrayList<Agent>();
		for(int i = 0; i < numAgents; i++){
			agents.add(new Agent(i, discount_factor));
		}
		
		for(int i = 0; i < adjacencies.size(); i++){
			for(int j = 0; j < adjacencies.size(); j++){
				if(adjacencies.get(i)[j].equals("1")){
					agents.get(i).addAgentToInfoNetwork(agents.get(j));
				}
			}
		}
		
		adjacencies = readFile(interactionNetwork);
		
		
		for(int i = 0; i < adjacencies.size(); i++){
			for(int j = 0; j < adjacencies.size(); j++){
				if(adjacencies.get(i)[j].equals("1")){
					agents.get(i).addAgentToInteractionNetwork(agents.get(j));
				}
			}
		}

		return agents;
	}
	
	public void run(){
		double stepEndedAt = 0;
		
		for(int i = 0; i < NUM_SIMS; i++){
			setup();
			stepEndedAt += runOneSimulation();
		}
		
		double avgStepEndedAt = stepEndedAt / NUM_SIMS;
		
		//myGrapher.addDataPoint(discount_factor, avgStepEndedAt);
		
		System.out.println("Discount: " + this.discount_factor + " Average step ended at: " + avgStepEndedAt);
	}
	
	
	public int runOneSimulation(){
		for(int i = 0; i < NUM_STEPS; i++){

			
			if(i < 10){ //randomly assign at first, TODO history data
				
				for(Agent a : myAgentList){
					//TODO figure out distribution
					
					
					//give % of pop 1 at start
					double rand = r.nextDouble();
					if(rand < PROB_ONE_START){
						a.setCurrentAction(1);
					}
					else{
						a.setCurrentAction(0);
					}
					
//					int action = r.nextInt(2); //random int either 0 or 1
//					a.setCurrentAction(action);
					
				}
				
			}
			
			//agents all play game
			for(Agent a : myAgentList){
				a.interactWithAllNeighbors();
			}

			
			if(i >= 10){
				
				if(i == 10){
					int a = 0;
					a++;
				}
				
				Agent luckyAgent = myAgentList.get(r.nextInt(myAgentList.size()));
				luckyAgent.chooseNewAction();
				
				
//				for(Agent a : myAgentList){
//					a.chooseNewAction();
//				}
				
			}
			
			
			//check state at end of each step
			double numZero = 0.0;
			double numOne = 0.0;
			for(Agent a : myAgentList){
				if(a.getCurrentAction() == 0){
					numZero++;
				}
				else if(a.getCurrentAction() == 1){
					numOne++;
				}
			}
			double percentOnes = numOne / myAgentList.size();
			
			if(percentOnes < last && i > 10){
				int a = 0;
				a++;
			}
			
			last = percentOnes;
			myGrapher.addDataPoint(i, percentOnes);
			
			
			
			
			//System.out.println("% 1 : " + percentOnes);
			if(percentOnes == 1.0){
				//System.out.println("Stopped with all ones at step " + i);
				return i;
			}
			
			
		}
		return NUM_STEPS;
		//end state
//		for(Agent a : myAgentList){
//			System.out.println(a + ": " + a.getCurrentAction());
//		}
		
	}
	
	private void step(){
		
	}
	
	private List<String[]> readFile(String filename){
		Path filePath = Paths.get(filename);
		Scanner scanner = null;
		try {
			scanner = new Scanner(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String[]> adjacencies = new ArrayList<String[]>();
		while (scanner.hasNextLine()) {
		    if (scanner.hasNextLine()) {
		    	//System.out.print("A");
		    	//System.out.println(scanner.next());
		    	adjacencies.add(scanner.nextLine().split(" "));
		        
		    } else {
		        scanner.next();
		    }
		}
		scanner.close();
		return adjacencies;
	}
	
	private void generateRandomGraph(String filename, int numAgents, boolean isInformation){
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[][] adjacency = new String[numAgents][numAgents];
		
		for(int i = 0; i < numAgents; i++){
			
			if(isInformation){
				adjacency[i][i] = "1";
			}
			else{ //interaction
				adjacency[i][i] = "0";
			}
			
			for(int j = i+1; j < numAgents; j++){
				
				//String rando = Integer.toString(r.nextInt(2));
				
				double rand = r.nextDouble();
				String connected;
				
				
				if(isInformation){
					if(rand < CONNECT_PROB_INFO){
						connected = "1";
					}
					else{
						connected = "0";
					}
				}
				else{
					if(rand < CONNECT_PROB_INTER){
						connected = "1";
					}
					else{
						connected = "0";
					}
				}
				
				//must be symmetrical
				adjacency[i][j] = connected;
				adjacency[j][i] = connected;
			}
		}
		
		for(int i = 0; i < numAgents; i++){
			for(int j = 0; j < numAgents; j++){
				writer.print(adjacency[i][j]);
				
				if(j != numAgents - 1){
					writer.print(" ");
				}
				
			}
			if(i != numAgents - 1){
				writer.print("\n");
			}
		}
		
		writer.close();
		
	}
	
	
}
