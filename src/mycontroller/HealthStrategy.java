package mycontroller;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class HealthStrategy implements IGoalStrategy {
	
	private static final float HEALTHLIMIT = 95f;
	private Sensor sensor;
	private Coordinate currentPos;
	private boolean foundHealth;
	private Coordinate closeHealth;

	public HealthStrategy(Sensor sensor) {
		this.sensor = sensor;
		foundHealth = false;
		closeHealth = null;
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
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap().equals("health"))) {
				foundHealth = true;
				if (init) {
					closestSoFar = coord;
				}
				//System.out.println("Health coord: " + coord);
				
				// get the "closest" by sum of differences of x and y
				if (((coord.x - currentPos.x) + (coord.y - currentPos.y)) < 
						(closestSoFar.x - currentPos.x) + (closestSoFar.y - currentPos.y)) {
							closestSoFar = coord;
						}
			}
			init = true;
		}
		return closestSoFar;
	}
	
	public boolean hasFoundHealth() {
		return foundHealth;
	}

	public float getHealthLimit() {
		return HEALTHLIMIT;
	}

	@Override
	public Coordinate update() {
		//return findAnyHealth();
		return closeHealth;
	}

	@Override
	public void updateMap(Coordinate currentPos) {
		this.currentPos = currentPos;
		closeHealth = findCloseHealth(currentPos);
	}

	public Object destinationReached() {
		// TODO Auto-generated method stub
		return null;
	}
}
