package mycontroller;

import java.util.HashMap;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

public class MyAIController extends CarController{

//	// How many minimum units the wall is away from the player.
//	private int wallSensitivity = 2;
//	
//	
//	private boolean turning = false; // This is initialized when the car sticks to a wall.
//	private WorldSpatial.RelativeDirection lastTurnDirection = null; // Shows the last turn direction the car takes.
//	private boolean isTurningLeft = false;
//	private boolean isTurningRight = false; 
//	private WorldSpatial.Direction previousState = null; // Keeps track of the previous state
//	
//	// Car Speed to move at
//	private final float CAR_SPEED = 2;
//	
//	// Offset used to differentiate between 0 and 360 degrees
//	private int EAST_THRESHOLD = 3;
	
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
	
	public void informKey(int keyID, Coordinate keyCoord) {
		for (int i =0;i<100;i++) {
			System.out.println("345");
		}
	}

	public Sensor getSensor() {
		return sensor;
	}
	

	Coordinate initialGuess;
	boolean notSouth = true;
	@Override
	public void update(float delta) {
		
		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();
		this.sensor.addToSeen(currentView);
		checkEndOfPath();
		Coordinate destination = strategy.update();
		setPath(destination);
		System.out.println(destination);
		if (currentPath!=null) {
			currentPath.tooString();
			move.update(delta, currentPath);
	
		}
	}
		
	
	private void checkEndOfPath() {
		Coordinate current = getCurrentCoord();
		if (currentPath!=null) {
			if((currentPath.getEnd().x == current.x) && (currentPath.getEnd().y == current.y)) {
				currentPath = null;
				strategy.destinationReached();
			}
		}
	}
	
	private Path setPath(Coordinate destination) {
		Coordinate currentPos = getCurrentCoord();
		if (currentPath == null) {
			currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		} else if((currentPath.getEnd().x != destination.x) || (currentPath.getEnd().y != destination.y)) {
			currentPath = this.pathFinder.returnPath(new Path(currentPos,destination,getOrientation()));
		}
		return currentPath;
	}
	
	
	private Coordinate getCurrentCoord() {
		return new Coordinate(getPosition());
	}
	
}
