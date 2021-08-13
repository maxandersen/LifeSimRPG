package org.dionthorn.lifesimrpg;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    // keep track of which screen we are on for flagging
    public enum SCREEN {
        BOOT,
        CHARACTER_CREATION,
        MAIN,
        PLAYER_INFO,
        JOB_INFO,
        MAP,
        DEAD
    }

    // Constants
    public static SCREEN CURRENT_SCREEN = SCREEN.BOOT;
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) {
        // setup Stage
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle("LifeSimRPG");
        primaryStage.show();
        new Engine(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }

}