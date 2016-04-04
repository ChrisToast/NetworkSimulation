import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Agent {
	
	private List<Agent> myInformationSet;
	private List<Agent> myInteractionSet;
	
	private int currentAction;
	private int currentOutcome;
	
	private List<Integer> myPastActions;
	private List<Integer> myPastOutcomes;
	
	
	public int chooseNewAction(){
		
	}
	
	
	public int interactWith(Agent other, int[][] gameMatrix){
		
		return gameMatrix[currentAction][other.currentAction]; //TODO check indexing, ij or ji 
		
	}
	
	private int BR(double threshold){ //TODO only works with coordination game
		Map<Integer, Integer> neighborsActions = new HashMap<Integer, Integer>();
		for(Agent a : myInformationSet){
			int action = a.myPastActions.get(myPastActions.size() - 1);
			
			if(!neighborsActions.containsKey(action)){
				neighborsActions.put(action, 1);
			}
			else{
				neighborsActions.put(action, neighborsActions.get(action) + 1);
			}
		}
		
		for(Integer act : neighborsActions.keySet()){
			
			double proportion = neighborsActions.get(act) / myInformationSet.size();
			if(proportion >= threshold) return act;
			
		}
		
		return 0; //TODO
		
		
	}
	
	private int IM(){
		
	}
}
