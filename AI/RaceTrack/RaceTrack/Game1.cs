using System;
using System.Collections.Generic;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

using Reinforcer;
using Classifier; 
namespace RaceTrack
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;
        SpriteFont Font1;

        Reinforcer.RaceTrack raceTrack;
        Reinforcer.ReinforcementLearner vi;
        EntropyDecisionTree eTree;
        public Game1()
        {
            
            
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.LTrack);
            vi = new  QLearning(raceTrack);
            vi.Run(); 

            //vi = new ValueIteration(raceTrack); 
           // carPos = new Reinforcer.Vector2(raceTrack.Width / 2, raceTrack.Height / 2+3);
            graphics = new GraphicsDeviceManager(this);
            graphics.PreferredBackBufferHeight = 800; 
            graphics.PreferredBackBufferWidth = 1400;
            Content.RootDirectory = "Content";
            carPos = raceTrack.FindStartSquare();

            List<ExampleInstance> a = Classifier.DataLoader.Load("output.txt");
            List<ClassifierAttribute> attributes = Classifier.DataLoader.GenerateAttributes(a);
            List<string> classifications = Classifier.DataLoader.LoadPossibleClassifications(a);
            eTree = new EntropyDecisionTree(a, attributes, false);
            
            
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here

            base.Initialize();
        }
        Texture2D redSquare;
        Microsoft.Xna.Framework.Vector2 squareSize; 

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            redSquare = Content.Load<Texture2D>("red");
            squareSize = new Microsoft.Xna.Framework.Vector2(
                graphics.GraphicsDevice.Viewport.Width / raceTrack.Width,
                graphics.GraphicsDevice.Viewport.Height / raceTrack.Height);

            Font1 = Content.Load<SpriteFont>("CourierNew"); 
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }
        bool releasedKey = true; 
        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            // TODO: Add your update logic here
            //if (Keyboard.GetState().IsKeyDown(Keys.N))
                //vi.Run();
            try
            {
                if (Keyboard.GetState().IsKeyDown(Keys.G))
                {
                    if (releasedKey)
                    {
                        releasedKey = false;
                        Reinforcer.Vector2 acc =
                            new Reinforcer.Vector2(
                                vi.Ss[carPos.X, carPos.Y, carVel.X + 5, carVel.Y + 5].BestAction.Dx,
                                vi.Ss[carPos.X, carPos.Y, carVel.X + 5, carVel.Y + 5].BestAction.Dy);

                        Reinforcer.RaceTrack.Apply(carVel, acc);
                        this.raceTrack.Move(ref carPos, ref carVel);
                    }
                }
                else if (Keyboard.GetState().IsKeyDown(Keys.E))
                {
                    if (releasedKey)
                    {
                        releasedKey = false;
                        int c = 0;
                        int action = int.Parse(eTree.Classify(new string[] { 
                        (carPos.X/ReinforcementLearner.xBlockSize).ToString(), 
                        (carPos.Y/ReinforcementLearner.yBlockSize).ToString(), 
                        carVel.X.ToString(), carVel.Y.ToString()
                    }, out c));


                        Reinforcer.Vector2 acc =
                            new Reinforcer.Vector2(
                                vi.Ss[carPos.X, carPos.Y, carVel.X + 5, carVel.Y + 5].Actions[action].Dx,
                                vi.Ss[carPos.X, carPos.Y, carVel.X + 5, carVel.Y + 5].Actions[action].Dy);

                        Reinforcer.RaceTrack.Apply(carVel, acc);
                        this.raceTrack.Move(ref carPos, ref carVel);

                    }
                }

                else releasedKey = true;

                if (Keyboard.GetState().IsKeyDown(Keys.R))
                {
                    carPos = raceTrack.FindStartSquare();
                    carVel = new Reinforcer.Vector2(0, 0);
                }

                if (Keyboard.GetState().IsKeyDown(Keys.W))
                {
                    this.vi.WriteOutTrainingData("out.txt");
                }

            }
            catch (Exception e)
            {
                Console.WriteLine("here"); 
            }
            base.Update(gameTime);
        }

        
        int k = 0;
        Reinforcer.Vector2 carPos = new Reinforcer.Vector2(1, 7);
        Reinforcer.Vector2 carVel = new Reinforcer.Vector2(0, 0); 

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            try
            {
                graphics.GraphicsDevice.Clear(Color.CornflowerBlue);

                spriteBatch.Begin(SpriteBlendMode.AlphaBlend);

                /* int x = Mouse.GetState().X;
                 int y = Mouse.GetState().Y;

                 int bx = x / (int)squareSize.X;
                 int by = y / (int)squareSize.Y;
                 List<Reinforcer.Vector2> positions = new List<Reinforcer.Vector2>(); 
                 if (bx >= 0 && by >= 0)
                 {
                     positions = this.raceTrack.GetAllIntermediateSquares(
                         carPos.X,carPos.Y, bx, by);
                 }*/
                /*
                if ((k++ % 150) == 149)
                {
                    Reinforcer.Vector2 v = new Reinforcer.Vector2(bx-carPos.X , by- carPos.Y );
                    raceTrack.Move(ref carPos,ref v);
                }
                */
                for (int i = 0; i < raceTrack.Width; i++)
                    for (int j = 0; j < raceTrack.Height; j++)
                    {

                        /*if ((x > i * squareSize.X && x < (i + 1) * squareSize.X) &&
                             (y > j * squareSize.Y && y < (j + 1) * squareSize.Y))
                            continue;
                        bool draw = true;
                        foreach (Reinforcer.Vector2 p in positions)
                        {
                            if (p.X == i && p.Y == j)
                                draw = false; 
                        }
                        if (!draw) continue; */
                        if (i == carPos.X && j == carPos.Y)
                            continue;
                        Rectangle destRect = new Rectangle(
                            (int)(i * squareSize.X),
                            (int)(j * squareSize.Y),
                            (int)squareSize.X, (int)squareSize.Y);

                        Color c;
                        if (raceTrack.Squares[i, j] == SquareState.Road)
                            c = Color.White;
                        else if (raceTrack.Squares[i, j] == SquareState.Wall)
                            c = Color.Red;
                        else
                            c = Color.Green;
                        spriteBatch.Draw(redSquare, destRect, c);
                        if (raceTrack.Squares[i, j] == SquareState.Wall)
                            continue; 
                        spriteBatch.DrawString(Font1, vi.Ss[i, j, 5, 5].MinValue.ToString(),
                            new Microsoft.Xna.Framework.Vector2(i * squareSize.X + 3, j * squareSize.Y + 4), Color.Blue);
                        spriteBatch.DrawString(Font1, vi.Ss[i, j, 5, 5].BestAction.ToString(),
                            new Microsoft.Xna.Framework.Vector2(i * squareSize.X + 3, j * squareSize.Y + 18), Color.Blue);
                        //spriteBatch.Draw(redSquare, new Vector2(i * 32, j * 32), Color.White);

                    }

                spriteBatch.End();
                // TODO: Add your drawing code here
            }
            catch (Exception e)
            {
                Console.WriteLine("Here"); 
            }
            base.Draw(gameTime);
        }
    }
}
