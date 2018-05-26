/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import java.util.HashMap;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

public class MyAIController extends CarController{
	
	private Sensor sensor;
	private PathFinder pathFinder;
	private CarMovement move;
	private CompositeStrategy strategy;
	private Path currentPath;
	
	public MyAIController(Car car) {
		super(car);
		this.sensor = new Sensor(this.getMap(), this);
		this.pathFinder = new PathFinder(this.sensor);
		this.move = new CarMovement(this);
		this.strategy = new CompositeStrategy(this);
		this.currentPath = null;
	}
	
	/**
	 * This functions calls the strategy to obtain the destination, then calls the pathfinder to find the path and finally calls the CarMovement object to move the car.
	 * @param delta
	 */
	@Override
	public void update(float delta) {
		
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		this.sensor.addToSeen(currentView);
		checkEndOfPath();
		
		Coordinate destination = strategy.update();
		setPath(destination);
		System.out.println(destination);
		//if a path exists, makes car move
		if (currentPath!=null) {
			currentPath.tooString();
			move.update(delta, currentPath);
	
		}
	}
		
	/**
	 * Checks if the end of the path has been reached.
	 */
	private void checkEndOfPath() {
		Coordinate current = new Coordinate(getPosition());
		if (currentPath!=null) {
			if((currentPath.getEnd().x == current.x) && (currentPath.getEnd().y == current.y)) {
				currentPath = null;
				strategy.destinationReached();
			}
		}
	}
	
	/**
	 * Sets a new path if the destination has changed.
	 * @param destination
	 * @return path
	 */
	private Path setPath(Coordinate destination) {
		Coordinate currentPos = new Coordinate(getPosition());
		if (currentPath == null) {
			currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		} else if((currentPath.getEnd().x != destination.x) || (currentPath.getEnd().y != destination.y)) {
			currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		}
		return currentPath;
	}
	
	/**
	 * This function is called by Sensor to inform of any newly found keys.
	 * @param keyID
	 * @param keyCoord
	 */
	public void informKey(int keyID, Coordinate keyCoord) {
		for (int i =0;i<100;i++) {
			System.out.println("345");
		}
	}

	/**
	 * 
	 * @return the object of sensor
	 */
	public Sensor getSensor() {
		return sensor;
	}
	
	
}
