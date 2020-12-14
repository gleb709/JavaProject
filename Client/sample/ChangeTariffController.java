package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class ChangeTariffController {

    private ObservableList<OrderInfo> order = FXCollections.observableArrayList();
    private ObservableList<Tariff> tariffs = FXCollections.observableArrayList();


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
    private Button changeTariffButton;

    @FXML
    private TableView<Tariff> tariffsTable;

    @FXML
    private TableColumn<Tariff, String> tariffName;

    @FXML
    private TableColumn<Tariff, String> tariffPrice;

    @FXML
    private TableColumn<Tariff, String> tariffID;

    @FXML
    private TableColumn<Tariff, String> tariffInfo;

    @FXML
    private TableColumn<Tariff, String> tariffSale;

    @FXML
    private TextField tariffId;

    @FXML
    void initialize() throws InterruptedException {
        tariffName.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffName"));
        tariffPrice.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffPrice"));
        tariffID.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffID"));
        tariffInfo.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffInfo"));
        tariffSale.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffSale"));

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });

        changeTariffButton.setOnAction(actionEvent -> {
            String str1 = tariffId.getText();
            try {
                addProduct(str1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Connection connect = new Connection();
        Tariff product = new Tariff();

        Object obj = connect.Connect(product ,"tariffList");
        ArrayList<Tariff> tariff1 = (ArrayList<Tariff>) obj;
        Iterator<Tariff> iterator = tariff1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            tariffs.add(new Tariff(tariff1.get(count).getTariffName(),tariff1.get(count).getTariffID(),
                    tariff1.get(count).getTariffInfo(), tariff1.get(count).getTariffPrice(), tariff1.get(count++).getTariffSale()));
            iterator.next();
        }
        tariffsTable.setItems(tariffs);
    }

    private void addProduct(String id) throws InterruptedException {
        User user = new User();
        Connection connect = new Connection();

        int flag = 0;
        int key = 0;

        for (int i = 0; i < tariffs.size();i++){
            if(id.equals(tariffs.get(i).getTariffID())){
                flag = 1;
                key = i;
            }
        }
        int flag1 = 0;
        try {
            Integer.parseInt(id);
        } catch(Exception e) {
            flag1 = 1;
        }

        if(flag1 == 0) {
            if (flag == 1) {
                user.setUserTariff(id);
                user.setUserLogin(userName);
                 User obj = (User) connect.Connect(user, "changeTariff");
                System.out.println("Tariff changed");
            } else {
                Shake userLoginAnim = new Shake(changeTariffButton);
                userLoginAnim.playAnim();
            }
        }
        else{
            Shake button = new Shake(changeTariffButton);
            button.playAnim();
        }
    }
}
