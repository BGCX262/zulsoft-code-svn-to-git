using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

namespace PongPong
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
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

        KeyboardState oldKeyState;
        //GamePadState oldGamePadState;
        GameState gameState;
        int numOfLives;
        float speed = 0.025f; 
        Texture2D ball;
        Rectangle ballRect;
        Vector2 ballVelocity;
        BallState ballState;

        Texture2D paddle;
        Rectangle paddleRect;
        Vector2 paddleVelocity;
        PaddleState paddleState;

        Texture2D background;
        Rectangle bckRect;

        public SpriteFont sf;
        String scoreText;
        Vector2 scorePosition;
        int score; // total score
        int rVal = 10;
        Texture2D lives;

        Bricks bricks;

         public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            //graphics.IsFullScreen = true;
            Content.RootDirectory = "Content";
            IsFixedTimeStep = false;
            //
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            //initialization logic
            //r = new Random((int)DateTime.Now.ToBinary());
            scorePosition = new Vector2(10f, 10f);
            gameState = GameState.INITIAL;
            oldKeyState = Keyboard.GetState();
            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // load game content
            ball = this.Content.Load<Texture2D>("ball");
            paddle = this.Content.Load<Texture2D>("paddle");
            ballRect = new Rectangle((int)(this.GraphicsDevice.Viewport.Width - ball.Width) / 2, this.GraphicsDevice.Viewport.Height - paddle.Height - ball.Height - 15, ball.Width, ball.Height);
            paddleRect = new Rectangle((int)(this.GraphicsDevice.Viewport.Width - paddle.Width) / 2, this.GraphicsDevice.Viewport.Height - paddle.Height - 10, paddle.Width, paddle.Height);
            background = this.Content.Load<Texture2D>("Night_Storm");
            bckRect = new Rectangle(0, 0, this.GraphicsDevice.Viewport.Width, this.GraphicsDevice.Viewport.Height);
            
            lives = this.Content.Load<Texture2D>("lives");
            sf = this.Content.Load<SpriteFont>("SpriteFont1");
                       
            bricks = new Bricks(this);
            
            
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (Keyboard.GetState(PlayerIndex.One).IsKeyDown(Keys.F4) && 
                    Keyboard.GetState(PlayerIndex.One).IsKeyDown(Keys.LeftAlt))
                this.Exit();
                       
            UpdateInput();
            UpdateWorld(gameTime);
                      
            base.Update(gameTime);
        }

        protected void StartNewGame()
        {
            ballVelocity = new Vector2(1.0f, -1.0f);
            paddleVelocity = Vector2.Zero;
            bricks.GenerateBrick();
            scoreText = "Score: 0";
            score = 0;
            numOfLives = 5;
            ballState = BallState.INITIAL;
            paddleState = PaddleState.STAY;
        }

        protected void StartNewLevel()
        {
            ballVelocity = new Vector2(10.0f, -10.0f);
            paddleVelocity = Vector2.Zero;
            bricks.GenerateBrick();
            ballState = BallState.INITIAL;
            paddleState = PaddleState.STAY;
            ballRect = new Rectangle((int)(this.GraphicsDevice.Viewport.Width - ball.Width) / 2, this.GraphicsDevice.Viewport.Height - paddle.Height - ball.Height - 15, ball.Width, ball.Height);
            paddleRect = new Rectangle((int)(this.GraphicsDevice.Viewport.Width - paddle.Width) / 2, this.GraphicsDevice.Viewport.Height - paddle.Height - 10, paddle.Width, paddle.Height);
        }

        protected void UpdateInput()
        {
            KeyboardState newState = Keyboard.GetState();
            
            ///user press enter key begin
            if (newState.IsKeyDown(Keys.Enter))
            {
                if (!oldKeyState.IsKeyDown(Keys.Enter))
                {
                    if (gameState == GameState.INITIAL)
                    {
                        gameState = GameState.START;
                        StartNewGame();
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
            }
            else if (oldKeyState.IsKeyDown(Keys.Enter))
            {
                // Key was down last update, but not down now, so
                // it has just been released.
            }
            
            ///user press space key begin
            if (newState.IsKeyDown(Keys.Space))
            {
                if (!oldKeyState.IsKeyDown(Keys.Space))
                {
                    if (gameState == GameState.START)
                    {
                        if(ballState != BallState.LAUNCH) ballState = BallState.LAUNCH;
                    }
                }
            }
            else if (oldKeyState.IsKeyDown(Keys.Space))
            {
                // Key was down last update, but not down now, so
                // it has just been released.
            }

            ///user press left key begin
            if (newState.IsKeyDown(Keys.Left))
            {
                if (!oldKeyState.IsKeyDown(Keys.Left))
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
            }
            else if (oldKeyState.IsKeyDown(Keys.Tab))
            {
                // Key was down last update, but not down now, so
                // it has just been released.
                paddleState = PaddleState.STAY;
            }

            ///user press right key begin
            if (newState.IsKeyDown(Keys.Right))
            {
                if (!oldKeyState.IsKeyDown(Keys.Right))
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
            else if (oldKeyState.IsKeyDown(Keys.Right))
            {
                // Key was down last update, but not down now, so
                // it has just been released.
                paddleState = PaddleState.STAY;
            }
            
            oldKeyState = newState;
        }

        protected void UpdateWorld(GameTime gameTime)
        {
            //System.Diagnostics.Debug.Write("current millisecond=" + (gameTime.ElapsedGameTime.TotalMilliseconds) + "\n");
            //System.Diagnostics.Debug.Write("current speed=" + (float)(speed * gameTime.ElapsedGameTime.TotalMilliseconds) +"\n");
            float distance = speed * (float)gameTime.ElapsedGameTime.TotalMilliseconds;
            float obsolute_direction = rVal * distance;

            switch (gameState)
            {
                case GameState.INITIAL:
                    {

                        break;
                    }
                case GameState.PAUSE:
                    break;

                case GameState.START:
                    {
                        if (numOfLives == 0)
                        {
                            gameState = GameState.GAME_OVER;
                            break;
                        }

                        if (paddleState == PaddleState.MOVE_LEFT)
                        {
                            if ((int)paddleRect.X <= 0)
                            {
                                paddleVelocity.X = 0.0f;
                            }
                            else
                            {
                                paddleVelocity.X = -obsolute_direction;
                            }
                            paddleRect.X = paddleRect.X + (int)paddleVelocity.X;
                        }

                        if (paddleState == PaddleState.MOVE_RIGHT)
                        {
                            if (((int)paddleRect.X + paddleRect.Width) >= this.GraphicsDevice.Viewport.Width)
                            {
                                paddleVelocity.X = 0.0f;
                            }
                            else
                            {
                                paddleVelocity.X = obsolute_direction;
                            }
                            paddleRect.X = paddleRect.X + (int)paddleVelocity.X;
                            
                        }

                        if (paddleState == PaddleState.STAY)
                        {
                            paddleVelocity.X = 0.0f;
                        }

                        if (ballState == BallState.INITIAL)
                        {
                            ballRect.X = ballRect.X + (int)paddleVelocity.X;
                        }

                        if (ballState == BallState.LAUNCH)
                        {
                            //int rVal = r.Next(15);
                            
                            if (ballRect.X <= 0.0)
                            {
                                ballVelocity.X = obsolute_direction;
                            }
                            else if (ballRect.X >= this.GraphicsDevice.Viewport.Width - ballRect.Width)
                            {
                                ballVelocity.X = -obsolute_direction;
                            }
                            else if (ballRect.Y <= 0)
                            {
                                ballVelocity.Y = obsolute_direction;
                            }
                            // ball pass the paddle at the bottom of view
                            else if (ballRect.Y >= this.GraphicsDevice.Viewport.Height - ballRect.Height)
                            {
                                ballRect.X = paddleRect.X + (paddleRect.Width / 2);
                                ballRect.Y = paddleRect.Y - ballRect.Height - 15;
                                numOfLives--;
                                ballState = BallState.INITIAL;
                            }

                            //detect collision between ball and paddle
                            if (ballRect.Intersects(paddleRect))
                            {
                                ballVelocity.X = (ballVelocity.X < 0) ? obsolute_direction : -obsolute_direction;
                                ballVelocity.Y = -obsolute_direction;
                                //scoreText = "Score: " + (score++);
                            }
                            //detect collision between ball and bricks
                            if (bricks.CheckCollision(ballRect))
                            {
                                ballVelocity.X = (ballVelocity.X < 0) ? obsolute_direction : -obsolute_direction;
                                ballVelocity.Y = (ballVelocity.Y < 0) ? obsolute_direction : -obsolute_direction;
                                scoreText = "Score: " + (score++);
                            }
                            ballRect.X = ballRect.X + (int)ballVelocity.X;
                            ballRect.Y = ballRect.Y + (int)ballVelocity.Y;

                            if (bricks.RemainingBricks() == 0)
                            {
                                //gameState = GameState.GAME_OVER;
                                gameState = GameState.START;
                                StartNewLevel();
                            }
                        }
                        break;
                    }
                case GameState.GAME_OVER:
                    {

                    }
                    break;
            }
        }
        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);

            //drawing code here
#if XNA_4 
            spriteBatch.Begin();
#else
            spriteBatch.Begin(SpriteBlendMode.AlphaBlend);
#endif
                if(gameState != GameState.START ) 
                    spriteBatch.Draw(background, bckRect, new Color(255,255,255,70));
                else
                    spriteBatch.Draw(background, bckRect, Color.White);

                if (gameState == GameState.INITIAL || gameState == GameState.GAME_OVER )
                {
                    if (gameState == GameState.GAME_OVER)
                    {
                        string s = string.Empty;
                        s = "Game Over! Total Score: " + score;
                        Vector2 stringV = sf.MeasureString(s);
                        Vector2 spos = new Vector2();
                        spos.X = (GraphicsDevice.Viewport.Width - stringV.X) / 2;
                        spos.Y = (GraphicsDevice.Viewport.Height - stringV.Y) / 2;

                        Color clr = new Color(0,0,0,20);

                        for (int c = 0; c < 4; c++)
                        {
                            spriteBatch.DrawString(sf, s, spos, clr , 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);
                            spos.X = spos.X + 1;
                            spos.Y = spos.Y + 1;
                        }
                        
                        spos.X = spos.X + 1;
                        spos.Y = spos.Y + 1;
                        spriteBatch.DrawString(sf, s, spos, Color.YellowGreen, 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);

                        spos.X = spos.X + 1;
                        spos.Y = spos.Y + 1;
                        spriteBatch.DrawString(sf, s, spos, Color.Firebrick, 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);

                    }
                    else
                    {
                        string s = "[Enter] to Begin New Game";
                        Vector2 stringV = sf.MeasureString(s);
                        Vector2 spos = new Vector2();
                        spos.X = (GraphicsDevice.Viewport.Width - stringV.X) / 2;
                        spos.Y = (GraphicsDevice.Viewport.Height - stringV.Y) / 2;

                        Color clr = new Color(0, 0, 0, 20);
                        for (int c = 0; c < 4; c++)
                        {
                            spriteBatch.DrawString(sf, s, spos, clr, 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);
                            spos.X = spos.X + 1;
                            spos.Y = spos.Y + 1;
                        }
                        spos.X = spos.X + 1;
                        spos.Y = spos.Y + 1;
                        spriteBatch.DrawString(sf, s, spos, Color.YellowGreen, 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);

                        spos.X = spos.X + 1;
                        spos.Y = spos.Y + 1;
                        spriteBatch.DrawString(sf, s, spos, Color.Firebrick, 0.0f, Vector2.Zero, 1.5f, SpriteEffects.None, 0);
           
                    }
                }
                else
                {
                    spriteBatch.Draw(ball, ballRect, Color.White);
                    spriteBatch.Draw(paddle, paddleRect, Color.White);
                    bricks.Draw(spriteBatch);
                    spriteBatch.DrawString(sf, scoreText, scorePosition, Color.White);
                    Rectangle livesrect = new Rectangle(this.GraphicsDevice.Viewport.Width - lives.Width, 5, lives.Width, lives.Height);
                    for (int i = 1; i <= numOfLives; i++)
                    {
                        spriteBatch.Draw(lives, livesrect, Color.White);
                        livesrect.X = livesrect.X - lives.Width;
                    }

                    if (gameState == GameState.PAUSE)
                    {
                        string s = "Game Paused";
                        Vector2 stringV = sf.MeasureString(s);
                        Vector2 spos = new Vector2();
                        spos.X = (GraphicsDevice.Viewport.Width - stringV.X) / 2;
                        spos.Y = (GraphicsDevice.Viewport.Height - stringV.Y) / 2;
                        spriteBatch.DrawString(sf, s, spos, Color.Firebrick,0.0f,Vector2.Zero,2.0f,SpriteEffects.None,0);
                    }
                }
            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
