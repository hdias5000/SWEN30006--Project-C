/* GROUP NUMBER: 71
 * NAME: Hasitha Dias      STUDENT ID: 789929
 * NAME: Elliot Jenkins    STUDENT ID: 762686 
 * 
 * LAST MODIFIED: 27/05/2018
 * 
 * */

package mycontroller;

//This interface is used for any extension/ allows flexibility to use more Path Finding Strategies
public interface IPathFinderStrategy {
	//finds the path
	public Path returnPath(Path path);
	
}
