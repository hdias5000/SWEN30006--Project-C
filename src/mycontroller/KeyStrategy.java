package mycontroller;

import java.util.ArrayList;

import tiles.LavaTrap;
import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class KeyStrategy implements IGoalStrategy {

	private VisitNodes sensor;
	private Coordinate[] keyCoords;
	private int currentKey;
	
	public KeyStrategy(VisitNodes sensor) {
		this.sensor = sensor;
	}
	
	public boolean foundNextKey(int currentKey) {
		if (keyCoords[currentKey - 1] != null) {
			return true;
		}
		return false;
	}
	
	private boolean checkMapForKey() {
		for (Coordinate coord: sensor.getCurrentMap().keySet()) {
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile.isType(MapTile.Type.TRAP)) && (((TrapTile) tile).getTrap() == "lava")) {
				int key = ((LavaTrap) tile).getKey();
				if (key > 0) {
					keyCoords[key] = coord;
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public Coordinate update() {
		return keyCoords[currentKey - 1];
	}

	@Override
	public void updateMap() {
		checkMapForKey();
	}
}
