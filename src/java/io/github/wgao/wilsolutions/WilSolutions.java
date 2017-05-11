package io.github.wgao.wilsolutions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * BIMSolutions
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class WilSolutions extends Application {

    private static Logger Log = LogManager.getLogger(WilSolutions.class);

    private static WilSolutions instance;

    private Stage window;

    /**
     * program's entry
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        if(Log.getLevel() == Level.DEBUG){
            Log.info("**************************************");
            Log.info(" Program is running in debug mode!");
            Log.info(" PLEASE DO NOT RUN IN PRODUCTION.");
            Log.info("**************************************");
        }else if(Log.getLevel() == Level.INFO){
            Log.info(" Program is running in production mode. No debug information will be displayed.");
        }

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
            Log.error("Cannot find resource main.fxml.");
            Log.error("Exiting...");
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

        Log.info(Log.getLevel() == Level.DEBUG ? "Peace out! D:" : "Program closed successfully.");
    }

    public static WilSolutions getInstance(){
        return WilSolutions.instance;
    }
}
