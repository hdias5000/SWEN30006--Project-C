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
	
	public Path(Path another) {
		this.start = another.start;
		this.end = another.end;
		this.length = another.length;
		this.orderOfCoordinates = new ArrayList<Coordinate>(another.orderOfCoordinates);
		this.visitedCoordinates = new ArrayList<Coordinate>(another.visitedCoordinates);
		this.directions = new HashMap<Coordinate,WorldSpatial.Direction>(another.directions);
	}
	
	public void backTrack(Coordinate coord) {
		int index = this.orderOfCoordinates.indexOf(coord);
		for (int i=index+1;i<=length;i++) {
			Coordinate remove = this.orderOfCoordinates.get(i);
			this.orderOfCoordinates.remove(i);
			this.directions.remove(remove);
		}
		this.length = index;
	}
	
	public void addCoord(Coordinate coord, WorldSpatial.Direction dir) {
		this.length+=1;
		this.orderOfCoordinates.add(coord);
		this.visitedCoordinates.add(coord);
		this.directions.put(coord, dir);
	}
	
	public void changeDir(Coordinate coord, WorldSpatial.Direction dir) {
		this.directions.remove(coord);
		this.directions.put(coord, dir);
	}
	
	public boolean checkVisited(Coordinate coord) {
		return visitedCoordinates.contains(coord);
	}
	
	public Direction peek(Coordinate coord) {
		int index = this.orderOfCoordinates.indexOf(coord);
		return this.directions.get(this.orderOfCoordinates.get(index+1));
	}
	
	
	
	public WorldSpatial.Direction currentDirection(){
		return this.directions.get(this.orderOfCoordinates.get(length));
	}
	
	public WorldSpatial.Direction getDirection(Coordinate coord){
		return this.directions.get(coord);
	}
	
	public Coordinate getStart() {
		return this.start;
	}
	
	public Coordinate getCurrent() {
		return this.orderOfCoordinates.get(length);
	}
	
	public Coordinate getEnd() {
		return this.end;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void tooString() {
		
		for (int i = 0; i<this.orderOfCoordinates.size();i++) {
			Coordinate coord = this.orderOfCoordinates.get(i);
			System.out.printf("(%d,%d)->",coord.x,coord.y);
		}
		System.out.println("yeah");
	}
	
}
