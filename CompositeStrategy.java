package mycontroller;

import utilities.Coordinate;

public class CompositeStrategy implements IGoalStrategy{
	
	public enum Strategies {
		DISCOVER,
		HEALTH,
		KEY;
	}

	private Strategies currentStrategy;
	private DiscoverStrategy discoverStrat;
	private HealthStrategy healthStrat;
	private KeyStrategy keyStrat;
	private VisitNodes sensor;
	public CompositeStrategy(MyAIController controller) {
		this.sensor = sensor;
		
		discoverStrat = new DiscoverStrategy();
		healthStrat = new HealthStrategy();
		keyStrat = new KeyStrategy();
		

		currentStrategy = Strategies.DISCOVER;
	}

	public Coordinate update() {
		//first make sure we're not gonna die
		if (/*health*/)
		// if we have found the next key go get it
		else if (sensor.foundNextKey) {
			currentStrategy = Strategies.KEY;
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