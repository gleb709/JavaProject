package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordButton;

    @FXML
    private TextField loginButton;

    @FXML
    private Button enterButton;

    @FXML
    private Button signButton;

    @FXML
    private Button checkButton;

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
              String loginText = loginButton.getText().trim();
              String passwordText = passwordButton.getText().trim();
              if(!loginText.equals("") && !passwordText.equals("")) {
                  if(loginText.equals("Admin") && passwordText.equals("qwerty"))
                      loginAdmin();
                  else {
                      try {
                          loginUser(loginText, passwordText);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      } catch (IOException e) {
                          e.printStackTrace();
                      } catch (ClassNotFoundException e) {
                          e.printStackTrace();
                      }
                  }
              }
              else
                  System.out.println("Error");
        });

        signButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/SignUp.fxml"));
            try{
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        checkButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) checkButton.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/TestMenu.fxml"));
            try{
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private void loginAdmin(){
        Stage stage1 = (Stage) enterButton.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/AdminMenu.fxml"));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loginUser(String loginText, String passwordText) throws InterruptedException, IOException, ClassNotFoundException {
        Connection connect = new Connection();
        User user = new User();

        user.setUserLogin(loginText);
        user.setUserPassword(passwordText);

        Object obj = connect.Connect(user,"signIn");
        System.out.println(obj);
        ArrayList<User> users = (ArrayList<User>) obj;

        if(users.size() != 0) {
            Shake userLoginAnim = new Shake(loginButton);
            Shake userPasswordAnimation = new Shake(passwordButton);
            userLoginAnim.playAnim();
            userPasswordAnimation.playAnim();
        }
        else{
            Stage stage1 = (Stage) signButton.getScene().getWindow();
            stage1.close();
            UserMenuController userMenuController = new UserMenuController();
            FXMLLoader loader = new FXMLLoader();
            userMenuController.setUserName(user.getUserLogin());
            loader.setLocation(getClass().getResource("/sample/UserMenu.fxml"));
            loader.setController(userMenuController);
            Parent root = loader.load();
            try{
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}

