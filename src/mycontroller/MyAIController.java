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
	private Coordinate currentDest;
	
	public MyAIController(Car car) {
		super(car);
		this.sensor = new Sensor(this.getMap(), this);
		this.pathFinder = new PathFinder(this.sensor);
		this.move = new CarMovement(this);
		this.strategy = new CompositeStrategy(this);
		this.currentPath = null;
		this.currentDest = null;
	}
	
	/**
	 * This functions calls the strategy to obtain the destination, then calls the pathfinder to find the path and finally calls the CarMovement object to move the car.
	 * @param delta
	 */
	@Override
	public void update(float delta) {
		Coordinate currentPos = new Coordinate(getPosition());
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		this.sensor.addToSeen(currentView);
		checkEndOfPath();
	
		this.currentDest = strategy.update();
		setPath(this.currentDest);

		System.out.println(this.currentDest);
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
	 */
	private void setPath(Coordinate destination) {
		Coordinate currentPos = new Coordinate(getPosition());
		if (currentPath == null) {
			this.currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		} else if((currentPath.getEnd().x != destination.x) || (currentPath.getEnd().y != destination.y)) {
			this.currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		}
		
	}

	/**
	 * 
	 * @return the object of sensor
	 */
	public Sensor getSensor() {
		return sensor;
	}

	public Path requestNewPath() {
		Coordinate currentPos = new Coordinate(getPosition());
		this.currentPath = this.pathFinder.returnPath(new Path(currentPos,this.currentDest,getOrientation()));
		return this.currentPath;
		
	}
	
	
}
