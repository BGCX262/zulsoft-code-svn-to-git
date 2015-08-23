/**
 * 
 */
package my.zulsoft.games.jpongpong;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import my.zulsoft.common.game.Config;
import my.zulsoft.common.game.CoreGame;
import my.zulsoft.common.game.GameTime;
import my.zulsoft.common.game.GraphicsDeviceManager;
import my.zulsoft.common.game.SpriteBatch;
import my.zulsoft.common.game.SpriteBatch.SpriteBlendMode;
import my.zulsoft.common.game.SpriteBatch.SpriteEffects;


/**
 * @author Faizul
 *
 */
@SuppressWarnings("deprecation")
public class JPongPongGame extends  CoreGame {
	
	enum GameState
    {
        INITIAL,
        START,
        PAUSE,
        GAME_OVER,
    }
    
    enum BallState
    {
        INITIAL,
        LAUNCH
    }

    enum PaddleState
    {
        STAY,
        MOVE_LEFT,
        MOVE_RIGHT
    }
            
    GraphicsDeviceManager graphics;
    SpriteBatch spriteBatch;
    //Random r;

    //KeyboardState oldKeyState;
    //GamePadState oldGamePadState;
    GameState gameState;
    int numOfLives;
   
    Texture ball;
    Rectangle ballRect;
    Vector2f ballVelocity;
    BallState ballState;

    Texture paddle;
    Rectangle paddleRect;
    Vector2f paddleVelocity;
    PaddleState paddleState;

    Texture background;
    Rectangle bckRect;

    //public SpriteFont sf;
    public TrueTypeFont sf;
    String scoreText;
    Vector2f scorePosition;
    int score; // total score
    
    Texture lives;

    Bricks bricks;
    
    public JPongPongGame() 
    {
    	graphics = new GraphicsDeviceManager(this);
    }

    /* (non-Javadoc)
	 * @see my.zulsoft.games.jpongpong.Game#initialize()
	 */
	@Override
	public void initialize() {
		
		// TODO Auto-generated method stub
		//initialization logic
        //r = new Random((int)DateTime.Now.ToBinary());
        scorePosition = new Vector2f(10f, 10f);
        gameState = GameState.INITIAL;
        //oldKeyState = Keyboard.GetState();
        
        super.initialize();
	}

	/* (non-Javadoc)
	 * @see my.zulsoft.games.jpongpong.Game#loadContent()
	 */
	@Override
	public void loadContent() {
		// Create a new SpriteBatch, which can be used to draw textures.
        spriteBatch = new SpriteBatch(getGraphicsDevice());
        try {
        	// load game content
        	ball = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(Config.getConfig().getConfigContentPath() + "/ball.png"));  //this.Content.Load<Texture2D>("ball");
        	paddle = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(Config.getConfig().getConfigContentPath() + "/paddle.png")); //this.Content.Load<Texture2D>("paddle");
        	ballRect = new Rectangle((int)(Display.getWidth() - ball.getWidth()) / 2, (int)(Display.getHeight() - paddle.getHeight() - ball.getHeight() - 15),(int) ball.getWidth(), (int) ball.getHeight());
        	paddleRect = new Rectangle((int)(Display.getWidth() - paddle.getWidth()) / 2, (int)(Display.getHeight() - paddle.getHeight() - 10), (int) paddle.getWidth(), (int) paddle.getHeight());
        	background = TextureLoader.getTexture("JPEG", ResourceLoader.getResourceAsStream(Config.getConfig().getConfigContentPath() + "/Night_Storm.jpg")); //this.Content.Load<Texture2D>("Night_Storm");
        	bckRect = new Rectangle(0, 0, Display.getWidth(), Display.getHeight());
        
