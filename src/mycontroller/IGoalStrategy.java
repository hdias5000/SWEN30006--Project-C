/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

import utilities.Coordinate;

public interface IGoalStrategy {
	//common functions in all strategy functions
	public Coordinate update();
	public void updateMap(Coordinate currentPos);
}
