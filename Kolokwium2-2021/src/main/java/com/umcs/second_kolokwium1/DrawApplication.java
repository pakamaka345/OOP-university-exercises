package com.umcs.second_kolokwium1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class DrawApplication extends Application {
    private static GraphicsContext gc;
    private static Map<ClientHandler, Color> colors = new HashMap<>();
    private double offsetX = 0, offsetY = 0;
    private Label offsetLabel;
    private static List<MyStroke> strokes;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(500, 500);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        offsetLabel = new Label();
        updateOffsetLabel();

        VBox root = new VBox();
        root.getChildren().addAll(offsetLabel, canvas);

        Scene scene = new Scene(root, 500, 500);
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Draw Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(new Server(8080)).start();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> offsetY -= 10;
            case DOWN -> offsetY += 10;
            case LEFT -> offsetX -= 10;
            case RIGHT -> offsetX += 10;
        }
        updateOffsetLabel();
        redraw();
    }

    private void updateOffsetLabel() {
        offsetLabel.setText("Offset: (" + offsetX + ", " + offsetY + ")");
    }

    public void redraw() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        if (DrawApplication.strokes != null) {
            for (MyStroke stroke : DrawApplication.strokes) {
                gc.setStroke(stroke.getColor());
                gc.setLineWidth(2.0);
                gc.strokeLine(stroke.getPoints()[0] - offsetX,
                        stroke.getPoints()[1] - offsetY,
                        stroke.getPoints()[2] - offsetX,
                        stroke.getPoints()[3] - offsetY);
            }
        }
    }

    public static void setColor(ClientHandler clientHandler, Color color) {
        DrawApplication.colors.put(clientHandler, color);
    }

    public static void drawLines(String points, ClientHandler clientHandler) {
        double[] pointsArray = Arrays.stream(points.split(" "))
                .mapToDouble(Double::parseDouble)
                .toArray();

        Color color = DrawApplication.colors.getOrDefault(clientHandler, Color.BLACK);
        gc.setStroke(color);
        gc.setLineWidth(2.0);
        gc.strokeLine(pointsArray[0], pointsArray[1], pointsArray[2], pointsArray[3]);

        if (DrawApplication.strokes == null) {
            DrawApplication.strokes = new ArrayList<>();
        }
        DrawApplication.strokes.add(new MyStroke(pointsArray[0], pointsArray[1], pointsArray[2], pointsArray[3], color));
        System.out.println("Drawing new line:\n Color is " + color + "\n Points are: " + pointsArray[0] + " " + pointsArray[1] + " " + pointsArray[2] + " " + pointsArray[3]);
    }
}
