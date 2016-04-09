import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Agent {
	
	public List<Agent> myInformationSet;
	public List<Agent> myInteractionSet;
	
	private int myCurrentAction;
	private double myCurrentOutcome;
	
	//private int myBank; TODO this?
	
	private List<Integer> myPastActions;
	private List<Double> myPastOutcomes;
	
	private int myGroup; //TODO
	
//	private int[][] gameMatrix = new int[][] {{1,0}, 
//			  								  {0,5}};
			  								  
//	private int[][] gameMatrix = new int[][] {{0,10}, 
//			  								  {10,-20}}; //lower bottom right # and the end state will hover around a smaller number....linear relationship????
			  								  
	private double[][] gameMatrix;
	
//	private int[][] gameMatrix = new int[][] {{0,1}, 
//	  										  {-1,0}};
			  								  
//	private int[][] gameMatrix = new int[][] {{0,1}, 
//				  						      {1,0}};	
	
			  							
	
	private static final int NUM_PAST_ACTIONS_CONSIDERED = 10;
	
	
	private int myId;
	private double discountFactor;
	
	public Agent(int id, double discount, double[][] matrix){
		myId = id;
		discountFactor = discount;
		myInformationSet = new ArrayList<Agent>();
		myInteractionSet = new ArrayList<Agent>();
		myPastActions = new ArrayList<Integer>();
		myPastOutcomes = new ArrayList<Double>();
		gameMatrix = matrix;
	}
	
	public void chooseNewAction(){
		myCurrentAction = IMHistory(discountFactor);
		//myCurrentAction = IM();
		//myCurrentAction = BR(.5);
	}
	
	public void interactWithAllNeighbors(){
		
		double total = 0;
		
		for(Agent other : myInteractionSet){
			double utility = this.interactWith(other, gameMatrix);
			total += utility;
		}
		myCurrentOutcome = total / (myInteractionSet.size());
		
		myPastActions.add(myCurrentAction);
		myPastOutcomes.add(myCurrentOutcome);
		
	}
	
	
	private double interactWith(Agent other, double[][] game){
		return game[myCurrentAction][other.myCurrentAction]; //TODO check indexing, ij or ji 
	}
	
	private int BR(double threshold){ //TODO only works with coordination game
		Map<Integer, Integer> neighborsActions = new HashMap<Integer, Integer>();
		for(Agent a : myInformationSet){
			
			//System.out.println(this + ": " + a + ": " + a.myPastActions);
			
			
			int action = a.myPastActions.get( a.myPastActions.size() - 1);
			
			if(!neighborsActions.containsKey(action)){
				neighborsActions.put(action, 1);
			}
			else{
				neighborsActions.put(action, neighborsActions.get(action) + 1);
			}
		}
		
		for(Integer act : neighborsActions.keySet()){
			
			double proportion = (double) neighborsActions.get(act) / (double) myInformationSet.size();
			if(proportion >= threshold){
				return act;
			}
			
		}
		
		return 0; //TODO
		
		
	}
	
	private int IM(){
		return IMHistory(0.0);
	}
	
	
	
	private int IMHistory(double discount){
		Map<Integer, Double> neighborsActions = new HashMap<Integer, Double>();
		Map<Integer, Double> neighborsActionsCount = new HashMap<Integer, Double>();
		for(Agent a : myInformationSet){
			
			//look at last choices
			for(int j = 0; j < NUM_PAST_ACTIONS_CONSIDERED; j++){
				
				int action = a.myPastActions.get( a.myPastActions.size() - 1 - j);
				double result = Math.pow(discount, j) *  a.myPastOutcomes.get(a.myPastOutcomes.size() - 1 - j);
				
				if(!neighborsActions.containsKey(action)){
					neighborsActions.put(action, result);
					neighborsActionsCount.put(action, 1.0);
				}
				else{
					neighborsActions.put(action, neighborsActions.get(action) + result);
					neighborsActionsCount.put(action, neighborsActionsCount.get(action) + 1.0);
				}
				
			}
			
			
		}
		
		//average all
		for(int action : neighborsActions.keySet()){
			
			neighborsActions.put(action, neighborsActions.get(action) / neighborsActionsCount.get(action));
			
		}
		
		//find best
		int curBestAction = 0;
		double curBestOutcome = 0;
		for(int possibleAction : neighborsActions.keySet()){
			
			if(neighborsActions.get(possibleAction) > curBestOutcome){
				curBestOutcome = neighborsActions.get(possibleAction);
				curBestAction = possibleAction;
			} else if(neighborsActions.get(possibleAction) == curBestOutcome){
				//if they're equal, choose one with largest amount of players
				if(neighborsActionsCount.get(possibleAction) > neighborsActionsCount.get(curBestAction)){
					
					curBestAction = possibleAction;
					
				}
				
			}
			
			
		}
		return curBestAction;
		
	}
	
	
	public void addAgentToInfoNetwork(Agent a){
		this.myInformationSet.add(a);
	}
	
	public void addAgentToInteractionNetwork(Agent a){
		this.myInteractionSet.add(a);
	}
	
	public String toString(){
		return Integer.toString(myId);
	}
	
	public void setCurrentAction(int action){
		myCurrentAction = action;
	}
	
	public int getCurrentAction(){
		return myCurrentAction;
	}
}
