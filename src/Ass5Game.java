import gameSettings.Game;

/**
 * This class initializes and runs the game.
 * It creates an instance of the gameSettings.Game class, initializes it, and starts the game loop.
 */
public class Ass5Game {

    /**
     * The main method of the game application.
     * It creates a gameSettings.Game instance, initializes the game, and starts the game loop.
     *
     * @param args = command-line arguments (not used in this game)
     */
    public static void main(String[] args) {

        // Create a game instance
        Game game = new Game();

        // Initialize the game (setup game elements)
        game.initialize();

        // Run the game (start the game loop)
        game.run();
    }
}
