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

import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public class Path {
	private Coordinate start;
	private Coordinate end;
	private ArrayList<Coordinate> orderOfCoordinates;
	private HashMap<Coordinate,WorldSpatial.Direction> directions = null;
	private ArrayList<Coordinate> visitedCoordinates;
	private int length;
	
	public Path(Coordinate start, Coordinate end, WorldSpatial.Direction dir) {
		this.start = start;
		this.end = end;
		this.length = 0;
		this.orderOfCoordinates = new ArrayList<Coordinate>();
		this.visitedCoordinates = new ArrayList<Coordinate>();
		this.directions = new HashMap<Coordinate,WorldSpatial.Direction>();
		this.orderOfCoordinates.add(this.start);
		this.visitedCoordinates.add(this.start);
		this.directions.put(this.start, dir);
	}
	
	//*******************remove this
	// copy constructor
	public Path(Path another) {
		this.start = another.start;
		this.end = another.end;
		this.length = another.length;
		this.orderOfCoordinates = new ArrayList<Coordinate>(another.orderOfCoordinates);
		this.visitedCoordinates = new ArrayList<Coordinate>(another.visitedCoordinates);
		this.directions = new HashMap<Coordinate,WorldSpatial.Direction>(another.directions);
	}
	
	 
	/**
	 * Used to go back to a point in the path if it doesn't lead to the destination.
	 * @param coord
	 */
	public void backTrack(Coordinate coord) {
		int index = this.orderOfCoordinates.indexOf(coord);
		for (int i=index+1;i<=length;i++) {
			Coordinate remove = this.orderOfCoordinates.get(i);
			this.orderOfCoordinates.remove(i);
			this.directions.remove(remove);
		}
		this.length = index;
	}
	
	
	/**
	 * Adds coordinated to path and list of Visited Nodes.
	 * @param coord
	 * @param dir	
	 */
	public void addCoord(Coordinate coord, WorldSpatial.Direction dir) {
		this.length+=1;
		this.orderOfCoordinates.add(coord);
		this.visitedCoordinates.add(coord);
		this.directions.put(coord, dir);
	}
	
	/**
	 * Changes direction of movement for a particular coordinate.
	 * @param coord
	 */
	public void changeDir(Coordinate coord, WorldSpatial.Direction dir) {
		this.directions.remove(coord);
		this.directions.put(coord, dir);
	}
	
	/**
	 * Checks if coordinate in list of visited nodes.
	 * @param coord
	 * @return	
	 */
	public boolean checkVisited(Coordinate coord) {
		return visitedCoordinates.contains(coord);
	}
	
	/**
	 * Finds direction of next coordinate.
	 * @param coord
	 * @return direction	
	 */
	public Direction peek(Coordinate coord) {
		int index = this.orderOfCoordinates.indexOf(coord);
		return this.directions.get(this.orderOfCoordinates.get(index+1));
	}
	
	/**
	 *
	 * @return direction of current coordinate	
	 */
	public WorldSpatial.Direction currentDirection(){
		return this.directions.get(this.orderOfCoordinates.get(length));
	}
	
	/**
	 * 
	 * @param coord
	 * @return direction of given coordinate	
	 */
	public WorldSpatial.Direction getDirection(Coordinate coord){
		return this.directions.get(coord);
	}
	
	/**
	 * 
	 * @return start coordinate of the Path	
	 */
	public Coordinate getStart() {
		return this.start;
	}
	
	/**
	 * 
	 * @return final position reached in the Path	
	 */
	public Coordinate getCurrent() {
		return this.orderOfCoordinates.get(length);
	}
	
	/**
	 * 
	 * @return end coordinate of the Path	
	 */
	public Coordinate getEnd() {
		return this.end;
	}
	
	/**
	 * 
	 * @return length of the Path	
	 */
	public int getLength() {
		return this.length;
	}
//*******************remove this	
	/**
	 * 
	 * @return starting position of the Path	
	 */
	public void tooString() {
		
		for (int i = 0; i<this.orderOfCoordinates.size();i++) {
			Coordinate coord = this.orderOfCoordinates.get(i);
			System.out.printf("(%d,%d)->",coord.x,coord.y);
		}
		System.out.println("yeah");
	}
	
}
