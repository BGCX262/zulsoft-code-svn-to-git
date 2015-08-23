/*
 * SimplePongCanvas.java
 *
 * Created on September 3, 2005, 10:59 AM
 */

package my.com.zulsoft.j2me.game.simplepong;

import java.util.*;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

class MyTimerTask extends TimerTask {
    
    boolean change;
    Canvas myCanvas;
    public MyTimerTask(Canvas myCanvas) {
        this.myCanvas = myCanvas;
        change = false;
    }
    
    public void run(){
        
        if(change) {
            ((SimplePongCanvas)myCanvas).showBestScore();
            //change = !change;
        } else {
            ((SimplePongCanvas)myCanvas).showWelcomeScreen();
            //change = !change;
        }
        change = !change;
    }
}

/**
 *
 * @author  Faizul Bin Ngsrimin
 * @version 1.0
 */
public class SimplePongCanvas extends Canvas 
                            implements Runnable, CommandListener {
    
    protected MIDlet parent;
    protected Display display;
    protected Image backBufferImage;
    protected Graphics backBufferGraphics;
    protected Thread gameThread;
    protected int screenWidth , screenHeight;
    protected int ballSize;
    protected int ballPosX, ballPosY;
    protected int ballAngleX, ballAngleY;
    protected int ballVol;
    protected int paddleHeight, paddleWidth;
    protected int paddlePosX, paddlePosY;
    protected int paddleVolXDir, paddleDir;
    protected Font myFont;
    protected RecordStore rsBestScore;
    protected Timer myTimer;
    protected int[] top3Score;
    protected int gameScore;
    protected int gameRestartCount;
    
    public boolean gameStart;
    public static String pongString = "PONG 1.0.3";
    public static String gameOverString = "GaMe OvEr";
    public static String scoreString = "Your Score: ";    
    public static String gameRetriesString = "Retries: ";
    public static int PADDLELEFT = -1;
    public static int PADDLERIGHT = 1;
        
    
    /**
     * constructor
     */
    public SimplePongCanvas(MIDlet parent) {
        
        this.parent = parent;
        display = Display.getDisplay(parent);
        try {
            // Set up this canvas to listen to command events
            setCommandListener(this);
            // Add the Exit command
            addCommand(new Command("Exit", Command.BACK, 1));
            addCommand(new Command("Start", Command.OK, 1));
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        backBufferImage = Image.createImage(screenWidth,screenHeight );
        backBufferGraphics = backBufferImage.getGraphics();
        
        top3Score = new int[3];
        top3Score[0] = 0;
        top3Score[1] = 0;
        top3Score[2] = 0;
        
        try {
            rsBestScore = RecordStore.openRecordStore("pong", true);
            getBestScore();
            sortBestScore();
        } catch(RecordStoreException rsE) { }
        
        ballSize = 5;
        ballPosX = 30; 
        ballPosY = 50;
        ballVol = 2;
        paddleWidth = 20;
        paddleHeight = 5;
        paddlePosX = 0;
        paddlePosY = screenHeight - paddleHeight;
        paddleVolXDir = 5;
        myFont = Font.getDefaultFont();
        ballAngleX = ballAngleY = 1;
        
        myTimer = new Timer();
        myTimer.schedule(new MyTimerTask(this), 1000, 1000);
    }
           
    /**
     * Called when a key is pressed.
     */
    protected  void keyPressed(int keyCode) {
        
        
        if(!gameStart) return;
        if(getGameAction(keyCode) == Canvas.LEFT) {
          synchronized(this) {
              paddleDir = SimplePongCanvas.PADDLELEFT;
              
              //this.serviceRepaints();
          }  
        } else if(getGameAction(keyCode) == Canvas.RIGHT) {
           synchronized(this) {
              paddleDir = SimplePongCanvas.PADDLERIGHT;
             
              //this.serviceRepaints();
          } 
        } 
    }
    
    /**
     * Called when a key is released.
     */
    protected  void keyReleased(int keyCode) {
        if(!gameStart) return;
        paddleDir = 0;
    }

    protected void showNotify() {
        if(gameStart) {
            gameThread = new Thread(this);
            gameThread.start();
        } 
    }

    protected void hideNotify() {
        if(gameStart) {
            gameThread = null;
        }
    }
    
    protected void getBestScore() {
        try {
            RecordEnumeration re = rsBestScore.enumerateRecords(null, null,true);
            int counter = 0;
            while(re.hasNextElement()) {
                byte[] data = re.nextRecord();
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                DataInputStream dis = new DataInputStream(bais);
                try {
                     top3Score[counter] = dis.readInt();
                     dis.close();
                     bais.close();
                } catch(IOException e) { top3Score[counter]= 0; }
                counter+=1;
                if (counter > 2) return;
            }
        } catch (RecordStoreException rse) { }
    }
    
    protected void storeBestScore() {
        //delete all RMS Records
        boolean allDeleted;
        RecordEnumeration re;
        do {
            allDeleted = true;
            try {
                re = rsBestScore.enumerateRecords(null, null, true);
            } catch (RecordStoreNotOpenException rsnoe) {
                return;
            }
        
            while(re.hasNextElement()) {
                try {
                    rsBestScore.deleteRecord(re.nextRecordId());
                } catch (RecordStoreException rse) {allDeleted = false;}
            }
        } while(allDeleted==false);
        
        //put new records
        for(int i=2; i >=0; i--) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            try {
                dos.writeInt(top3Score[i]);
                dos.flush();
                byte[] data = baos.toByteArray();
                rsBestScore.addRecord(data,0, data.length);
                dos.close();
                baos.close();
            } catch(IOException e) {}
              catch(RecordStoreException rse) {}
        }
    }
    
    protected void sortBestScore() {
        int tempVal;
        
        if(top3Score[2] > top3Score[0]) {
            //swap the 3rd number with the first
            tempVal= top3Score[0];
            top3Score[0] = top3Score[2];
            top3Score[2] = tempVal;
        }
        
        if(top3Score[1] > top3Score[0]) {
                tempVal = top3Score[0];
                top3Score[0] = top3Score[1];
                top3Score[1] = tempVal;
        } else {
            if(top3Score[1] < top3Score[2]) {
               tempVal = top3Score[2];
               top3Score[2] = top3Score[1];
               top3Score[1] = tempVal;
            }
        }
    }
    
    protected boolean putScoreWithTop3() {
        for(int i=2; i >=0; i--) {
            if(top3Score[i] < gameScore) {
                top3Score[i] = gameScore;
                return true;
            }
        }
        return false;
    }
    
    /**
     * paint
     */
    public void paint(Graphics g) {
        g.drawImage(backBufferImage, 0,0, Graphics.TOP | Graphics.LEFT);
    }

    public void showWelcomeScreen() {
        
        int stringWidth = myFont.stringWidth(pongString);
        
        backBufferGraphics.setColor(0);
        backBufferGraphics.fillRect(0,0, screenWidth, screenHeight);
        backBufferGraphics.setColor(255, 0, 0);
        backBufferGraphics.drawString(pongString, (screenWidth - stringWidth)/ 2, 
                                        (screenHeight - myFont.getHeight()) / 2, 
                                         Graphics.TOP | Graphics.LEFT);
        repaint();
    }
    
    public void showBestScore() {
        String theStr = "BEST SCORE";
        int stringWidth = myFont.stringWidth(theStr);
        
        backBufferGraphics.setColor(0);
        backBufferGraphics.fillRect(0,0, screenWidth, screenHeight);
        backBufferGraphics.setColor(255, 0, 0);
        backBufferGraphics.drawString(theStr, (screenWidth - stringWidth)/ 2, 
                                        myFont.getHeight() + 10, 
                                         Graphics.TOP | Graphics.LEFT);
        
        for(int i=2; i >= 0; i--) {
             theStr = "-" + (i+1) + "-" + top3Score[i];
            stringWidth = myFont.stringWidth(theStr);
            backBufferGraphics.drawString(theStr, (screenWidth - stringWidth)/ 2, 
                                        myFont.getHeight()*(i+2) + 10, 
                                         Graphics.TOP | Graphics.LEFT);
        }
        repaint();
    }
    
    public void showGameStat() {
        String screenText = scoreString + gameScore;
        int stringWidth = myFont.stringWidth(screenText);
        
        backBufferGraphics.setColor(0);
        backBufferGraphics.fillRect(0,0, screenWidth, screenHeight);
        backBufferGraphics.setColor(255, 0, 0);
        backBufferGraphics.drawString(screenText, (screenWidth - stringWidth)/ 2, 
                                        (screenHeight - myFont.getHeight()) / 2, 
                                         Graphics.TOP | Graphics.LEFT);
        
        if(gameStart) {
            screenText = gameRetriesString + gameRestartCount;
            stringWidth  = myFont.stringWidth(screenText);
            
        } else {
            screenText = gameOverString;
            stringWidth  = myFont.stringWidth(screenText);
            if(putScoreWithTop3()) {
                storeBestScore();
            }
            sortBestScore();
            myTimer = new Timer();
            myTimer.schedule(new MyTimerTask(this),1000,1000);
        }
        backBufferGraphics.drawString(screenText,
                                        (screenWidth - stringWidth)/ 2,
                                        (screenHeight / 2) + myFont.getHeight(), 
                                         Graphics.TOP | Graphics.LEFT);
        repaint();
        //AlertType.ALARM.playSound(display);
    }
    
    public void startGame() {
        if(!gameStart) {
            gameRestartCount = 3;
            gameThread = new Thread(this);
            gameThread.start();
            gameStart = true;
        }
    }
    
    public void stopGame() {
        if(gameStart) {
            gameThread = null;
            gameStart = false;
            showGameStat();
        }
    }
    
    public void run() {
        
        Random myRandom = new Random();
        byte G4 = (byte)(ToneControl.C4 + 7);
        boolean hit = false;
        boolean paddleHit = false;
        
        Thread currThread = Thread.currentThread();
        while (gameStart && currThread == gameThread) {
            
            //check if the player have lost all retries 
            if(gameRestartCount <= 0) {
               stopGame();
               return;
            }
            
            //clear  previous screen
            backBufferGraphics.setColor(0);
            backBufferGraphics.fillRect(0,0,screenWidth, screenHeight);
            
            //draw the ball
            backBufferGraphics.setColor(255,0,0);
            backBufferGraphics.fillArc(ballPosX, ballPosY, ballSize, ballSize,
                                        0, 360);
            //draw the paddle
            if(paddleHit) {
                backBufferGraphics.setColor(255,0,0);
                paddleHit = false;
            }
            else
                backBufferGraphics.setColor(255,255,255);
            
            backBufferGraphics.fillRect(paddlePosX,paddlePosY, 
                                    paddleWidth, paddleHeight);
            
            repaint();
            
            if(hit) {
                try {
                    Manager.playTone(G4,1, 100);
                  } catch (Exception e) {} 
                  hit =false;
            }
            
            if(paddlePosX <= 0) {
                paddlePosX=0;
//                hit = true;
            }
            else {
                if(paddlePosX >= (screenWidth - paddleWidth)) {
                    paddlePosX = (screenWidth - paddleWidth);
//                    hit = true;
                }                
            }
            paddlePosX = paddlePosX + paddleVolXDir*paddleDir;
            
            if(ballPosX <= 0) {
                  ballAngleX = 1;
                  ballAngleY = ((myRandom.nextInt() % 2) == 0 ? 1 : -1);
                  hit = true;
            } else {
                if((ballPosX + ballSize) >= screenWidth) {
                    ballAngleX = -1;
                    ballAngleY = ((myRandom.nextInt() % 2) == 0 ? -1 : 1);
                    hit = true;
                } 
            } 
            
            if(ballPosY <= 0) {
                  ballAngleY = 1;
                  ballAngleX = ((myRandom.nextInt() % 2) == 0 ? -1 : 1);
                  hit = true;
            } else {
                
                if( ballPosX >= (paddlePosX - ballSize) &&
                    ballPosX <= (paddlePosX + paddleWidth - ballSize) &&   
                    ballPosY >= (paddlePosY - ballSize) &&
                    ballPosY  <= (paddlePosY + paddleHeight - ballSize)) {
                    //the ball hit the paddle add 1 to gameScore
                    // bounce the ball back
                    gameScore = gameScore + 1;
                    ballAngleY = -1;
                    ballAngleX = ((myRandom.nextInt() % 2) == 0 ? -1 : 1);
                    hit = true;
                    paddleHit = hit;
                } else {
                    if(ballPosY >= (screenHeight)) {
                        //reset ball location if the ball goes beyond the paddle
                       ballPosX = 30; 
                       ballPosY = 50;
                       // substract the retries count gameRestartCount
                       gameRestartCount = gameRestartCount - 1;
                       showGameStat();
                       try {
                            //gameThread.sleep(500);
                    	   Thread.sleep(500);
                        } catch (InterruptedException e) {}
                    }
                }
            }
            
            ballPosX = ballPosX + (ballAngleX * ballVol);
            ballPosY = ballPosY + (ballAngleY * ballVol);

            try {
                //gameThread.sleep(50);
            	Thread.sleep(50);
            } catch (InterruptedException e) {}
                    
        }
    }
    
    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
        if(command.getCommandType()==Command.BACK) {
            try {
                rsBestScore.closeRecordStore();
            } catch(RecordStoreException e){}
            ((SimplePongMidlet)parent).destroyApp(true);
        }
        
        if(gameStart) return;
        if(command.getCommandType()==Command.OK) {
            if(command.getLabel()=="Start") {
                myTimer.cancel();
                gameScore = 0;
                startGame();
            }
        }
    }
    
}
