/**
 * 
 */
package my.zulsoft.common.game;

/**
 * @author Faizul
 *
 */
public class GraphicsDeviceManager {

	CoreGame cg;
	GraphicsDevice graphicsDevice;
	/**
	 * 
	 */
	public GraphicsDeviceManager(CoreGame g) {
		
		cg = g;
		this.graphicsDevice = g.getGraphicsDevice();
		
	}

}
