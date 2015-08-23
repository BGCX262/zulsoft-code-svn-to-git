/*
 * Ball.java
 *
 * Created on January 28, 2006, 9:53 AM
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
public class Ball {
    
    public int ballSize;
    public int currPosX;
    public int currPosY;
    public int vectorX;
    public int vectorY;
    public int velocity;
    
    private Graphics g;
    
    /** Creates a new instance of Ball */
    public Ball(Graphics bgGraphics, int initPosX, int initPosY, int ballSize) {
        g=bgGraphics;
        currPosX = initPosX;
        currPosY = initPosY;
        this.ballSize = ballSize;
        
        //set vector
        vectorX = 1; //ball move to the right
        vectorX = 1; // ball move downward
        velocity = 5;
    }
    
    public boolean detectCollisionWithWall(Wall wall) {
        return wall.detectCollisionWithBall(this);
    }
    
    public boolean detectCollisionWithPaddle(Paddle paddle) {
        return paddle.detectCollisionWithBall(this);
    }
    
    public void move() {
        currPosX = currPosX + (vectorX * velocity);
        currPosY = currPosY + (vectorY * velocity);
    }
    
    public void paint() {
       g.setColor(255,0,0);
       g.fillArc(currPosX, currPosY, ballSize, ballSize, 0, 360);
    }
}
