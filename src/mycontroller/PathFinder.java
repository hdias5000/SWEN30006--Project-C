/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;

public class PathFinder {
	private Sensor sensor;
	public PathFinder(Sensor sensor) {
		this.sensor = sensor;
	}
	
	/**
	 * Finds the path to traverse from the start to the end of the given path.
	 * @param path
	 * @return path to follow
	 */
	public Path returnPath(Path path) {
		Coordinate current = path.getCurrent();
		Coordinate end = path.getEnd();
		if (checkIfEnd(current,end)) {
			return path;
		}
		//finds order to traverse
		Queue<Coordinate> traverse = findOrderToTraverse(path);
		while (traverse.peek()!=null) {
			Coordinate newCoord = traverse.remove();
			WorldSpatial.Direction newDir = findDir(current,newCoord);
			path.changeDir(current, newDir);
			path.addCoord(newCoord, newDir);
			Path newPath = returnPath(path);
			if (newPath!=null) {
				//when a path has been found
				return newPath;
			}else {
				//if direction was unsuccessful
				path.backTrack(current);
			}
		}
		return null;
	}
	
	/**
	 * Evaluation Function.
	 * @param current
	 * @param end
	 * @return manhattan distance from current to end
	 */
	private int calculateManhattanDistance(Coordinate current, Coordinate end) {
		return (Math.abs(end.x-current.x) + Math.abs(end.y - current.y));
	}
	
	/**
	 * 
	 * @param end
	 * @param current
	 * @return if end of path is reached 
	 */
	private boolean checkIfEnd(Coordinate current, Coordinate end) {
		return ((current.x==end.x)&&(current.y==end.y));
	}
	
	/**
	 * Finds order of coordinated to traverse from the final coordinate in path.
	 * @param path
	 * @return queue of coordinates to traverse
	 */
	private Queue<Coordinate> findOrderToTraverse(Path path){
		Coordinate current = path.getCurrent();
		WorldSpatial.Direction dir = path.getDirection(current);
		
		Queue<Coordinate> traverse = new LinkedList<Coordinate>(); 
		Queue<Coordinate> secondChoice = new LinkedList<Coordinate>();
		
		checkBeforeAdd(traverse,secondChoice,path,dir);
		checkBeforeAdd(traverse,secondChoice,path,findLeftDir(dir));
		checkBeforeAdd(traverse,secondChoice,path,findRightDir(dir));
		while (secondChoice.peek()!=null) {
			traverse.add(secondChoice.remove());
		}
		return traverse;
	}
	
	/**
	 * Finds the order for the coordinate to be traversed in according to evaluation function.
	 * @param traverse
	 * @param secondChoice
	 * @param path
	 * @param dir
	 */
	private void checkBeforeAdd(Queue<Coordinate> traverse, Queue<Coordinate> secondChoice, Path path, Direction dir) {
		Coordinate current = path.getCurrent();
		Coordinate end = path.getEnd();
		Coordinate coord = findCoord(current,dir);
		int currentDistance = calculateManhattanDistance(current,end);
		HashMap<Coordinate,MapTile> currentMap = this.sensor.getCurrentMap();
		if (checkNotWall(currentMap.get(coord)) && !path.checkVisited(coord)) {
			if (calculateManhattanDistance(coord,end)<currentDistance) {
				traverse.add(coord);
			}else {
				secondChoice.add(coord);
			}
		}
	}

	/**
	 * 
	 * @param tile
	 * @return if tile is not a wall
	 */
	private boolean checkNotWall(MapTile tile) {
		if (tile.isType(MapTile.Type.WALL)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Finds new coordinate when coord moves in direction dir.
	 * @param coord
	 * @param dir
	 * @return new coordinate
	 */
	private Coordinate findCoord(Coordinate coord, WorldSpatial.Direction dir) {
		switch (dir) {
		case EAST:
			return new Coordinate(coord.x+1,coord.y);
		case WEST:
			return new Coordinate(coord.x-1,coord.y);
		case NORTH:
			return new Coordinate(coord.x,coord.y+1);
		case SOUTH:
			return new Coordinate(coord.x,coord.y-1);
		}
		return null;
	}
	
	/**
	 * 
	 * @param dir
	 * @return direction when car turns right
	 */
	private WorldSpatial.Direction findRightDir(WorldSpatial.Direction dir){
		switch (dir) {
		case EAST:
			return WorldSpatial.Direction.SOUTH;
		case WEST:
			return WorldSpatial.Direction.NORTH;
		case NORTH:
			return WorldSpatial.Direction.EAST;
		case SOUTH:
			return WorldSpatial.Direction.WEST;
		}
		return null;
	}
	
	/**
	 * 
	 * @param dir
	 * @return direction when car turns left
	 */
	private WorldSpatial.Direction findLeftDir(WorldSpatial.Direction dir){
		switch (dir) {
		case EAST:
			return WorldSpatial.Direction.NORTH;
		case WEST:
			return WorldSpatial.Direction.SOUTH;
		case NORTH:
			return WorldSpatial.Direction.WEST;
		case SOUTH:
			return WorldSpatial.Direction.EAST;
		}
		return null;
	}
	
	/**
	 * Finds the direction moved when two adjoining coordinates are given.
	 * @param start
	 * @param end
	 * @return direction moved by the start coordinate
	 */
	private WorldSpatial.Direction findDir(Coordinate start, Coordinate end){
		if (((end.x-start.x)==1) && ((end.y-start.y)==0)) {
			return WorldSpatial.Direction.EAST;
		}
		if (((end.x-start.x)==-1) && ((end.y-start.y)==0)) {
			return WorldSpatial.Direction.WEST;
		}
		if (((end.x-start.x)==0) && ((end.y-start.y)==1)) {
			return WorldSpatial.Direction.NORTH;
		}
		if (((end.x-start.x)==0) && ((end.y-start.y)==-1)) {
			return WorldSpatial.Direction.SOUTH;
		}
		return null;
	}

}
