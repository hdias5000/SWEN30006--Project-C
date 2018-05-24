package mycontroller;

import utilities.Coordinate;

public class HealthStrategy implements IGoalStrategy {
	
	private static final float HEALTHLIMIT = 50f;
	private VisitNodes sensor;

	public HealthStrategy(VisitNodes visitNodes) {
		this.sensor = sensor;
	}

	public float getHealthLimit() {
		return HEALTHLIMIT;
	}

	@Override
	public Coordinate update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMap() {
		// TODO Auto-generated method stub
		
	}

}
