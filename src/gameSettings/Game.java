package gameSettings;

import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreIndicator;
import listeners.ScoreTrackingListener;

import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.Collidable;
import listeners.Counter;

/**
 * The class creating the game environment.
 */
public class Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private final SpriteCollection sprites; // Collection of all game sprites
    private final GameEnvironment environment; // gameSettings.Game environment for collisions
    private final GUI gui; // GUI for displaying the game
    private final Sleeper sleeper; // Sleeper for controlling frame rate
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter scoreCounter;



    /**
     * Create a new gameSettings.Game instance.
     * Initializes sprites, environment, GUI, sleeper and counters.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        this.sleeper = new Sleeper();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.scoreCounter = new Counter();

    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c = the collidable object to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Remove a collidable object from the game environment.
     *
     * @param c = the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);

    }

    /**
     * Adds a sprite object to the game sprite's collection.
     *
     * @param s = the sprite object to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Remove a sprite object from the game sprite's collection.
     *
     * @param s = the sprite object to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * @return the game environment object
     */
    public GameEnvironment getEnvironment() {
        return environment;
    }

    /**
     * Initializes the game by creating blocks, balls, and a paddle.
     */
    public void initialize() {
        // Create blocks
        List<Block> blocks = new ArrayList<>();
        addSprite(new Block(new Point(0, 0), WIDTH, HEIGHT, Color.blue));

        // Create new sprites.Block remover listener
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);

        // Create listeners.ScoreTrackingListener
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(scoreCounter);

        // Create rows of blocks with different colors
        int blockHeight = 20;
        int blockWidth = 50;
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK, Color.GREEN};
        int startY = 100; // Start below the top border block

        for (int i = 0; i < colors.length; i++) {
            int blocksInRow = 12 - i; // Decrease the number of blocks in each row
            int startX = WIDTH - blockWidth - blockHeight; // Start after the right border block
            for (int j = 0; j < blocksInRow; j++) {
                Block block = new Block(new Point(startX - j * blockWidth, startY + i * blockHeight),
                        blockWidth, blockHeight, colors[i]);
                blocks.add(block);
                block.addHitListener(scoreListener); // Register the blockRemover with the block
                block.addHitListener(blockRemover); // Register the blockRemover with the block
                remainingBlocks.increase(1); // Set initial count of blocks

            }
        }

        // Add the gray blocks on the borders separately
        blocks.add(new Block(new Point(0, 0), WIDTH, 20, Color.GRAY)); // Top border
        //  blocks.add(new sprites.Block(new geometry.Point(0, HEIGHT - 20), WIDTH, 20, Color.GRAY)); // Bottom border
        blocks.add(new Block(new Point(0, 0), 20, HEIGHT, Color.GRAY)); // Left border
        blocks.add(new Block(new Point(WIDTH - 20, 0), 20, HEIGHT, Color.GRAY)); // Right border

        // Create new sprites.Block remover listener
        BallRemover ballRemover = new BallRemover(this, remainingBalls);

        // Death region sprites.Block
        Block deathRegion = new Block(new Point(0, HEIGHT + 15), WIDTH, 0, Color.GRAY);
        blocks.add(deathRegion);
        deathRegion.addHitListener(ballRemover); // Register the ballRemover with the block


        // Add blocks to the game
        for (Block block : blocks) {
            block.addToGame(this); // Adds the block as both collidable and sprite
        }

        // Create a list to hold the balls
        List<Ball> balls = new ArrayList<>();
        Random rnd = new Random();


        // Create and add balls to the list
        Ball ball1 = new Ball(new Point(400, 300), 7, Color.WHITE, getEnvironment());
        ball1.setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(360), 5));
        balls.add(ball1);

        Ball ball2 = new Ball(new Point(300, 400), 7, Color.BLACK, getEnvironment());
        ball2.setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(360), 5));
        balls.add(ball2);

        Ball ball3 = new Ball(new Point(350, 450), 7, Color.PINK, getEnvironment());
        ball3.setVelocity(Velocity.fromAngleAndSpeed(rnd.nextInt(360), 5));
        balls.add(ball3);

        // Add initial balls to the game
        for (Ball ball : balls) {
            ball.addToGame(this); // Adds the ball to the game
            remainingBalls.increase(1); // Set initial count of blocks
        }

        // Create a paddle and pass the list of balls
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard, new Rectangle(new Point(350, HEIGHT - 40), 100, 20),
                Color.ORANGE, 10, balls);
        paddle.addToGame(this); // Add the paddle to the game

        // Create listeners.ScoreIndicator and add it to the game
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreCounter);
        scoreIndicator.addToGame(this);
    }

    // Method to check if no blocks are remaining
    private boolean noBlockRemaining() {
        return remainingBlocks.getValue() == 0;
    }

    // Method to check if no blocks are remaining
    private boolean noBallsRemaining() {
        return remainingBalls.getValue() == 0;
    }

    /**
     * Runs the game loop, handling rendering and updating at a fixed frame rate.
     */
    public void run() {
        int framesPerSecond = 60;
        long millisecondsPerFrame = 1000 / framesPerSecond;

        while (!noBallsRemaining()) {
            long startTime = System.currentTimeMillis();

            // Process user input, update game state, and render
            DrawSurface d = gui.getDrawSurface();
            sprites.drawAllOn(d);
            gui.show(d);
            sprites.notifyAllTimePassed();

            // Calculate the time taken for processing and rendering
            long elapsedTime = System.currentTimeMillis() - startTime;

            // Calculate the time to sleep to maintain the desired frame rate
            long sleepTime = millisecondsPerFrame - elapsedTime;

            // If the sleep time is positive, sleep for that duration
            if (sleepTime > 0) {
                sleeper.sleepFor(sleepTime);
            }

            // If there is no more blocks. get 100+ point bonus + finish the game.
            if (noBlockRemaining()) {
                scoreCounter.increase(100); // Bonus for clearing all blocks
                System.out.println(scoreCounter.getValue());
                gui.close();
            }
        }
        gui.close();
    }
}