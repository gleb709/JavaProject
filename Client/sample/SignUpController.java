package sample;

import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField passwordButton;

    @FXML
    private TextField firstName;

    @FXML
    private Button enterButton;

    @FXML
    private TextField lastName;

    @FXML
    private TextField passwordTEstButton;

    @FXML
    private TextField loginButton;

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
            try {
                signUpNewUser();
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

   private void signUpNewUser() throws InterruptedException, SQLException {
       String firstName = this.firstName.getText();
       String lastName = this.lastName.getText();
       String userLogin = this.loginButton.getText();
       String userPassword = this.passwordButton.getText();
       String userTestPassword = this.passwordTEstButton.getText();
   if( firstName.length() != 0 && lastName.length() != 0 && userLogin.length() != 0)
          if (userTestPassword.length() > 5 && userTestPassword.equals(userPassword)) {
               User user = new User(firstName, lastName, userLogin, userPassword);
               Connection connect = new Connection();

               Object obj = connect.Connect(user, "signUp");
               System.out.println(obj);

               ArrayList<User> users = (ArrayList<User>) obj;

               if (users.size() != 0) {
                  Shake userSignUpAnim = new Shake(this.enterButton);
                 userSignUpAnim.playAnim();
               } else{
                    System.out.println ("Пользователь зарегистрирован");
                   Stage stage1 = (Stage) enterButton.getScene().getWindow();
                   stage1.close();
               }
          }else{
           Shake userLoginAnim = new Shake(passwordTEstButton);
           Shake userPasswordAnimation = new Shake(passwordButton);
           userLoginAnim.playAnim();
           userPasswordAnimation.playAnim();
          }
   else{
       Shake userLoginButton = new Shake(enterButton);
       userLoginButton.playAnim();
   }
   }
}

