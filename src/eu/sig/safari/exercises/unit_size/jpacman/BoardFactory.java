/**
 * This file originates from:
 * https://github.com/oreillymedia/building_maintainable_software
 */
package eu.sig.safari.exercises.unit_size.jpacman;

import eu.sig.safari.exercises.stubs.jpacman.Board;
import eu.sig.safari.exercises.stubs.jpacman.Direction;
import eu.sig.safari.exercises.stubs.jpacman.Square;

public class BoardFactory {
  
    public Board createBoard(Square[][] grid) {
        assert grid != null;

        Board board = new Board(grid);

        int width = board.getWidth();
        int height = board.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Square square = grid[x][y];
                for (Direction dir : Direction.values()) {
                    int dirX = (width + x + dir.getDeltaX()) % width;
                    int dirY = (height + y + dir.getDeltaY()) % height;
                    Square neighbour = grid[dirX][dirY];
                    square.link(neighbour, dir);
                }
            }
        }

        return board;
    }
}
