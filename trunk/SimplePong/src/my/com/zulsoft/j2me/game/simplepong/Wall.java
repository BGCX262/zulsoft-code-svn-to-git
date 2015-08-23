/*
 * Wall.java
 *
 * Created on January 28, 2006, 10:44 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package my.com.zulsoft.j2me.game.simplepong;

import java.util.Random;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Administrator
 */
public class Wall {
    
    int wallWidth, wallHeight;
    Graphics g;
    
    /** Creates a new instance of Wall */
    public Wall(Graphics g, int width, int height) {
        wallWidth = width;
        wallHeight = height;
        this.g = g;
    }
    
    public boolean detectCollisionWithBall(Ball ball) {
       if(ball.currPosX <= 0 || ball.currPosX >= (wallWidth -  ball.ballSize)
       || ball.currPosY <= 0) return true;
       
       return false;
    }
    
    public boolean detectCollisionWithPaddle(Paddle paddle) {
        return paddle.detectCollisionWithWall(this);
    } 
    
    public boolean checkIfBallAtTheBottom(Ball ball) {
        if(ball.currPosY > (wallHeight - ball.ballSize)) return true;
        return false;
    }
    
    public void paint() {
      g.setColor(0, 255,255);
      g.drawRect(0,0,wallWidth-1, wallHeight-1);
    }
}
