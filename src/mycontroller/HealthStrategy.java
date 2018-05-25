package mycontroller;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class HealthStrategy implements IGoalStrategy {
	
	private static final float HEALTHLIMIT = 75f;
	private VisitNodes sensor;
	private Coordinate currentPos;

	public HealthStrategy(VisitNodes sensor) {
		this.sensor = sensor;
	}
	
	private Coordinate findAnyHealth() {
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap() == "health")) {
				//System.out.println("Health coord: " + coord);
				return coord;
			}
		}
		return null;
	}
	
	private Coordinate findCloseHealth(Coordinate currentPos) {
		// init is to initialise closestSoFar to the first coord in the current map
		boolean init = false;
		Coordinate closestSoFar = null;
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			if (!init) {
				closestSoFar = coord;
			}
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap() == "health")) {
				//System.out.println("Health coord: " + coord);
				
				// get the "closest" by sum of differences of x and y
				if (((coord.x - currentPos.x) + (coord.y - currentPos.y)) < 
						(closestSoFar.x - currentPos.x) + (closestSoFar.y - currentPos.y)) {
							coord = closestSoFar;
						}
			}
			init = true;
		}
		return closestSoFar;
	}

	public float getHealthLimit() {
		return HEALTHLIMIT;
	}

	@Override
	public Coordinate update() {
		//return findAnyHealth();
		return findCloseHealth(currentPos);
	}
	

	@Override
	public void updateMap(Coordinate currentPos) {
		this.currentPos = currentPos;
	}

}
