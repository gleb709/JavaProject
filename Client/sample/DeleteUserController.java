package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DeleteUserController {

    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Label deleteField;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TextField userName;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> loginField;

    @FXML
    private TableColumn<User, String> passwordField;

    @FXML
    private TableColumn<User, String> moneyField;

    @FXML
    void initialize() {

        loginField.setCellValueFactory(new PropertyValueFactory<User, String>("userLogin"));
        passwordField.setCellValueFactory(new PropertyValueFactory<User, String>("userPassword"));
        moneyField.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));

        table();

        deleteUserButton.setOnAction(actionEvent -> {
            String name = userName.getText();

            if(name.length() != 0){
                Connection connect1 = new Connection();
                User user2 = new User();

                user2.setUserLogin(name);
                ArrayList<User> list1= new ArrayList<User>();
                try {
                    list1 = (ArrayList<User>) connect1.Connect(user2,"check");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(list1.size() != 0) {
                    Connection connect2 = new Connection();
                    User user3 = new User();

                    user3.setUserLogin(name);
                    Object obj = new Object();
                    try {
                        obj = connect1.Connect(user3, "deleteUser");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }else{
                    Shake button = new Shake(userName);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(userName);
                button.playAnim();
            }
            users.clear();
            table();
        });

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });
    }

    public void table(){
        usersTable.refresh();
        String user = userName.getText();

        Connection connect = new Connection();
        User user1 = new User();
        ArrayList<User> list= new ArrayList<User>();
        try {
            list = (ArrayList<User>) connect.Connect(user1,"allUsers");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
        Iterator<User> iterator = list.listIterator();
        int count = 0;

        while(iterator.hasNext()){
            users.add(new User(list.get(count).getFirstName(),list.get(count).getLastName(),
                    list.get(count).getUserLogin(), list.get(count).getUserPassword()));
            iterator.next();
            count++;
        }
        usersTable.setItems(users);
    }
}
