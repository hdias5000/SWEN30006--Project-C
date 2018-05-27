/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tiles.LavaTrap;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class Sensor {
	private HashMap<Coordinate, MapTile> currentMap;
	private HashMap<Coordinate, MapTile> notVisited;
	private HashMap<Coordinate, MapTile> visited; 
	private MyAIController controller;
	
	
	public Sensor(HashMap<Coordinate, MapTile> map, MyAIController controller) {
		this.currentMap = new HashMap<Coordinate, MapTile>();
		this.notVisited = new HashMap<Coordinate, MapTile>();
		addToMap(this.currentMap, map);
		addToMap(this.notVisited, map);
		this.visited = new HashMap<Coordinate, MapTile>();
		this.controller = controller;
	}
	
	/**
	 * Stores all the nodes seen by the radar of the car in visited and removes them from NotVisited. This function also informs AIController if it finds a key.
	 * @param currentView
	 */
	public void addToSeen(HashMap<Coordinate, MapTile> currentView) {
		Set<?> set = currentView.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			Coordinate currentCoord = (Coordinate) mentry.getKey();
			MapTile currentTile = (MapTile) mentry.getValue();
			if (this.notVisited.containsKey(currentCoord)) {
				this.notVisited.remove(currentCoord);
				this.visited.put(currentCoord,currentTile);
				this.currentMap.remove(currentCoord);
				this.currentMap.put(currentCoord,currentTile);
			}
		}
	}

	
	/**
	 * Copies all instances in one HashMap to another.
	 * @param appendMap
	 * @param newMap
	 */
	private void addToMap(HashMap<Coordinate, MapTile> appendMap,HashMap<Coordinate, MapTile> newMap) {
		Set<?> set = newMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			appendMap.put((Coordinate) mentry.getKey(), (MapTile) mentry.getValue());
		}
	}

	/**
	 * 
	 * @return currentMap
	 */
	public HashMap<Coordinate, MapTile> getCurrentMap() {
		return this.currentMap;
	}
	
	/**
	 * 
	 * @return notVisited coordinates
	 */
	public HashMap<Coordinate, MapTile> getNotVisited() {
		return this.notVisited;
	}
	
	
}
