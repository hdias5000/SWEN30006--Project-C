package mycontroller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tiles.LavaTrap;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class VisitNodes {
	private HashMap<Coordinate, MapTile> currentMap;
	private HashMap<Coordinate, MapTile> notVisited;
	private HashMap<Coordinate, MapTile> visited; 
	private CompositeStrategy strategy;
	
	
	public VisitNodes(HashMap<Coordinate, MapTile> map, CompositeStrategy strategy) {
		this.currentMap = new HashMap<Coordinate, MapTile>();
		this.notVisited = new HashMap<Coordinate, MapTile>();
		addToMap(this.currentMap, map);
		addToMap(this.notVisited, map);
		this.visited = new HashMap<Coordinate, MapTile>();
		this.strategy = strategy;
	}
	
	
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
				if ((currentTile.isType(MapTile.Type.TRAP)) && (((TrapTile) currentTile).getTrap() == "lava")) {
					int key = ((LavaTrap) currentTile).getKey();
					if (key>0) {
						this.strategy.informKey(key, currentCoord);
					}
				}
			}
		}
	}

	
	private void addToMap(HashMap<Coordinate, MapTile> appendMap,HashMap<Coordinate, MapTile> newMap) {
		Set<?> set = newMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			appendMap.put((Coordinate) mentry.getKey(), (MapTile) mentry.getValue());
		}
	}

	
	public HashMap<Coordinate, MapTile> getCurrentMap() {
		return this.currentMap;
	}
	
	public HashMap<Coordinate, MapTile> getNotVisited() {
		return this.notVisited;
	}
	
	
}
