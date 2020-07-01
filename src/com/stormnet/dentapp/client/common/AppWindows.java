package com.stormnet.dentapp.client.common;

import com.stormnet.dentapp.client.exceptions.CanNotCreateWindowException;
import com.stormnet.dentapp.client.exceptions.UnknownWindowException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppWindows {

    private static final AppWindows instance = new AppWindows();

    public static AppWindows getInstance() {
        return instance;
    }

    private Map<WindowConfigs, WindowController> appWindows;

    private AppWindows() {
        appWindows = new HashMap<>();
    }

    public void addMainStage(Stage mainStage) {
        createWindow(mainStage, WindowConfigs.InvitationWindow);
    }

    public void showWindow(WindowConfigs config) {
        WindowController windowController = getWindowByConfig(config);
        windowController.showWindow();
    }

    public void hideWindow(WindowConfigs config) {
        WindowController windowController = getWindowByConfig(config);
        windowController.hideWindow();
    }

    public void reloadWindow(WindowConfigs config) throws IOException {
        WindowController windowController = getWindowByConfig(config);
        windowController.reloadWindow();
    }

    private WindowController getWindowByConfig(WindowConfigs config) {
        WindowController windowController = appWindows.get(config);
        if (windowController == null) {
            throw new UnknownWindowException(config);
        }

        return windowController;
    }

    public <T> void setFormObject(WindowConfigs config, T object) throws IOException {
        WindowController windowController = getWindowByConfig(config);
        windowController.setFormObject(object);
    }

    public void createWindow(WindowConfigs windowConfig) {
        Stage stage = new Stage();
        createWindow(stage, windowConfig);
    }

    public void createWindow(Stage stage, WindowConfigs windowConfig) {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowConfig.getXmlUi()));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new CanNotCreateWindowException(e, windowConfig);
        }

        stage.setTitle(windowConfig.getTitle());

        if (windowConfig.isModalWindow()) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        Scene scene = new Scene(root, windowConfig.getWidth(), windowConfig.getHeight());
        stage.setScene(scene);

        Controller controller = fxmlLoader.getController();

        WindowController windowController = new WindowController(stage, controller);
        appWindows.put(windowConfig, windowController);
    }
}
