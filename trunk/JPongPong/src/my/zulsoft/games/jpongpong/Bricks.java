package my.zulsoft.games.jpongpong;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import my.zulsoft.common.game.SpriteBatch;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

class Bricks
{
    class BrickStruct
    {
        public int offset;
        public int state;
        public int number;
    }

    private Texture[] bricktiles;
    private LinkedList<BrickStruct> listOfBrick;
    int brickWidth;
    int brickHeight;
    JPongPongGame g;

    public Bricks(JPongPongGame g)
    {
        brickWidth = 64; 
        brickHeight = 32;
        bricktiles = new Texture[3];
        try {
        	bricktiles[0] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/brick1.png")); 
        	bricktiles[1] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/brick2.png"));  //g.Content.Load<Texture2D>("brick2");
        	bricktiles[2] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/brick3.png")); //g.Content.Load<Texture2D>("brick3");
        } catch (IOException e) {
        	
        }
        this.g = g;
        listOfBrick = new LinkedList<BrickStruct>();
    }

    public int generateBrick()
    {
        listOfBrick.removeAll(listOfBrick);
        
        //list how many offset 
        
        Random r = new Random((new Date().getTime()));
        for (int i = 1; i <= r.nextInt(50) + 7; i++)
        {
            BrickStruct bs = new BrickStruct();
            bs.number = i;
            bs.offset = r.nextInt(bricktiles.length);
            bs.state = 1;

            listOfBrick.addLast(bs);
        }

        return listOfBrick.size();
    }

    public void draw(SpriteBatch b)
    {
        Rectangle destRect = new Rectangle(10, 40, brickWidth, brickHeight);
        for(BrickStruct i : listOfBrick) 
        {
            if (i.state != 0)
            {
            	int centerX = (int)((destRect.getX() + destRect.getWidth())/2);
            	int centerY = (int)((destRect.getY() + destRect.getHeight())/2);
                Vector2f v = new Vector2f(centerX, centerY);
                
                bricktiles[i.offset].bind();
                
                b.draw(bricktiles[i.offset], destRect, Color.WHITE);
                b.drawString(g.sf, String.valueOf(i.number) ,v , Color.WHITE);
                
            }
            destRect.setX(destRect.getX() + 64);
            if ((destRect.getX() + destRect.getWidth() - 10) > Display.getWidth())
            {
                destRect.setX(10);
                destRect.setY(destRect.getY() + brickHeight + 10);
            }
        }
    }

    public boolean checkCollision(Rectangle rect)
    {
        //bool collisionDetected = false;
        Rectangle destRect = new Rectangle(10, 40, brickWidth, brickHeight);
        for (BrickStruct i : listOfBrick)
        {
            if (i.state != 0)
            {
                if (rect.intersects(destRect))
                {
                    i.state = 0;
                    return true;
                }
            }

            destRect.setX(destRect.getX() + brickWidth);
            if ((destRect.getX() + destRect.getWidth() - 10) > Display.getWidth())
            {
                destRect.setX(10);
                destRect.setY(destRect.getY() + brickHeight + 10);
            }
        }
      
      return false;
    }

    public int remainingBricks()
    {
        int count = 0;
        for (BrickStruct b : listOfBrick)
        {
            if(b.state != 0) count = count + 1;
        }
        return count;

    }

    
}
