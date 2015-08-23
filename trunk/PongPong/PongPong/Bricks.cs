using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;
using Microsoft.Xna.Framework.Graphics.PackedVector;

namespace PongPong
{
    class Bricks
    {
        class BrickStruct
        {
            public int offset;
            public int state;
            public int number;
        }

        private Texture2D[] bricktiles;
        private LinkedList<BrickStruct> listOfBrick;
        int brickWidth;
        int brickHeight;
        Game1 g;

        public Bricks(Game1 g)
        {
            brickWidth = 64; 
            brickHeight = 32;
            bricktiles = new Texture2D[3];
            bricktiles[0] = g.Content.Load<Texture2D>("brick1");
            bricktiles[1] = g.Content.Load<Texture2D>("brick2");
            bricktiles[2] = g.Content.Load<Texture2D>("brick3");
            this.g = g;
            listOfBrick = new LinkedList<BrickStruct>();
        }

        public int GenerateBrick()
        {
            listOfBrick.Clear();
            //list how many offset 
            
            Random r = new Random((int)DateTime.Now.ToBinary());
            for (int i = 1; i <= r.Next(50) + 7; i++)
            {
                BrickStruct bs = new BrickStruct();
                bs.number = i;
                bs.offset = r.Next(bricktiles.Length);
                bs.state = 1;

                listOfBrick.AddLast(bs);
            }

            return listOfBrick.Count();
        }

        public void Draw(SpriteBatch b)
        {
            Rectangle destRect = new Rectangle(10, 40, brickWidth, brickHeight);
            foreach(BrickStruct i in listOfBrick) 
            {
                if (i.state != 0)
                {
                    Vector2 v = new Vector2(destRect.Center.X, destRect.Center.Y);
                    b.Draw(bricktiles[i.offset], destRect, Color.White);
                    b.DrawString(g.sf, i.number.ToString(),v , Color.White);
                }
                destRect.X = destRect.X + 64;
                if ((destRect.X + destRect.Width - 10) > g.GraphicsDevice.Viewport.Width)
                {
                    destRect.X = 10;
                    destRect.Y = destRect.Y + brickHeight + 10;
                }
            }
        }

        public bool CheckCollision(Rectangle rect)
        {
            //bool collisionDetected = false;
            Rectangle destRect = new Rectangle(10, 40, brickWidth, brickHeight);
            foreach (BrickStruct i in listOfBrick)
            {
                if (i.state != 0)
                {
                    if (rect.Intersects(destRect))
                    {
                        i.state = 0;
                        return true;
                    }
                }

                destRect.X = destRect.X + brickWidth;
                if ((destRect.X + destRect.Width - 10) > g.GraphicsDevice.Viewport.Width)
                {
                    destRect.X = 10;
                    destRect.Y = destRect.Y + brickHeight + 10;
                }
            }
          
          return false;
        }

        public int RemainingBricks()
        {
            //method 1 traditional
            //int count = 0;
            //foreach (BrickStruct b in listOfBrick)
            //{
            //    if(b.state != 0) count = count + 1;
            //}
            // return count;

            //method 2 (a) LINQ
            //var queryNames = from b in listOfBrick
            //                 where b.state != 0
            //                 select b;
            // method 2(b)
            //var queryNames = listOfBrick.Where(b => b.state != 0);
            //return queryNames.Count();

            //method 3 LINQ Function
            return listOfBrick.Where(b => b.state != 0).Count();
        }

        
    }
}
