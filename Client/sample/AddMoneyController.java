package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import static java.lang.Integer.parseInt;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMoneyController {

    protected String userName;

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
    private Label moneyField;

    @FXML
    private Button addMoneyButton;

    @FXML
    private TextField moneyText;

    @FXML
    void initialize() throws InterruptedException {
        Connection connect = new Connection();
        User user = new User();

        user.setUserLogin(getUserName());
        User obj = (User)connect.Connect(user,"money");
        moneyField.setText("Ваш баланс: " + obj.getUserMoney());

        addMoneyButton.setOnAction(actionEvent -> {
            String moneyTxt = moneyText.getText().trim();
            int flag = 0;
            try {
                Integer.parseInt(moneyTxt);
            } catch(Exception e) {
                flag = 1;
            }
            if( flag == 0) {
                User user1 = new User();
                user1.setUserMoney(moneyTxt);
                user1.setUserLogin(getUserName());
                Object obj1 = null;
                try {
                    obj1 = connect.Connect(user1, "addMoney");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user1 = (User) obj1;
                moneyField.setText("Ваш баланс: " + user1.getUserMoney());
            }
            else {
                Shake button = new Shake(moneyText);
                button.playAnim();
            }
        });

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });
    }
}
