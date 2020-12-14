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

public class DeleteProductController {

    private ObservableList<Product> products = FXCollections.observableArrayList();


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
    private TableView<Product> productInPut;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product,String> productPrice;

    @FXML
    private TableColumn<Product,String> productID;

    @FXML
    private TableColumn<Product,String> productInfo;

    @FXML
    void initialize() throws InterruptedException {

        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("productCost"));
        productID.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        productInfo.setCellValueFactory(new PropertyValueFactory<Product, String>("productInfo"));

        table();

        deleteButton.setOnAction(actionEvent -> {
            Connection connect = new Connection();
            Product product = new Product();
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
                    for(int i = 0; i < products.size(); i++)
                        if(id.equals(products.get(i).getProductID()))
                            flag1 = 1;
                    if(flag1 == 1) {
                        product.setProductID(id);
                        try {
                            Object obj = connect.Connect(product, "removeProduct");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        products.clear();
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
        productInPut.refresh();
        Connection connect = new Connection();
        Product product = new Product();

        Object obj = connect.Connect(product , "allProducts");
        ArrayList<Product> product1 = (ArrayList<Product>) obj;
        Iterator<Product> iterator = product1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            products.add(new Product(product1.get(count).getProductName(),product1.get(count).getProductCost(),
                    product1.get(count).getProductID(), product1.get(count++).getProductInfo()));
            iterator.next();
        }
        productInPut.setItems(products);
    }
}
