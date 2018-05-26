/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

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
	
	/**
	 * Called by AIController with a set Path and this function makes the car move accordingly.
	 * @param delta
	 * @param path
	 */
	public void update(float delta, Path path) {
		Coordinate current = new Coordinate(controller.getPosition());
		checkStateChange();
		System.out.println(controller.getX());
		
		//different behavior if turning 
		if (!turning) {
			readjust(lastTurnDirection,delta);
			///////////////////////////////////////////////////////////////////////////////////////////////
			for (int i=0;i<10000;i++) {
				readjustPosition(delta);
			}
			
			//gets the car upto speed
			if(controller.getSpeed() < CAR_SPEED){
				controller.applyForwardAcceleration();	
			}else if (controller.getSpeed() > CAR_SPEED) {
//				controller.applyBrake();
			}
			//initiates function for turn if turn is required
			if(!controller.getOrientation().equals(path.getDirection(current))) {
				turning = true;
				applyTurn(path.getDirection(current),delta);
			}
			
		}else {
			//gets the car upto speed
			if(controller.getSpeed() < TURN_SPEED){
				controller.applyForwardAcceleration();	
			}
			//turns the car according to direction
			if (isTurningRight) {
				controller.turnRight(delta);
			}else if(isTurningLeft) {
				controller.turnLeft(delta);
			}
		}
		//reversing if stopped by wall - maybe remove
		if (controller.getSpeed() == 0) {
			for (int i=0;i<1000;i++) {
				controller.applyReverseAcceleration();
			}
		}
	}
/////////////////////////////////////////////////////////////remove this
	private void readjustPosition(float delta) {
		Coordinate coord = new Coordinate(controller.getPosition());
		WorldSpatial.Direction dir = controller.getOrientation(); 
		float x = controller.getX();
		float y = controller.getY();
		if (((dir.equals(WorldSpatial.Direction.NORTH))&&(coord.x>x)) || ((dir.equals(WorldSpatial.Direction.EAST))&&(coord.y<y)) || ((dir.equals(WorldSpatial.Direction.SOUTH))&&(coord.x<x)) || ((dir.equals(WorldSpatial.Direction.WEST))&&(coord.y>y))) {
			controller.turnLeft(delta);
		}
		if (((dir.equals(WorldSpatial.Direction.NORTH))&&(coord.x<x)) || ((dir.equals(WorldSpatial.Direction.EAST))&&(coord.y>y)) || ((dir.equals(WorldSpatial.Direction.SOUTH))&&(coord.x>x)) || ((dir.equals(WorldSpatial.Direction.WEST))&&(coord.y<y))) {
			controller.turnRight(delta);
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
	 * @param orientation
	 * @param delta
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

	/**
	 * Try to orient myself to a degree that I was supposed to be at if I am
	 * misaligned.
	 * @param orientation
	 * @param delta
	 */
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
	
	/**
	 * Initiates a turn in the correct direction depending on the orientation.
	 * @param orientation
	 * @param delta
	 */
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

}
