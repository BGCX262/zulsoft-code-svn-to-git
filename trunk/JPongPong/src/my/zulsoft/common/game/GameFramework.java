package my.zulsoft.common.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class GameFramework {
	
	CoreGame g;
	GameTime gameTime;
	Config c;
	GraphicsDevice graphicsDevice;
	
	public GameFramework(String[] args, CoreGame g2) {
		g = g2;
		c = Config.loadConfig(args);
	}
	
	public void start() {
		
		try {
			
			DisplayMode mode = getCompatibleDisplayMode(c.getConfigScreenWidth(), 
					c.getConfigScreenHeight(), c.getConfigScreenBitPerPixel(), c.getConfigScreenFullsceen());
			Display.setDisplayMode(mode);
			Display.setDisplayConfiguration(c.getConfigScreenGamma(), 
					c.getConfigScreenBrightness(), c.getConfigScreenContrast());
			Display.setVSyncEnabled(c.getConfigScreenVSync());
			Display.setFullscreen(c.getConfigScreenFullsceen());
			Display.create();
			
			GL11.glMatrixMode (GL11.GL_PROJECTION);
			GL11.glLoadIdentity ();
			GL11.glOrtho (0, c.getConfigScreenWidth(), c.getConfigScreenHeight(), 0, 0, 1);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glMatrixMode (GL11.GL_MODELVIEW);
			
			
			gameTime = new GameTime();
			
			createGraphicsDevice();
			g.setGraphicsDevice(this.graphicsDevice);
			g.initialize();
			g.loadContent();
			run();
			g.unloadContent();
		
		} catch (LWJGLException e) {
			e.printStackTrace();
		
		}
		return;
	}
	
	private void createGraphicsDevice() {
		this.graphicsDevice = new GraphicsDevice();
	}

	public void run() {
		
		while(!g.exitGame() && !Display.isCloseRequested()) {
			gameTime.tick();
			g.update(gameTime);
			gameTime.tick();
			g.draw(gameTime);
			gameTime.tick();
			Display.update();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected DisplayMode getCompatibleDisplayMode(int width, int height, int bitperpixel, boolean canFullscreen)
	{
		DisplayMode[] dispMode;
		ArrayList dipMod = new ArrayList();
		
		DisplayMode dmCompat = null;
		boolean width_Any = false;
		boolean height_Any = false;
		boolean bb_Any = false;
		
		if(width <= 0)  width_Any = true;
		if(height <= 0) height_Any = true;
		if(bitperpixel <= 0) bb_Any = true;
		
		try {
			
			dispMode = Display.getAvailableDisplayModes();
			
			for(DisplayMode dm : dispMode)
			{
					if(width_Any || height_Any || bb_Any ) {
												
					} else {
						if(dm.getHeight() == height && 
							dm.getWidth() == width &&  
							dm.getBitsPerPixel() == bitperpixel && //) {
							dm.isFullscreenCapable() == canFullscreen ) {
							dipMod.add(dm);
						}
					}
			}
			
			if(dipMod.size() > 1) {
				Random r = new Random((new Date()).getTime());
				dmCompat = (DisplayMode) dipMod.get(r.nextInt(dipMod.size()));
			} else if(dipMod.size() == 1){
				dmCompat = (DisplayMode) dipMod.get(0);
			} 
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		//return default displaymode
		return dmCompat;
	}
}
