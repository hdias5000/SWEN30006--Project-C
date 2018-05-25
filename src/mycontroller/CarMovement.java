package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class CarMovement {
	MyAIController controller;
	
	// How many minimum units the wall is away from the player.
	private int wallSensitivity = 2;
	
	
	private boolean turning = false; // This is initialized when the car sticks to a wall.
	private WorldSpatial.RelativeDirection lastTurnDirection = null; // Shows the last turn direction the car takes.
	private boolean isTurningLeft = false;
	private boolean isTurningRight = false; 
	private WorldSpatial.Direction previousState = null; // Keeps track of the previous state
	
	// Car Speed to move at
	private final double CAR_SPEED = 2;
	private final double TURN_SPEED = 0.1;
	
	// Offset used to differentiate between 0 and 360 degrees
	private int EAST_THRESHOLD = 3;

	
	public CarMovement(MyAIController myAIController) {
		this.controller = myAIController;
	}
	
	public void update(float delta, Path path) {
		Coordinate current = new Coordinate(controller.getPosition());
		checkStateChange();
		System.out.println(controller.getX());
		
		if (!turning) {
			readjust(lastTurnDirection,delta);
			
			if(controller.getSpeed() < CAR_SPEED){
				controller.applyForwardAcceleration();	
			}else if (controller.getSpeed() > CAR_SPEED) {
//				controller.applyBrake();
			}
			if(!controller.getOrientation().equals(path.getDirection(current))) {
				turning = true;
				applyTurn(path.getDirection(current),delta);
			}
			
		}else {
			if(controller.getSpeed() < TURN_SPEED){
				controller.applyForwardAcceleration();	
			}
			if (isTurningRight) {
				controller.turnRight(delta);
			}else if(isTurningLeft) {
				controller.turnLeft(delta);
			}
			
//			readjust(lastTurnDirection,delta);
		}
		
		if (controller.getSpeed() == 0) {
			for (int i=0;i<1000;i++) {
				controller.applyReverseAcceleration();
			}
		}
	}
	
	/**
	 * Readjust the car to the orientation we are in.
	 * @param lastTurnDirection
	 * @param delta
	 */
	private void readjust(WorldSpatial.RelativeDirection lastTurnDirection, float delta) {
		if(lastTurnDirection != null){
			if(!isTurningRight && lastTurnDirection.equals(WorldSpatial.RelativeDirection.RIGHT)){
				adjustRight(controller.getOrientation(),delta);
			}
			else if(!isTurningLeft && lastTurnDirection.equals(WorldSpatial.RelativeDirection.LEFT)){
				adjustLeft(controller.getOrientation(),delta);
			}
		}
		
	}
	
	/**
	 * Try to orient myself to a degree that I was supposed to be at if I am
	 * misaligned.
	 */
	private void adjustLeft(WorldSpatial.Direction orientation, float delta) {
		
		switch(orientation){
		case EAST:
			if(controller.getAngle() > WorldSpatial.EAST_DEGREE_MIN + EAST_THRESHOLD){
				controller.turnRight(delta);
			}
			break;
		case NORTH:
			if(controller.getAngle() > WorldSpatial.NORTH_DEGREE){
				controller.turnRight(delta);
			}
			break;
		case SOUTH:
			if(controller.getAngle() > WorldSpatial.SOUTH_DEGREE){
				controller.turnRight(delta);
			}
			break;
		case WEST:
			if(controller.getAngle() > WorldSpatial.WEST_DEGREE){
				controller.turnRight(delta);
			}
			break;
			
		default:
			break;
		}
		
	}

	private void adjustRight(WorldSpatial.Direction orientation, float delta) {
		System.out.println(controller.getAngle());
		switch(orientation){
		
		case EAST:
			if(controller.getAngle() > WorldSpatial.SOUTH_DEGREE && controller.getAngle() < WorldSpatial.EAST_DEGREE_MAX){
				controller.turnLeft(delta);
			}
			break;
		case NORTH:
			if(controller.getAngle() < WorldSpatial.NORTH_DEGREE){
				controller.turnLeft(delta);
			}
			break;
		case SOUTH:
			if(controller.getAngle() < WorldSpatial.SOUTH_DEGREE){
				controller.turnLeft(delta);
			}
			break;
		case WEST:
			if(controller.getAngle() < WorldSpatial.WEST_DEGREE){
				controller.turnLeft(delta);
			}
			break;
			
		default:
			break;
		}
		
	}
	
	/**
	 * Checks whether the car's state has changed or not, stops turning if it
	 *  already has.
	 */
	private void checkStateChange() {
		if(previousState == null){
			previousState = controller.getOrientation();
		}
		else{
			if(previousState != controller.getOrientation()){
				if(isTurningLeft){
					isTurningLeft = false;
					turning = false;
				}
				if(isTurningRight){
					isTurningRight = false;
					turning = false;
				}
				previousState = controller.getOrientation();
			}
		}
	}
	
	
	private void applyTurn(WorldSpatial.Direction orientation, float delta) {
		switch(orientation){
		case EAST:
			if(!controller.getOrientation().equals(WorldSpatial.Direction.NORTH)){
				controller.turnLeft(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				isTurningLeft = true;
			}else {
				controller.turnRight(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
				isTurningRight = true;
			}
			break;
		case NORTH:
			if(!controller.getOrientation().equals(WorldSpatial.Direction.WEST)){
				controller.turnLeft(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				isTurningLeft = true;
			}else {
				controller.turnRight(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
				isTurningRight = true;
			}
			break;
		case SOUTH:
			if(!controller.getOrientation().equals(WorldSpatial.Direction.EAST)){
				controller.turnLeft(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				isTurningLeft = true;
			}else {
				controller.turnRight(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
				isTurningRight = true;
			}
			break;
		case WEST:
			if(!controller.getOrientation().equals(WorldSpatial.Direction.SOUTH)){
				controller.turnLeft(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				isTurningLeft = true;
			}else {
				controller.turnRight(delta);
				lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
				isTurningRight = true;
			}
			break;
		default:
			break;
		
		}
	}
	
//	private boolean checkWall() {
//		
//	}

}
