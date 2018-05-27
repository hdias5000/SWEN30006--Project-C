/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import java.util.ArrayList;

import tiles.LavaTrap;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class KeyStrategy implements IGoalStrategy {
	
	private static final int MAX_KEYS = 100;

	private Sensor sensor;
	private Coordinate[] keyCoords;
	private int currentKey;
	
	public KeyStrategy(Sensor sensor) {
		this.sensor = sensor;
		keyCoords = new Coordinate[MAX_KEYS];
	}
	
	/**
	 * Check if the next key has been found
	 * @param currentKey
	 * @return true if the next key is found 
	 */
	public boolean foundNextKey(int currentKey) {
		if (keyCoords[currentKey - 1] != null) {
			this.currentKey = currentKey;
			return true;
		}
		return false;
	}
	
	/**
	 * Finds any keys in the map.
	 * @return true if a key is found
	 */
	private boolean checkMapForKey() {
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap().equals("lava"))) {
				int key = ((LavaTrap) tile).getKey();
				if (key > 0) {
					keyCoords[key] = coord;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return coordinate of next key
	 */
	@Override
	public Coordinate update() {
		if (foundNextKey(currentKey)) {
			return keyCoords[currentKey - 1];
		}
		else {
			return null;
		}
	}
	
	/**
	 * Checks if the map contains any keys.
	 */
	@Override
	public void updateMap(Coordinate currentPos) {
		checkMapForKey();
	}
	
	//////////////////////////////////////////maybe
	public Object destinationReached() {
		// TODO Auto-generated method stub
		return null;
	}
}
