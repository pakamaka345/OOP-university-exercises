package com.umcs.imageblur;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private int kernelRadius = 1;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kernel radius settings");

        Label label = new Label("Kernel Radius: " + kernelRadius);
        Slider slider = new Slider(1, 15, kernelRadius);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int value = newValue.intValue();
                if (value % 2 == 1) {
                    kernelRadius = value;
                    label.setText("Kernel radius: " + kernelRadius);
                }
            }
        });

        VBox vBox = new VBox(slider, label);
        Scene scene = new Scene(vBox, 300,100);
        stage.setScene(scene);
        stage.show();
    }

    public int getKernelRadius() {
        return kernelRadius;
    }

    public static void main(String[] args) {
        launch();
    }
}