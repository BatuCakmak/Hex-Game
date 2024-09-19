package org.example.hexgame;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class GameModel extends GameApp {

    @FXML
    private Button playbutton;

    private static final double r = 20;
    private static final double n = Math.sqrt(r * r * 0.75);
    private static final double TILE_HEIGHT = 2 * r;
    private static final double TILE_WIDTH = 2 * n;
    private Color currentPlayerColor = Color.RED;
    private Tile[][] board;
    private Label statusLabel;
    private Label currentPlayerLabel;
    private boolean gameOver = false;
    private Stage primaryStage;

    @FXML
    protected void five() {
        setupBoard(5, 350, 250);
    }

    @FXML
    protected void eleven() {
        setupBoard(11, 650, 400);
    }

    @FXML
    protected void seventeen() {
        setupBoard(17, 1000, 650);
    }

    private void setupBoard(int size, int sceneWidth, int sceneHeight) {
        gameOver = false;
        AnchorPane tileMap = new AnchorPane();
        Scene content = new Scene(tileMap, sceneWidth, sceneHeight);

        primaryStage = new Stage();
        primaryStage.setScene(content);

        board = new Tile[size][size];
        int xStartOffset = 40;
        int yStartOffset = 40;

        GameLogic gameLogic = new GameLogic(board);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double xCoord = x * TILE_WIDTH + y * n + xStartOffset;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

                Tile tile = new Tile(xCoord, yCoord, x, y, r, n, TILE_WIDTH);
                board[x][y] = tile;
                tileMap.getChildren().add(tile);

                tile.setOnMouseClicked(e -> {
                    if (!tile.isFilled() && !gameOver) {

                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), tile);
                        fadeTransition.setFromValue(1.0);
                        fadeTransition.setToValue(0.0);
                        fadeTransition.setOnFinished(event -> {
                            // Animasyon tamamlandıktan sonra renk değişimi
                            tile.setFill(currentPlayerColor);
                            tile.setFillColor(currentPlayerColor);
                            tile.setFilled(true);

                            // Renk değişiminden sonra  tekrar uygula
                            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), tile);
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();

                            if (gameLogic.checkWinCondition(currentPlayerColor)) {
                                gameOver = true;
                                String winner = currentPlayerColor == Color.RED ? "Red Wins!" : "Blue Wins!";
                                statusLabel.setText(winner);
                                statusLabel.setLayoutX((content.getWidth() - statusLabel.getWidth()) / 2);
                                statusLabel.setLayoutY(content.getHeight() - 40);
                            }

                            currentPlayerColor = (currentPlayerColor == Color.RED) ? Color.BLUE : Color.RED;
                            currentPlayerLabel.setText("Current Player: " + (currentPlayerColor == Color.RED ? "RED" : "BLUE"));
                        });

                        fadeTransition.play();
                    }
                });


                if (gameLogic.isOuterTile(x, y, size)) {
                    addBorderLines(tileMap, xCoord, yCoord, x, y, size);
                }
            }
        }

        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        statusLabel.setLayoutX((sceneWidth - 200) / 2);
        statusLabel.setLayoutY((sceneHeight - 50) / 2);
        tileMap.getChildren().add(statusLabel);

        currentPlayerLabel = new Label("Current Player: RED");
        currentPlayerLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: black;");
        currentPlayerLabel.setLayoutX(sceneWidth - 150);
        currentPlayerLabel.setLayoutY(10);
        tileMap.getChildren().add(currentPlayerLabel);

        primaryStage.show();
    }

    private void addBorderLines(AnchorPane tileMap, double x, double y, int col, int row, int rowCount) {
        double[][] points = {
                {x, y},
                {x, y + r},
                {x + n, y + r * 1.5},
                {x + TILE_WIDTH, y + r},
                {x + TILE_WIDTH, y},
                {x + n, y - r * 0.5}
        };

        if (row == 0) {
            addLine(tileMap, points[0][0], points[0][1], points[5][0], points[5][1], Color.RED);
            addLine(tileMap, points[5][0], points[5][1], points[4][0], points[4][1], Color.RED);
        }
        if (col == 0) {
            addLine(tileMap, points[0][0], points[0][1], points[1][0], points[1][1], Color.BLUE);
            addLine(tileMap, points[2][0], points[2][1], points[1][0], points[1][1], Color.BLUE);
        }
        if (row == rowCount - 1) {
            addLine(tileMap, points[1][0], points[1][1], points[2][0], points[2][1], Color.RED);
            addLine(tileMap, points[2][0], points[2][1], points[3][0], points[3][1], Color.RED);
        }
        if (col == rowCount - 1) {
            addLine(tileMap, points[4][0], points[4][1], points[3][0], points[3][1], Color.BLUE);
            addLine(tileMap, points[5][0], points[5][1], points[4][0], points[4][1], Color.BLUE);
        }
    }

    private void addLine(AnchorPane tileMap, double startX, double startY, double endX, double endY, Color color) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(2);
        tileMap.getChildren().add(line);
    }
}
