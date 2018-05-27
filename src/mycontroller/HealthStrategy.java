/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

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
	
	/**
	 * Finds any health tile in the current map.
	 * @return path
	 */
	private Coordinate findAnyHealth() {
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap().equals("health"))) {
				return coord;
			}
		}
		return null;
	}
	
	/**
	 * Finds the closest health to currentPos.
	 * @param currentPos
	 * @return closest health to currentPos
	 */
	private Coordinate findCloseHealth(Coordinate currentPos) {
		Coordinate closestSoFar = null;
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap().equals("health"))) {
				foundHealth = true;
				if (closestSoFar == null) {
					// it's the first interation
					closestSoFar = coord;
				}
				
				// get the "closest" by sum of differences of x and y
				if (((coord.x - currentPos.x) + (coord.y - currentPos.y)) < 
						(closestSoFar.x - currentPos.x) + (closestSoFar.y - currentPos.y)) {
							closestSoFar = coord;
						}
			}
		}
		return closestSoFar;
	}
	
	/**
	 * Check if any health has been found on the map
	 * @return true if health is found
	 */
	public boolean hasFoundHealth() {
		return foundHealth;
	}

	/**
	 * @return lowest Health Limit, the lowest the health can be before changing strat
	 */
	public float getHealthLimit() {
		return HEALTHLIMIT;
	}

	/**
	 * @return the closest health found
	 */
	@Override
	public Coordinate update() {
		//return findAnyHealth();
		return closeHealth;
	}

	/**
	 * Updates current position and finds Health tile closest to the Current Position.
	 * @param currentPos
	 */
	@Override
	public void updateMap(Coordinate currentPos) {
		this.currentPos = currentPos;
		closeHealth = findCloseHealth(currentPos);
	}
	
//////////////////////////////////////////maybe
	public Object destinationReached() {
		// TODO Auto-generated method stub
		return null;
	}
}
