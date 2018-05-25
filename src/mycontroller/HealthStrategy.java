package mycontroller;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class HealthStrategy implements IGoalStrategy {
	
	private static final float HEALTHLIMIT = 60f;
	private VisitNodes sensor;
	
	

	public HealthStrategy(VisitNodes sensor) {
		this.sensor = sensor;
	}
	
	private Coordinate findAnyHealth() {
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap() == "health")) {
				return coord;
			}
		}
		return null;
	}

	public float getHealthLimit() {
		return HEALTHLIMIT;
	}

	@Override
	public Coordinate update() {
		return findAnyHealth();
	}
	

	@Override
	public void updateMap() {
	}

}
