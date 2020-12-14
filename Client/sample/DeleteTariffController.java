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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DeleteTariffController {

    private ObservableList<Tariff> tariffs = FXCollections.observableArrayList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField idField;

    @FXML
    private TableView<Tariff> tariffInPut;

    @FXML
    private TableColumn<Tariff, String> tariffName;

    @FXML
    private TableColumn<Tariff, String> tariffPrice;

    @FXML
    private TableColumn<Tariff, String> tariffID;

    @FXML
    private TableColumn<Tariff, String> tariffSale;

    @FXML
    private TableColumn<Tariff, String> tariffInfo;

    @FXML
    void initialize() throws InterruptedException {

        tariffName.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffName"));
        tariffPrice.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffPrice"));
        tariffID.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffID"));
        tariffInfo.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffInfo"));
        tariffSale.setCellValueFactory(new PropertyValueFactory<Tariff, String>("tariffSale"));

        table();

        deleteButton.setOnAction(actionEvent -> {
            Connection connect = new Connection();
            Tariff tariff = new Tariff();

            String id = new String();

            id = idField.getText();
            int flag = 0;

            if(id.length() != 0) {
                try {
                    Integer.parseInt(id);
                } catch (Exception e) {
                    flag = 1;
                }
                if (flag == 0) {
                    int flag1 = 0;
                    for(int i = 0; i < tariffs.size(); i++)
                        if(id.equals(tariffs.get(i).getTariffID()))
                            flag1 = 1;
                    if(flag1 == 1) {
                        tariff.setTariffID(id);
                        try {
                            Object obj = connect.Connect(tariff, "removeTariff");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tariffs.clear();
                        try {
                            table();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Shake oldPass = new Shake(idField);
                        oldPass.playAnim();
                    }
                } else {
                    Shake oldPass = new Shake(idField);
                    oldPass.playAnim();
                }
            }else{
                Shake oldPass = new Shake(idField);
                oldPass.playAnim();
            }

        });
        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });
    }

    public void table() throws InterruptedException {
        tariffInPut.refresh();
        Connection connect = new Connection();
        Tariff tariff = new Tariff();

        Object obj = connect.Connect(tariff , "tariffList");
        ArrayList<Tariff> tariff1 = (ArrayList<Tariff>) obj;
        Iterator<Tariff> iterator = tariff1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            tariffs.add(new Tariff(tariff1.get(count).getTariffName(),tariff1.get(count).getTariffID(),
                    tariff1.get(count).getTariffInfo(), tariff1.get(count).getTariffPrice(), tariff1.get(count++).getTariffSale()));

            iterator.next();
        }
        tariffInPut.setItems(tariffs);
    }
}
