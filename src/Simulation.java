import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Simulation {
	

	
	private final static int NUM_STEPS = 500;
	private List<Agent> myAgentList;
	
	private Random r = new Random(1237);
	
	
	public static void main(String[] args){
		Simulation s = new Simulation();
		s.setup();
		s.run();
	}
	
	private void setup(){
		myAgentList = createNetwork("infoCenter.txt", "interCenter.txt");
		
		for(Agent a : myAgentList){
			System.out.println(a.myInformationSet + " --- " + a.myInteractionSet);
		}
	}
	
	public List<Agent> createNetwork(String informationNetwork, String interactionNetwork){ 
		
		List<String[]> adjacencies = readFile(informationNetwork);
		
//		for(String[] s : adjacencies){
//			System.out.println(Arrays.toString(s));
//		}
		
		int numAgents = adjacencies.size();
		
		
		
		List<Agent> agents = new ArrayList<Agent>();
		for(int i = 0; i < numAgents; i++){
			agents.add(new Agent(i));
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
		for(int i = 0; i < NUM_STEPS; i++){
			
			if(i < 5){ //randomly assign at first, TODO history data
				
				for(Agent a : myAgentList){
					int action = r.nextInt(2); //random int either 0 or 1
					//if(i == 0) System.out.println(action);
					a.setCurrentAction(action);
					
				}
				
			}
			
			
			for(Agent a : myAgentList){
				a.interactWithAllNeighbors();
			}
			
			
			
//			Agent luckyAgent = myAgentList.get(r.nextInt(myAgentList.size()));
//			luckyAgent.chooseNewAction();
			
			if(i >= 5){
				for(Agent a : myAgentList){
					a.chooseNewAction();
				}
			}
			
			
		}
		
		//end state
		for(Agent a : myAgentList){
			System.out.println(a + ": " + a.getCurrentAction());
		}
		
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
	
	
}
