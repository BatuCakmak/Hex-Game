package org.example.hexgame;

import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class GameLogic {
    private Tile[][] board;

    public GameLogic(Tile[][] board) {
        this.board = board;
    }

    public boolean checkWinCondition(Color playerColor) {
        Set<Tile> visited = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            if (playerColor == Color.RED) {
                if (board[i][0].getFillColor() == Color.RED && dfs(board[i][0], playerColor, visited)) {
                    return true;
                }
            } else {
                if (board[0][i].getFillColor() == Color.BLUE && dfs(board[0][i], playerColor, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(Tile tile, Color playerColor, Set<Tile> visited) {
        if (visited.contains(tile)) {
            return false;
        }
        visited.add(tile);

        int x = tile.getX();
        int y = tile.getY();

        if (playerColor == Color.RED && y == board.length - 1) {
            return true;
        } else if (playerColor == Color.BLUE && x == board.length - 1) {
            return true;
        }

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValidCoordinate(newX, newY) && board[newX][newY].getFillColor() == playerColor) {
                if (dfs(board[newX][newY], playerColor, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board.length;
    }

    public boolean isOuterTile(int col, int row, int rowCount) {
        return (col == 0 || row == 0 || col == rowCount - 1 || row == rowCount - 1 || col + row == rowCount - 1);
    }
}
