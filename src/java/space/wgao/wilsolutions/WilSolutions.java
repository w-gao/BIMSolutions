package space.wgao.wilsolutions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class WilSolutions extends Application {

    private static WilSolutions instance;

    private Stage window;

    /**
     * program's entry
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        if(WilSolutions.instance == null){
            WilSolutions.instance = this;
        }

        this.window = primaryStage;

        URL loc = this.getClass().getClassLoader().getResource("main.fxml");
        if(loc == null){
            System.out.println("Cannot find resource main.fxml.");
            System.out.println("Exiting...");
            return;
        }

        Parent root = FXMLLoader.load(loc);
        primaryStage.setTitle("BIM Solutions - v0.3.1");
        primaryStage.getIcons().add(new Image("/-icon.png"));

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        primaryStage.setScene(new Scene(root));
        //primaryStage.setResizable(false);
        primaryStage.setMinWidth(675.0);
        primaryStage.setMinHeight(450.0);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        primaryStage.show();
    }

    private void closeProgram() {
        this.window.close();

        System.out.println("Peace out! D:");
    }

    public static WilSolutions getInstance(){
        return WilSolutions.instance;
    }
}
