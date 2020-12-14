package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePasswordController {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button changeButton;

    @FXML
    private TextField password;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField repPassword;

    @FXML
    void initialize() {

        changeButton.setOnAction(actionEvent -> {
            String pass = password.getText().trim();
            String newPass = newPassword.getText().trim();
            String repPass = repPassword.getText().trim();

            Connection connect = new Connection();
            User user = new User();

            user.setUserLogin(getUserName());

            try {
                user = (User)connect.Connect(user, "money");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(newPass.equals(repPass) && newPass.length() > 5)
                if(pass.equals(user.getUserPassword())){
                    user.setUserPassword(newPass);
                    user.setUserLogin(getUserName());
                    try {
                        Object user1 = connect.Connect(user, "password");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Shake oldPass = new Shake(password);
                    oldPass.playAnim();
                }
            else{
                Shake newPassShake = new Shake(newPassword);
                Shake newPassTestShake = new Shake(repPassword);
                newPassShake.playAnim();
                newPassTestShake.playAnim();
            }


        });

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });
    }
}
