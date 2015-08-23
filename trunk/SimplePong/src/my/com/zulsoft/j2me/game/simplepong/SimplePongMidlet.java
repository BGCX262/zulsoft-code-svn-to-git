/*
 * SimplePongMidlet.java
 *
 * Created on September 3, 2005, 2:34 AM
 */

package my.com.zulsoft.j2me.game.simplepong;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Faizul Bin Ngsrimin
 * @version 1.0
 */
public class SimplePongMidlet extends MIDlet {
    protected boolean midletStarted;
    protected boolean midletPaused;
    protected Display screenDisplay;
    protected SimplePongCanvas gameCanvas;
    
    public void startApp() throws MIDletStateChangeException {
        //do one time init
        if(!midletStarted) {
            screenDisplay = Display.getDisplay(this);
            if(screenDisplay == null) throw new MIDletStateChangeException();
            gameCanvas = new SimplePongCanvas(this);
            screenDisplay.setCurrent(gameCanvas);
            //gameCanvas.showWelcomeScreen();
            midletStarted = true;
        }
        
        //do action needed after midlet was paused
        if(midletPaused) {
        	midletPaused = false;
        }
    }
    
    public void pauseApp() {
        midletPaused = true;
    }
    
    public void destroyApp(boolean unconditional) {
        gameCanvas.stopGame();
        gameCanvas = null;
        this.notifyDestroyed();
    }
}
