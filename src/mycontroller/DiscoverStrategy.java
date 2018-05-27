/* GROUP NUMBER: 71
	 * NAME: Hasitha Dias      STUDENT ID: 789929
	 * NAME: Elliot Jenkins    STUDENT ID: 762686 
	 * 
	 * LAST MODIFIED: 27/05/2018
	 * 
	 * */

package mycontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.World;

public class DiscoverStrategy implements IGoalStrategy {

	private Sensor sensor;
	private ArrayList<Coordinate> discoveryPoints;
	private Coordinate currentPos;
	
	public DiscoverStrategy(Sensor sensor) {
		this.sensor = sensor;
	}

	/**
	 * Update to get the coordinate in discoveryPoint closest to the car's current position
	 * @return the next destination coordinate
	 */
	@Override
	public Coordinate update() {
		return getClosestDiscoveryPoint();
	}

	/**
	 * Update the map to find the newest undiscovered points to go to
	 * @param currentPos the position of the car
	 */
	@Override
	public void updateMap(Coordinate currentPos) {
		// update this so we can know where newly discovered lava is and where we've already discovered
		this.currentPos = currentPos;
		discoveryPoints = findDiscoveryPoints(sensor.getNotVisited());
		//adjustPointsToRoads(discoveryPoints);
	}
	
	private ArrayList<Coordinate> findDiscoveryPoints(HashMap<Coordinate, MapTile> map) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		int height = World.MAP_HEIGHT;
		int width = World.MAP_WIDTH;
		// just split into 8x8 in case we really can't reach the center tile and have to offset one of the centerpoints
		// overlapping viewed tiles is better than not discovering tiles
		int gridSize = Car.VIEW_SQUARE * 2;
		
		// not = height or width because there'll always be a wall around the map 
		for (int y = gridSize/2; y < height; y += gridSize) {
			for (int x = gridSize/2; x < width; x += gridSize) {
				Coordinate centrePoint = new Coordinate(x,y);
				
				// find the point in the map closest to the centrePoint
				// that is also a road
				int centreX = centrePoint.x;
				int centreY = centrePoint.y;
				boolean foundRoad = false;
				// check tiles around the centrePoint in an outwards spiral pattern for roads
				// alternate i and j between 0,1,0,-1 then add that to centreX/Y to get the spiral pattern
				int i=0, j=1;
				for (int count = 0; !foundRoad; count++) {
					for (int side=0; !foundRoad && side<=count/2; side++) {
						centreX+=i;
						centreY+=j;
						Coordinate newCentrePoint = new Coordinate(centreX, centreY);
						if (map.get(newCentrePoint) != null && (map.get(newCentrePoint).isType(MapTile.Type.ROAD))) {
							coords.add(newCentrePoint);
							foundRoad = true;
						}
					}
					//switch the direction of the spiral
					// Up    : i= 0,  j= 1
					// Right : i= 1,  j= 0
					// Down  : i= 0,  j=-1
					// Left  : i=-1,  j= 0
					if (i==0 && j==1) {
						i=1;
						j=0;
					} else if (i==1 && j==0) {
						i=0;
						j=-1;
					} else if (i==0 && j==-1) {
						i=-1;
						j=0;
					} else if (i==-1 && j==0) {
						i=0;
						j=1;
					}
				}
				
			}
		}
		//System.out.println(coords);
		return coords;
	}
	
	public void destinationReached() {
	}

	// get the discovery point closest to the car's current position
	private Coordinate getClosestDiscoveryPoint() {
		Coordinate closestSoFar = discoveryPoints.get(0);
		for(Coordinate coord : discoveryPoints) {
			if (((coord.x - currentPos.x) + (coord.y - currentPos.y)) < 
					(closestSoFar.x - currentPos.x) + (closestSoFar.y - currentPos.y)) {
				closestSoFar = coord;
			}
		}
		return closestSoFar;
	}

}