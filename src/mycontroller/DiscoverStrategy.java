package mycontroller;

import java.util.ArrayList;
import java.util.Random;

import tiles.MapTile;
import utilities.Coordinate;

public class DiscoverStrategy implements IGoalStrategy {

	private Sensor sensor;
	private Coordinate destCoord;
	
	public DiscoverStrategy(Sensor sensor) {
		this.sensor = sensor;
		this.destCoord = new Coordinate(6,15);
		getNewDest();
	}

	
	@Override
	public Coordinate update() {
		System.out.println("in Discover update: " + 
	sensor.getCurrentMap().get(new Coordinate(1,9)).getType());
		return destCoord;
	}

	@Override
	public void updateMap(Coordinate currentPos) {
		
	}
	
	public void getNewDest() {
		Coordinate coord;
		boolean done = false;
		Random random = new Random();
		ArrayList<Coordinate> keys      = new ArrayList<Coordinate>(sensor.getNotVisited().keySet());
		while (!done) {
			coord = keys.get( random.nextInt(keys.size()) );
			MapTile tile = sensor.getCurrentMap().get(coord);
			if ((tile != null)&&((tile.isType(MapTile.Type.ROAD)))) {
				done=true;
				destCoord = coord;
				
			}
			
		}
	}


	public void destinationReached() {
		getNewDest();
	}
}
