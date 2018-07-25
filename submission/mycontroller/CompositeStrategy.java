/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import utilities.Coordinate;

public class CompositeStrategy implements IGoalStrategy{
	
	private enum Strategies {
		DISCOVER,
		HEALTH,
		KEY;
	}

	private Strategies currentStrategy;
	private DiscoverStrategy discoverStrat;
	private HealthStrategy healthStrat;
	private KeyStrategy keyStrat;
	private MyAIController controller;
	
	public CompositeStrategy(MyAIController controller) {
		this.controller = controller;
		
		discoverStrat = new DiscoverStrategy(controller.getSensor());
		healthStrat = new HealthStrategy(controller.getSensor());
		keyStrat = new KeyStrategy(controller.getSensor());
		
		currentStrategy = Strategies.DISCOVER;
	}
	
	/**
	 * Called by AIController to choose a strategy.
	 * @return destination coordinate depending on the strategy
	 */
	public Coordinate update() {
		// update the sensor map for all the strats so they can preprocess it
		updateMap(new Coordinate(controller.getPosition()));

		// first make sure we're not gonna die, get health if we know where health is
		if ((controller.getHealth() <= healthStrat.getHealthLimit()) && healthStrat.hasFoundHealth()) {
			currentStrategy = Strategies.HEALTH;
		} else if (keyStrat.foundNextKey(controller.getKey())) {
			// if we have found the next key go get it
			currentStrategy = Strategies.KEY;
		} else {
			currentStrategy = Strategies.DISCOVER;
		}
		
		switch(currentStrategy) {
			case DISCOVER:
				return discoverStrat.update();
			case HEALTH:
				return healthStrat.update();
			case KEY:
				return keyStrat.update();
			default:
				return discoverStrat.update();
		}
	}
	
	/**
	 * Updates the maps for all the strategies.
	 * @param currentPos
	 */
	@Override
	public void updateMap(Coordinate currentPos) {
		discoverStrat.updateMap(currentPos);
		healthStrat.updateMap(currentPos);
		keyStrat.updateMap(currentPos);
	}
	
}