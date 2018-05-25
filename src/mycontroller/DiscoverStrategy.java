package mycontroller;

import java.util.ArrayList;

import tiles.MapTile;
import utilities.Coordinate;

public class DiscoverStrategy implements IGoalStrategy {

	private Sensor sensor;
	
	public DiscoverStrategy(Sensor sensor) {
		this.sensor = sensor;
	}

	
	@Override
	public Coordinate update() {
		return new Coordinate(1,9);
	}

	@Override
	public void updateMap(Coordinate currentPos) {
		
	}
}
