/**
 * 
 */
package my.zulsoft.common.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

/**
 * @author Faizul
 *
 */
@SuppressWarnings("deprecation")
public class SpriteBatch {

	public enum SpriteEffects {
		None
	}

	public enum SpriteBlendMode {
		AlphaBlend
	}

	private GraphicsDevice graphicsDevice;
	
	/**
	 * @param graphicsDevice 
	 * 
	 */
	public SpriteBatch(GraphicsDevice graphicsDevice) {
		this.graphicsDevice = graphicsDevice;
		
	}

	public void draw(Texture t, Rectangle r, ReadableColor rc) 
	{
		t.bind();
		GL11.glLoadIdentity();
	}
    
	public void drawString(TrueTypeFont sf, String s ,Vector2f v, ReadableColor rc)
	{
		
	}

	public void begin(SpriteBlendMode mode) 
	{
		if(mode == SpriteBlendMode.AlphaBlend) {
			GL11
		}
		graphicsDevice.begin();
	}

	public void drawString(TrueTypeFont sf, String s, Vector2f spos,
			ReadableColor rc, float f, Vector2f v, float g,
			SpriteEffects se, int i) {
		
		
	}

	public void End() {
		
		
	}

}
