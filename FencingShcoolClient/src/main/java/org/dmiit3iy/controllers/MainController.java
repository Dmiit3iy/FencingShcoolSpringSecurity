package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import okhttp3.Credentials;
import org.dmiit3iy.App;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.ApprenticeRepository;
import org.dmiit3iy.retrofit.TrainerRepository;
import org.dmiit3iy.retrofit.UserRepository;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController implements ControllerData<User> {

    public Button buttonLogOff;
    public Label labelGreetings;
    public ListView<Trainer> listViewTrainer;
    public ListView<Apprentice> listViewApprentice;
    private User user;
    private Preferences preferences;// = Preferences.userRoot().node("fencing");
    private String login;// = preferences.get("userLogin","-1");
    private String password; //= preferences.get("userPassword", "-1");

    TrainerRepository trainerRepository;//= new TrainerRepository(login,password);
    ApprenticeRepository apprenticeRepository;//= new ApprenticeRepository(login,password);
    UserRepository userRepository;// = new UserRepository(login,password);

    public void initData(User user) {
        this.user = user;
        labelGreetings.setText("Приветствую, " + user.getFio() + "!");
    }

    @FXML
    void initialize() {

        preferences = Preferences.userRoot().node("fencing");
        login = preferences.get("userLogin", "-1");
        password = preferences.get("userPassword", "-1");

        trainerRepository = new TrainerRepository(login, password);
        apprenticeRepository = new ApprenticeRepository(login, password);
        userRepository = new UserRepository(login, password);


        try {
            ObservableList<Trainer> trainersList = FXCollections.observableList(trainerRepository.get());
            System.out.println(trainersList);
            this.listViewTrainer.setItems(trainersList);

            ObservableList<Apprentice> apprenticesList = FXCollections.observableList(apprenticeRepository.get());
            System.out.println(apprenticesList);
            this.listViewApprentice.setItems(apprenticesList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long id = preferences.getLong("userID", -1);
        System.out.println(id);
        try {
            labelGreetings.setText("Приветствую, " + userRepository.get(id).getFio() + "!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void buttonLogOff(ActionEvent actionEvent) {
        preferences.remove("userID");
        preferences.remove("userLogin");
        preferences.remove("userPassword");
        try {
            App.openWindow("authorization.fxml", "", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        App.closeWindow(actionEvent);
    }

    @FXML
    public void buttonAddTrainer(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/add.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    System.out.println("список тренеров при закрытии:" + trainerRepository.get());
                    ObservableList<Trainer> trainersList1 = FXCollections.observableList(trainerRepository.get());
                    ObservableList<Apprentice> apprenticesList1 = FXCollections.observableList(apprenticeRepository.get());
                    System.out.println("по закрытию" + trainersList1);
                    listViewTrainer.setItems(trainersList1);
                    listViewApprentice.setItems(apprenticesList1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.show();
    }

    @FXML
    public void buttonDeleteUser(ActionEvent actionEvent) throws IOException {
        userRepository.delete(user.getId());
        App.openWindow("authorization.fxml", "", null);
        App.closeWindow(actionEvent);

    }


    @FXML
    public void handleMouseTrainer(MouseEvent mouseEvent) throws IOException {
        Trainer trainer = listViewTrainer.getSelectionModel().getSelectedItems().get(0);
        System.out.println(trainer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/schedule.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        ScheduleController scheduleController = loader.getController();
        scheduleController.initData(trainer);

        stage.setOnCloseRequest(event -> {
                    try {
                        ObservableList<Trainer> trainersList1 = FXCollections.observableList(trainerRepository.get());
                        System.out.println("по закрытию" + trainersList1);
                        this.listViewTrainer.setItems(trainersList1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        stage.show();

    }

    @FXML
    public void handleMouseApprentice(MouseEvent mouseEvent) throws IOException {
        Apprentice apprentice = listViewApprentice.getSelectionModel().getSelectedItems().get(0);
        System.out.println(apprentice);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/apprentice.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        ApprenticeController apprenticeController = loader.getController();
        apprenticeController.initData(apprentice);

        stage.setOnCloseRequest(event -> {
                    try {
                        ObservableList<Apprentice> apprenticesList1 = FXCollections.observableList(apprenticeRepository.get());
                        System.out.println("по закрытию" + apprenticesList1);
                        this.listViewApprentice.setItems(apprenticesList1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        stage.show();
    }
}
