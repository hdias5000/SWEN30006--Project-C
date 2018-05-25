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
	
	public Path returnPath(Path path) {
		Coordinate current = path.getCurrent();
		Coordinate end = path.getEnd();
		if (checkIfEnd(current,end)) {
			return path;
		}
		Queue<Coordinate> traverse = findOrderToTraverse(path);
		while (traverse.peek()!=null) {
			Coordinate newCoord = traverse.remove();
			WorldSpatial.Direction newDir = findDir(current,newCoord);
			path.changeDir(current, newDir);
			path.addCoord(newCoord, newDir);
			Path newPath = returnPath(path);
			if (newPath!=null) {
				return newPath;
			}else {
				path.backTrack(current);
			}
		}
		return null;
	}
	
	private int calculateManhattanDistance(Coordinate current, Coordinate end) {
		return (Math.abs(end.x-current.x) + Math.abs(end.y - current.y));
	}
	
	private boolean checkIfEnd(Coordinate current, Coordinate end) {
		return ((current.x==end.x)&&(current.y==end.y));
	}
	
	private Queue<Coordinate> findOrderToTraverse(Path path){
		Coordinate current = path.getCurrent();
//		Coordinate end = path.getEnd();
		WorldSpatial.Direction dir = path.getDirection(current);
//		int currentDistance = calculateManhattanDistance(current,end);
//		HashMap<Coordinate,MapTile> currentMap = this.sensor.getCurrentMap();
		
		Queue<Coordinate> traverse = new LinkedList<Coordinate>(); 
		Queue<Coordinate> secondChoice = new LinkedList<Coordinate>();
		
//		HashMap<Coordinate,Integer> bestTraverse = new HashMap<Coordinate,Integer>();
		
//		Coordinate sameDir = findCoord(current,dir);
//		Coordinate left = findCoord(current,findLeftDir(dir));
//		Coordinate right = findCoord(current,findRightDir(dir));
		
		checkBeforeAdd(traverse,secondChoice,path,dir);
		checkBeforeAdd(traverse,secondChoice,path,findLeftDir(dir));
		checkBeforeAdd(traverse,secondChoice,path,findRightDir(dir));
		while (secondChoice.peek()!=null) {
			traverse.add(secondChoice.remove());
		}
		return traverse;
	}
	
	private void checkBeforeAdd(Queue<Coordinate> traverse, Queue<Coordinate> secondChoice, Path path, Direction dir) {
		Coordinate current = path.getCurrent();
		Coordinate end = path.getEnd();
		Coordinate coord = findCoord(current,dir);
		int currentDistance = calculateManhattanDistance(current,end);
		HashMap<Coordinate,MapTile> currentMap = this.sensor.getCurrentMap();
		if (checkNotWall(currentMap.get(coord)) && !path.checkVisited(coord)) {
//			if ((dir != path.getDirection(current)) && (checkNotWall(currentMap.get(findCoord(coord,dir))))) {
//				secondChoice.add(coord);
//			} else {
				if (calculateManhattanDistance(coord,end)<currentDistance) {
					traverse.add(coord);
				}else {
					secondChoice.add(coord);
				}
//			}
		}
	}

	
	private boolean checkNotWall(MapTile tile) {
		if (tile.isType(MapTile.Type.WALL)) {
			return false;
		}
		return true;
	}
	
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
