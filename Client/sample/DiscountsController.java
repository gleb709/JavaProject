package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DiscountsController {

    private ObservableList<Tariff> tariffs = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private TableView<Tariff> tariffInPut;

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
    void initialize() {
        tariffName.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffName"));
        tariffPrice.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffPrice"));
        tariffID.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffID"));
        tariffInfo.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffInfo"));
        tariffSale.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffSale"));

        Connection connect = new Connection();
        Tariff tariff = new Tariff();
        Object obj = null;
        try {
            obj = connect.Connect(tariff ,"tariffList");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(obj);
        ArrayList<Tariff> tariff1 = (ArrayList<Tariff>) obj;
        System.out.println(tariff1.size());
        Iterator<Tariff> iterator = tariff1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            if(Integer.parseInt(tariff1.get(count).getTariffSale()) > 0) {
                tariffs.add(new Tariff(tariff1.get(count).getTariffName(), tariff1.get(count).getTariffID(),
                        tariff1.get(count).getTariffInfo(), tariff1.get(count).getTariffPrice(), tariff1.get(count).getTariffSale()));
            }
            count++;
            iterator.next();
        }

        tariffInPut.setItems(tariffs);
    }
}
