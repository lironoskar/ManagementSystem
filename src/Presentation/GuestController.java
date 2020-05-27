package Presentation;

import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class GuestController extends GeneralController implements Initializable {


    private Client m_client;//split by |


    private GridPane mainPane;

    @FXML private HBox mainView;
    @FXML private TextField tf_email;
    @FXML private PasswordField tf_password;
    @FXML private Label l_systemName;
    @FXML private ImageView iv_systemLogo;
    @FXML private ToolBar tb_menu;
    @FXML private BorderPane bp_main;



    public void loginButtonPushed(ActionEvent action){

        String Email = tf_email.getText();
        String password = tf_password.getText();
        if(Checker.isValidPassword(password)&&Checker.isValidEmailAddress(Email)){
            List<String> user = m_client.sendToServer("logIn|"+Email+"|"+password);
            String[] split = (user.get(0).split("\\|"));
            String loggedUser = split[0];
            if(loggedUser.equals("Login Failed")){
                showAlert("wrong email or password!",Alert.AlertType.ERROR);
                tf_email.setText("");
                tf_password.setText("");
            }
            else{
                tf_email.setText("");
                tf_password.setText("");
                user = getRolesFromSplitedText(split,1);

                setSceneByFXMLPath("UserView.fxml", user, loggedUser, m_client, mainPane);
            }
        }
        else
            showAlert("wrong email or password!",Alert.AlertType.ERROR);

    }

    public void buildRegistrationForm(ActionEvent action) {
        buildForm("fan", mainView, m_client,"", mainPane);
    }

    public void searchButtonPushed(ActionEvent actionEvent){
        clearMainView(mainView);
        clearMainView(mainPane);
        buildSearchView(mainPane, mainView, m_client,"");
    }

    public void viewInfoButtonPushed(ActionEvent actionEvent){
        clearMainView(mainView);
        clearMainView(mainPane);
        buildViewInfoScene(mainPane, mainView, m_client);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        l_systemName.setText(Main.SYSTEM_NAME);
        m_client = new Client(7567);
        setImage(iv_systemLogo, "resources/logo.png");
        mainPane = new GridPane();
        tb_menu.setOrientation(Orientation.HORIZONTAL);
        //add listeners to primary stage

    }


}
