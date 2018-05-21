package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;

public class VisitNodes {
	private HashMap<Coordinate, MapTile> visited; 
	public VisitNodes(HashMap<Coordinate, MapTile> map) {
		this.visited = map;
	}

	public void addToMap(HashMap<Coordinate, MapTile> currentView) {
		
	}

}
