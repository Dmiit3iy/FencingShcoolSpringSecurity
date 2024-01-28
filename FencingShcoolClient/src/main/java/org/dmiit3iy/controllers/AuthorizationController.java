package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.UserRepository;

import java.io.IOException;
import java.net.ConnectException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    Preferences preferences = Preferences.userRoot().node("fencing");
    private String username=preferences.get("userLogin","-1");
    private String password=preferences.get("userPassword", "-1");
    private UserRepository userRepository;



    @FXML
    public void buttonEnter(ActionEvent actionEvent) throws IOException {
        try {
        username = textFieldLogin.getText();
        password = textFieldPassword.getText();
        userRepository = new UserRepository( username,password);
        preferences.put("userLogin", username);
        preferences.put("userPassword", password);
        User user = null;
        user = userRepository.get();
            if (user != null) {
                preferences.putLong("userID", user.getId());
                //preferences.put("userLogin", user.getUserName());
              //  preferences.put("userPassword", user.getPassword());
                App.openWindow("main.fxml", "Main window", user);
                App.closeWindow(actionEvent);
            } else {
                App.showMessage("Mistake", "Incorrect login or password", Alert.AlertType.ERROR);
            }
        } catch (ConnectException e) {
            App.showMessage("Warning", "There is no connection to the server", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        App.openWindow("registration.fxml","Registration window",null);
    }
}