        	lives = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(Config.getConfig().getConfigContentPath() + "/lives.png")); // this.Content.Load<Texture2D>("lives");
        
        	// load font from a .ttf file
    		InputStream inputStream	= ResourceLoader.getResourceAsStream(Config.getConfig().getConfigContentPath() + "Kooten.ttf");
    		
    		Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
    		awtFont = awtFont.deriveFont(12f); // set font size
    		sf = new TrueTypeFont(awtFont, true);
    			
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	                   
        bricks = new Bricks(this);
        super.loadContent();
	}

	/* (non-Javadoc)
	 * @see my.zulsoft.games.jpongpong.Game#unloadContent()
	 */
	@Override
	public void unloadContent() {
		
	}
	
	@Override
	public void update(GameTime gameTime)
    {
        // Allows the game to exit
        //if (Keyboard.GetState(PlayerIndex.One).IsKeyDown(Keys.F4) && 
        //        Keyboard.GetState(PlayerIndex.One).IsKeyDown(Keys.LeftAlt))
        //    this.stopGame();
                   
        updateInput();
        updateWorld();
                  
        super.update(gameTime);
    }

	
    protected void startNewGame()
    {
        ballVelocity = new Vector2f(1.0f, -1.0f);
        paddleVelocity = new Vector2f(0.0f,0.0f);
        bricks.generateBrick();
        scoreText = "Score: 0";
        score = 0;
        numOfLives = 5;
        ballState = BallState.INITIAL;
        paddleState = PaddleState.STAY;
    }

    protected void startNewLevel()
    {
        ballVelocity = new Vector2f(10.0f, -10.0f);
        paddleVelocity = new Vector2f(0.0f,0.0f);
        bricks.generateBrick();
        ballState = BallState.INITIAL;
        paddleState = PaddleState.STAY;
        ballRect = new Rectangle((int)(Display.getWidth() - ball.getWidth()) / 2, 
        		(int)(Display.getHeight() - paddle.getHeight() - ball.getHeight() - 15), 
        		(int)ball.getWidth(), (int) ball.getHeight());
        paddleRect = new Rectangle((int)(Display.getWidth() - paddle.getWidth() / 2), 
        		(int)(Display.getHeight() - paddle.getHeight() - 10), 
        		(int)paddle.getHeight(), 
        		(int)paddle.getHeight());
    }

    protected void updateInput()
    {
    	while(Keyboard.next()) {
    		
    		int key = Keyboard.getEventKey();
    		boolean state = Keyboard.getEventKeyState();
    		
    		// Allows the game to exit
            if (key == Keyboard.KEY_F4 && state) {
            	this.stopGame();
            	return;
            }
                
            ///user press enter key begin
    		if (key == Keyboard.KEY_RETURN && state)
    		{
                if (gameState == GameState.INITIAL)
                {
                    gameState = GameState.START;
                    startNewGame();
                }
                else if (gameState == GameState.START)
                {
                    gameState = GameState.PAUSE;
                }
                else if (gameState == GameState.PAUSE)
                {
                    gameState = GameState.START;
                }
                else if (gameState == GameState.GAME_OVER)
                {
                    gameState = GameState.INITIAL;
                }
    		}
                
    		///user press space key begin
    		if (key == Keyboard.KEY_SPACE && state)
    		{
                if (gameState == GameState.START)
                {
                    if(ballState != BallState.LAUNCH) ballState = BallState.LAUNCH;
                }
            }
        
    		///user press left key begin
    		if (key == Keyboard.KEY_LEFT && state)
    		{
                if (gameState == GameState.START)
                {
                    paddleState = PaddleState.MOVE_LEFT;
                }
                else
                {
                    paddleState = PaddleState.STAY;
                }
            }
        
    		///user press right key begin
    		if (key == Keyboard.KEY_RIGHT && state)
    		{
                if (gameState == GameState.START)
                {
                    paddleState = PaddleState.MOVE_RIGHT;
                }
                else
                {
                    paddleState = PaddleState.STAY;
                }
            }
        }
        
     }


    protected void updateWorld()
    {
        switch (gameState)
        {
            case INITIAL:
                {

                    break;
                }
            case PAUSE:
                	break;

            case START:
                {
                    if (numOfLives == 0)
                    {
                        gameState = GameState.GAME_OVER;
                        break;
                    }

                    if (paddleState == PaddleState.MOVE_LEFT)
                    {
                        if ((int)paddleRect.getX() <= 0)
                        {
                            paddleVelocity.setX(0.0f);
                        }
                        else
                        {
                            paddleVelocity.setX(-10.0f);
                        }
                        paddleRect.setX(paddleRect.getX() + (int)paddleVelocity.getX());
                    }

                    if (paddleState == PaddleState.MOVE_RIGHT)
                    {
                        if (((int)paddleRect.getX() + paddleRect.getWidth()) >= Display.getWidth())
                        {
                            paddleVelocity.setX(0.0f);
                        }
                        else
                        {
                            paddleVelocity.setX(10.0f);
                        }
                        paddleRect.setX(paddleRect.getX() + (int)paddleVelocity.getX());
                        
                    }

                    if (paddleState == PaddleState.STAY)
                    {
                        paddleVelocity.setX(0.0f);
                    }

                    if (ballState == BallState.INITIAL)
                    {
                        ballRect.setX(ballRect.getX() + (int)paddleVelocity.getX());
                    }

                    if (ballState == BallState.LAUNCH)
                    {
                        //int rVal = r.Next(15);
                        int rVal = 10;
                        if (ballRect.getX() <= 0.0)
                        {
                            ballVelocity.setX(rVal);
                        }
                        else if (ballRect.getX() >= Display.getWidth() - ballRect.getWidth())
                        {
                            ballVelocity.setX(-rVal);
                        }
                        else if (ballRect.getY() <= 0)
                        {
                            ballVelocity.setY(rVal);
                        }
                        // ball pass the paddle at the bottom of view
                        else if (ballRect.getY() >= Display.getHeight() - ballRect.getHeight())
                        {
                            ballRect.setX(paddleRect.getX() + (paddleRect.getWidth() / 2));
                            ballRect.setY(paddleRect.getY() - ballRect.getHeight() - 15);
                            numOfLives--;
                            ballState = BallState.INITIAL;
                        }

                        //detect collision between ball and paddle
                        if (ballRect.intersects(paddleRect))
                        {
                            ballVelocity.setX(((ballVelocity.getX() < 0) ? rVal : -rVal));
                            ballVelocity.setY(-rVal);
                            //scoreText = "Score: " + (score++);
                        }
                        //detect collision between ball and bricks
                        if (bricks.checkCollision(ballRect))
                        {
                            ballVelocity.setX(((ballVelocity.getX() < 0) ? rVal : -rVal));
                            ballVelocity.setY(rVal);
                            scoreText = "Score: " + (score++);
                        }
                        ballRect.setX(ballRect.getX() + (int)ballVelocity.getX());
                        ballRect.setY(ballRect.getY() + (int)ballVelocity.getY());

                        if (bricks.remainingBricks() == 0)
                        {
                            //gameState = GameState.GAME_OVER;
                            gameState = GameState.START;
                            startNewLevel();
                        }
                    }
                    break;
                }
            case GAME_OVER:
                {

                }
                break;
        }
    }
    /// <summary>
    /// This is called when the game should draw itself.
    /// </summary>
    /// <param name="gameTime">Provides a snapshot of timing values.</param>
    @Override
	public void draw(GameTime gameTime)
    {
        getGraphicsDevice().clear(Color.BLUE);

        //drawing code here
        spriteBatch.begin(SpriteBlendMode.AlphaBlend);
            if(gameState != GameState.START ) 
                spriteBatch.draw(background, bckRect, new Color(255,255,255,70));
            else
                spriteBatch.draw(background, bckRect, Color.WHITE);

            if (gameState == GameState.INITIAL || gameState == GameState.GAME_OVER )
            {
                if (gameState == GameState.GAME_OVER)
                {
                    String s = "Game Over! Total Score: " + score;
                    Vector2f spos = new Vector2f();
                    spos.setX((Display.getWidth() - sf.getWidth(s)) / 2);
                    spos.setY((Display.getHeight() - sf.getHeight(s)) / 2);

                    ReadableColor clr = new Color(0,0,0,20);

                    for (int c = 0; c < 4; c++)
                    {
                        spriteBatch.drawString(sf, s, spos, clr , 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);
                        spos.setX(spos.getX() + 1);
                        spos.setY(spos.getY() + 1);
                    }
                    
                    spos.setX(spos.getX() + 1);
                    spos.setY(spos.getY() + 1);
                    spriteBatch.drawString(sf, s, spos, Color.YELLOW, 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);

                    spos.setX(spos.getX() + 1);
                    spos.setY(spos.getY() + 1);
                    
                    spriteBatch.drawString(sf, s, spos, Color.ORANGE, 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);

                }
                else
                {
                    String s = "[Enter] to Begin New Game";
                    //Vector2 stringV = sf.MeasureString(s);
                    Vector2f spos = new Vector2f();
                    spos.setX((Display.getWidth() - sf.getWidth(s)) / 2);
                    spos.setY((Display.getHeight() - sf.getHeight(s)) / 2);

                    Color clr = new Color(0,0,0,20);

                    for (int c = 0; c < 4; c++)
                    {
                        spriteBatch.drawString(sf, s, spos, clr , 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);
                        spos.setX(spos.getX() + 1);
                        spos.setY(spos.getY() + 1);
                    }
                    
                    spos.setX(spos.getX() + 1);
                    spos.setY(spos.getY() + 1);
                    spriteBatch.drawString(sf, s, spos, Color.YELLOW, 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);

                    spos.setX(spos.getX() + 1);
                    spos.setY(spos.getY() + 1);
                    
                    spriteBatch.drawString(sf, s, spos, Color.ORANGE, 0.0f, new Vector2f(), 1.5f, SpriteEffects.None, 0);

                }
            }
            else
            {
                spriteBatch.draw(ball, ballRect, Color.WHITE);
                spriteBatch.draw(paddle, paddleRect, Color.WHITE);
                bricks.draw(spriteBatch);
                spriteBatch.drawString(sf, scoreText, scorePosition, Color.WHITE);
                Rectangle livesrect = new Rectangle((int)(Display.getWidth() - lives.getWidth()), 5, (int)lives.getWidth(), (int)lives.getHeight());
                for (int i = 1; i <= numOfLives; i++)
                {
                    spriteBatch.draw(lives, livesrect, Color.WHITE);
                    livesrect.setX((int)(livesrect.getX() - lives.getWidth()));
                }

                if (gameState == GameState.PAUSE)
                {
                    String s = "Game Paused";
                    //Vector2 stringV = sf.MeasureString(s);
                    Vector2f spos = new Vector2f();
                    spos.setX((Display.getWidth() - sf.getWidth(s)) / 2);
                    spos.setY((Display.getHeight() - sf.getHeight(s)) / 2);
                    spriteBatch.drawString(sf, s, spos, Color.ORANGE,0.0f, new Vector2f(),2.0f,SpriteEffects.None,0);
                }
            }
        spriteBatch.End();
        super.draw(gameTime);
    }

}
