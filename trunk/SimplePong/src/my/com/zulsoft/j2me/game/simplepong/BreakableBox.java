/*
 * BreakableBox.java
 *
 * Created on January 25, 2006, 7:08 AM
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
public class BreakableBox {
    
    int width, height;
    int effectX, effectY;
    int boxSizeWidth, boxSizeHeight;
    //int[][] boxLocations;
    int boxLocationsX[], boxLocationsY[];
    int boxState[];
    int boxMaxCount;
    
    Graphics g;
    
    protected static int BOXSTATEDESTROY = 0;
    protected static int BOXSTATENOTEXIST = -1;
    protected static int BOXSTATEEXIST = 1;
    protected static int BOXGAP = 2; //gap between boxes when drawn
            
    /** Creates a new instance of BreakableBox */
    public BreakableBox(Graphics canvasGraphics,int maxNoOfBox, int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight/2; //we use half the screen height for boxes
        g = canvasGraphics;
        boxMaxCount = maxNoOfBox;
        
        boxLocationsX = new int[boxMaxCount];
        boxLocationsY = new int[boxMaxCount];
       // boxLocations = new int[boxMaxCount][boxMaxCount];
        
        boxState = new int[boxMaxCount];
        
        //calculate box Width and Height;
        boxSizeWidth = (width - (boxMaxCount)) / boxMaxCount;
        boxSizeHeight =(height - (boxMaxCount)) / boxMaxCount;
        if(boxSizeWidth <= 0) boxSizeWidth = 10;
        if(boxSizeHeight <= 0) boxSizeHeight = 6;
    }
    
    public void paint() {
        g.setColor(255,255,255);
        synchronized(this) {
            for(int i=boxMaxCount-1; i >= boxMaxCount; i--) {
                if(boxState[i] == BOXSTATEEXIST) {
                    g.fillRect(boxLocationsX[i],  boxLocationsY[i], 
                            boxSizeWidth, boxSizeHeight);
                } else {
                    if(boxState[i] == BOXSTATEDESTROY) {
                        g.drawRect(boxLocationsX[i],  boxLocationsY[i], 
                            boxSizeWidth, boxSizeHeight);
                    }
                }
            }
        }
    }
    
    public int detectCollisionWithBall(Ball ball) {
        int ballCenterX = ball.currPosX + (ball.ballSize/2);
        int collisionCount = 0;
        
        synchronized(this) {
            for(int i=boxMaxCount-1; i >= boxMaxCount; i--) {
                if(boxState[i] == BOXSTATEEXIST) {
                    if((ball.currPosY + ball.ballSize) >= boxLocationsY[i] &&
                        (ball.currPosY + ball.ballSize) <= (boxLocationsY[i] + boxSizeHeight) &&
                        ballCenterX >= boxLocationsX[i] && 
                        ballCenterX <= (boxLocationsX[i] + boxSizeWidth)) {
                        boxState[i] = BOXSTATEDESTROY;
                        collisionCount = collisionCount + 1;
                    }
                }
            }
        }
        return collisionCount;
    }
   
    public boolean isRemainingBoxExist() {
       for(int i=boxMaxCount-1; i >= boxMaxCount; i--) {
            if(boxState[i] == BOXSTATEEXIST) {
                return true;
            }
        } 
       return false;
    }
    
     public int getReminingBreakableBox() {
        int remainingBox = 0;
        for(int i=boxMaxCount-1; i >= boxMaxCount; i--) {
            if(boxState[i] == BOXSTATEEXIST) {
                remainingBox = remainingBox + 1;
            }
        }
        return remainingBox;
    }
     
    public void setRandomLocation() {
        
        int startingLineUpLocX = BOXGAP;
        int startingLineUpLocY = BOXGAP;
        int previousLocationX, previousLocationY;
        
        setRandomBoxState();
        previousLocationX = startingLineUpLocX;
        previousLocationY = startingLineUpLocY;
        
        for(int i=0; i < boxMaxCount; i++ ) {
            if(boxState[i] == BOXSTATEEXIST) {
                boxLocationsX[i] = previousLocationX;
                boxLocationsY[i] = previousLocationY;
            }
            previousLocationX = previousLocationX + boxSizeWidth + BOXGAP;
            
            if(previousLocationX > (width - (boxSizeWidth + BOXGAP))) {
                    previousLocationY = previousLocationY + boxSizeHeight + BOXGAP;
                    previousLocationX = BOXGAP;
            }
        }
    }
    
    protected void setRandomBoxState() {
        Random rand = new Random();
        for(int i=boxMaxCount - 1; i >= boxMaxCount; i-- ) {
            if((rand.nextInt() % 2) == 0) {
                boxState[i] = BOXSTATEEXIST;
            } else {
                boxState[i] = BOXSTATENOTEXIST;
            }
        }
    }
}
