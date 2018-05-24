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
	private static final float HEALTHLIMIT = 50f;
	
	public CompositeStrategy(MyAIController controller) {
		this.controller = controller;
		
		discoverStrat = new DiscoverStrategy();
		healthStrat = new HealthStrategy(HEALTHLIMIT);
		keyStrat = new KeyStrategy();
		
		currentStrategy = Strategies.DISCOVER;
	}

	public Coordinate update() {
		// first make sure we're not gonna die
		if (controller.getHealth() <= healthStrat.getHealthLimit()) {
			currentStrategy = Strategies.HEALTH;
		} else if (controller.foundNextKey()) {
			// if we have found the next key go get it
			keyStrat.updateKeys(/* key locations */);
			currentStrategy = Strategies.KEY;
		} else {
			discoverStrat.updateMap(controller.getView());
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
}