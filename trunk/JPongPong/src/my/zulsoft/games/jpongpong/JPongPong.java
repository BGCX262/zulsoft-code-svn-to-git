package my.zulsoft.games.jpongpong;

import my.zulsoft.common.game.GameFramework;

/**
 * 
 */

/**
 * @author Faizul
 *
 */
public class JPongPong {

	/**
	 * 
	 */
	public JPongPong(String[] args) {
		JPongPongGame g = new JPongPongGame();
		new GameFramework(args, g).start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JPongPong(args);
	}

}
