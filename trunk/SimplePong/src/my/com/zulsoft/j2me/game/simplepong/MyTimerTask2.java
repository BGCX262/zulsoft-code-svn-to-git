/*
 * MyTimerTask.java
 *
 * Created on February 1, 2006, 9:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package my.com.zulsoft.j2me.game.simplepong;

import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
class MyTimerTask2 extends TimerTask
{
        boolean change;
        SimplePongCanvas canvas;
        public MyTimerTask2(SimplePongCanvas c) {
            canvas = c;
            change = false;
        }
    
        public void run() {
            if(change) {
                canvas.showBestScore();
                change = !change;
            } else {
                canvas.showWelcomeScreen();
                change = !change;
            }
        }
   
}
