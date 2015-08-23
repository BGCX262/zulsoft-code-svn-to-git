/**
 * 
 */
package my.zulsoft.common.game;

/**
 * @author Faizul
 *
 */
public class GameTime {

	long startTime;
	long currentTime;
	long elapsedTime;
	
	/**
	 * 
	 */
	public GameTime() {
		startTime = System.nanoTime();
	}
	
	public void tick() {
		currentTime = System.nanoTime();
		elapsedTime = currentTime - startTime; 
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}
