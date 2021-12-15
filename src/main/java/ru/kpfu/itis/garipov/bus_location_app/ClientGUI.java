package ru.kpfu.itis.garipov.bus_location_app;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    BusLocationAPI api = new BusLocationAPI();

    @Override
    public void start(Stage primaryStage) {
        ScrollPane display = new ScrollPane();
        display.setFitToWidth(true);
        display.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        display.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        display.setMaxSize(600 - 20, 600 - 90);
        display.setMinSize(600 - 20, 600 - 90);
        display.setLayoutY(10);
        display.setLayoutX(10);

        Label busInfo = new Label();
        busInfo.setAlignment(Pos.TOP_LEFT);
        busInfo.setWrapText(true);
        busInfo.setTextAlignment(TextAlignment.JUSTIFY);

        Label setNumber = new Label("Номер автобуса");
        setNumber.setLayoutX(100);
        setNumber.setLayoutY(530);

        TextField busNumber = new TextField();
        busNumber.setLayoutX(100);
        busNumber.setLayoutY(550);

        Button getInfoButton = new Button("Получить данные");
        getInfoButton.setLayoutX(350);
        getInfoButton.setLayoutY(550);
        getInfoButton.setOnAction(e -> {
            busInfo.setText(api.get(busNumber.getText()));
            display.setContent(busInfo);
        });

        Group root = new Group();

        ObservableList<Node> list = root.getChildren();

        list.addAll(display, getInfoButton, busNumber, setNumber);

        Scene mainScene = new Scene(root, 600, 600);
        primaryStage.setTitle("Автобусы в Казани");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
