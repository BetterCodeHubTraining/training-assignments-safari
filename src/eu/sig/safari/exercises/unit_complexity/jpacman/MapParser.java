/** 
 * NOTICE: 
 * - This file has been heavily modified by the Software Improvement Group (SIG) to adapt it for training purposes
 * - The original file can be found here: https://github.com/SERG-Delft
 * - All dependencies in this file have been stubbed, some methods and/or their implementations in this file have been omitted, modified or removed
 * 
 */

package eu.sig.safari.exercises.unit_complexity.jpacman;

import java.util.List;

import eu.sig.safari.exercises.stubs.jpacman.BoardFactory;
import eu.sig.safari.exercises.stubs.jpacman.LevelFactory;
import eu.sig.safari.exercises.stubs.jpacman.NPC;
import eu.sig.safari.exercises.stubs.jpacman.PacmanConfigurationException;
import eu.sig.safari.exercises.stubs.jpacman.Square;

/**
 * Creates new {@link Level}s from text representations.
 *
 * @author Jeroen Roosen 
 */
public class MapParser {
	
    /**
     * The factory that creates the levels.
     */
    private final LevelFactory levelCreator;

    /**
     * The factory that creates the squares and board.
     */
    private final BoardFactory boardCreator;

    /**
     * Creates a new map parser.
     *
     * @param levelFactory
     *            The factory providing the NPC objects and the level.
     * @param boardFactory
     *            The factory providing the Square objects and the board.
     */
    public MapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
        this.levelCreator = levelFactory;
        this.boardCreator = boardFactory;
    }


    /**
     * Adds a square to the grid based on a given character. These
     * character come from the map files and describe the type
     * of square.
     *
     * @param grid
     *            The grid of squares with board[x][y] being the
     *            square at column x, row y.
     * @param ghosts
     *            List of all ghosts that were added to the map.
     * @param startPositions
     *            List of all start positions that were added
     *            to the map.
     * @param x
     *            x coordinate of the square.
     * @param y
     *            y coordinate of the square.
     * @param c
     *            Character describing the square type.
     */
    protected void addSquare(Square[][] grid, List<NPC> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case ' ':
                grid[x][y] = boardCreator.createGround();
                break;
            case '#':
                grid[x][y] = boardCreator.createGround();
                break;
            case '.':
                Square pelletSquare = boardCreator.createGround();
                grid[x][y] = pelletSquare;
                levelCreator.createPellet().occupy(pelletSquare);
                break;
            case 'G':
                Square ghostSquare = makeGhostSquare(ghosts);
                grid[x][y] = ghostSquare;
                break;
            case 'P':
                Square playerSquare = boardCreator.createGround();
                grid[x][y] = playerSquare;
                startPositions.add(playerSquare);
                break;
            default:
                throw new PacmanConfigurationException("Invalid character at "
                    + x + "," + y + ": " + c);
        }
    }

    private Square makeGhostSquare(List<NPC> ghosts) {
        Square ghostSquare = boardCreator.createGround();
        NPC ghost = levelCreator.createGhost();
        ghosts.add(ghost);
        ghost.occupy(ghostSquare);
        return ghostSquare;
    }
}