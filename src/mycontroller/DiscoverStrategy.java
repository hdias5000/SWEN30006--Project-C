package mycontroller;

import java.util.ArrayList;

import tiles.MapTile;
import utilities.Coordinate;

public class DiscoverStrategy implements IGoalStrategy {

	private VisitNodes sensor;
	
	public DiscoverStrategy(VisitNodes sensor) {
		this.sensor = sensor;
	}

	
	@Override
	public Coordinate update() {
		return new Coordinate(10,2);
	}

	@Override
	public void updateMap() {
		
	}
}
