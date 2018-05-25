package mycontroller;

import utilities.Coordinate;

public interface IGoalStrategy {
	public Coordinate update();
	public void updateMap(Coordinate currentPos);
}
