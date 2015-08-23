/**
 * 
 */
package my.zulsoft.common.game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.ReadableColor;

/**
 * @author Faizul
 *
 */
public class GraphicsDevice {

	/**
	 * 
	 */
	public GraphicsDevice() {
		try {
			Display.getDrawable().makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void clear(ReadableColor rc) {
		GL11.glClearColor((float)rc.getRed(),(float) rc.getGreen(),(float) rc.getBlue(),0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void begin() {
		GL11.glBegin(GL11.GL_TRIANGLES);
	}

}
