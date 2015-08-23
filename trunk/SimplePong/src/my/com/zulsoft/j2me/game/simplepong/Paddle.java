/*
 * Paddle.java
 *
 * Created on January 28, 2006, 10:10 AM
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
public class Paddle {
    
    public int currPosX, currPosY;
    public int vectorX, vectorY;
    public int paddleWidth, paddleHeight;
    public int velocity;
    
    private Graphics g;
    private boolean paddleHit;
       
    /** Creates a new instance of Paddle */
    public Paddle(Graphics g, int initPosX, int initPosY, int width, int height) {
        this.g = g;
        currPosX = initPosX < 0 ? 0 : initPosX;
        currPosY = initPosY < 0 ? 0 : initPosY;
        paddleWidth = width <= 0 ? 10 : width;
        paddleHeight = height <= 0 ? 5 : height;
        paddleHit = false;
        velocity = 5;
        vectorX = vectorY = 0;
    }
    
    public void move() {
        currPosX = currPosX + (vectorX*velocity);
        currPosY = currPosY + (vectorY*velocity);
    }
        
    public boolean detectCollisionWithBall(Ball ball) {
        
        int ballCenterX = ball.currPosX + (ball.ballSize/2);
        
        if((ball.currPosY + ball.ballSize) >= currPosY &&
               (ball.currPosY + ball.ballSize) <= (currPosY + paddleHeight) &&
                ballCenterX >= currPosX && 
                ballCenterX <= (currPosX + paddleWidth)) {
                paddleHit = true;
                return true;
        }
        return false;
    }
    
    public boolean detectCollisionWithWall(Wall wall) {
        if(currPosX > 0 && currPosX < (wall.wallWidth - paddleWidth)) return false;
        else return true;
    }
    
    public void paint() {
       if(paddleHit) {
           g.setColor(255,0,0);
           paddleHit = false;
       } else {
           g.setColor(255,255,255);
       }
       g.fillRect(currPosX, currPosY, paddleWidth, paddleHeight);
    }
    
}
