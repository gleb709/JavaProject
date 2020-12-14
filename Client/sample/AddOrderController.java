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

public class AddOrderController {

    private ObservableList<OrderInfo> order = FXCollections.observableArrayList();
    private ObservableList<Product> products = FXCollections.observableArrayList();


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
    private Button animalButtom;

    @FXML
    private Button foodButton;

    @FXML
    private Button trashButton;

    @FXML
    private Button createOrderButton;

    @FXML
    private TableView<OrderInfo> productsSelected;

    @FXML
    private TableColumn<OrderInfo, String> productName1;

    @FXML
    private TableColumn<OrderInfo, String> productPrice1;

    @FXML
    private TableColumn<OrderInfo, String> prodactNumber;

    @FXML
    private TableColumn<OrderInfo, String> productID1;

    @FXML
    private TableColumn<OrderInfo, String> productInfo1;

    @FXML
    private TableColumn<OrderInfo, String> productsCost;

    @FXML
    private TextField addProductId;

    @FXML
    private TextField addProductCount;

    @FXML
    private Button addProductButton;

    @FXML
    private TextField deleteProductId;

    @FXML
    private Button deleteProductbutton;

    @FXML
    private TextArea text;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, String> productPrice;

    @FXML
    private TableColumn<Product, String> productID;

    @FXML
    private TableColumn<Product, String> productInfo;

    @FXML
    void initialize() {
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("productCost"));
        productID.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        productInfo.setCellValueFactory(new PropertyValueFactory<Product, String>("productInfo"));
        productID1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productID"));
        productPrice1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productCost"));
        productName1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productName"));
        productInfo1.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("productInfo"));
        productsCost.setCellValueFactory(new PropertyValueFactory<OrderInfo,String>("price"));
        prodactNumber.setCellValueFactory(new PropertyValueFactory<OrderInfo, String>("count"));

        stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });

        animalButtom.setOnAction(actionEvent -> {
            productsTable.getItems().clear();
            try {
                createTable("router");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        foodButton.setOnAction(actionEvent -> {
            productsTable.getItems().clear();
            try {
                createTable("link");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        trashButton.setOnAction(actionEvent -> {
            productsTable.getItems().clear();
            try {
                createTable("trash");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        deleteProductbutton.setOnAction(actionEvent -> {
            String str1 = deleteProductId.getText();
            remProduct(str1);
        });

        addProductButton.setOnAction(actionEvent -> {
            String str1 = addProductCount.getText();
            String str2 = addProductId.getText();
            try {
                addProduct(str1, str2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        createOrderButton.setOnAction((actionEvent -> {
            try {
                createOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

    }

    private void createOrder() throws InterruptedException {
        OrderInfo info = new OrderInfo();
        Connection connect = new Connection();

        text.clear();

        int flag = 0;

        int moneyNeed = 0;

        if(order.size() != 0) {
            ArrayList<OrderInfo> ord = new ArrayList<OrderInfo>();
            for (int i = 0; i < order.size(); i++) {
                OrderInfo obj = new OrderInfo();
                obj.setProductName(order.get(i).getProductName());
                obj.setProductID(order.get(i).getProductID());
                obj.setProductCost(order.get(i).getProductCost());
                obj.setProductInfo(order.get(i).getProductInfo());
                obj.setCount(order.get(i).getCount());
                obj.setPrice(order.get(i).getPrice());
                moneyNeed += Integer.parseInt(obj.getPrice());
                ord.add(obj);
            }

            Connection connect1 = new Connection();
            User user1 = new User();

            user1.setUserLogin(getUserName());
            User obj1 = (User) connect1.Connect(user1, "money");
            Object obj = new Object();

            if(Integer.parseInt(obj1.getUserMoney()) > moneyNeed){
                obj = connect.Connect(ord, getUserName());
                User user2 = new User();
                user2.setUserMoney(Integer.toString(moneyNeed));
                System.out.println(user2.getUserMoney());
                user2.setUserLogin(getUserName());
                Object obj2 = null;
                try {
                    obj2 = connect.Connect(user2, "delMoney");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Заказ отправлен");
            }
            else {
                Shake button = new Shake(createOrderButton);
                button.playAnim();
                text.setText("У вас недостаточно средств!");
            }
        }else{
            Shake button = new Shake(createOrderButton);
            button.playAnim();
        }
    }

    private  void remProduct(String id){
        int flag = 0;
        int key = 0;

        for (int i = 0; i < order.size();i++){
            System.out.println(id);
            if(id.equals(order.get(i).getProductID())){
                flag = 1;
                key = i;
            }
        }
        if(flag == 1){
            order.remove(key);
            productsSelected.refresh();
        }
        else{
            Shake userLoginAnim = new Shake(deleteProductbutton);
            userLoginAnim.playAnim();
        }
    }

    private void addProduct(String count,String id) throws InterruptedException {
        OrderInfo info = new OrderInfo();
        Connection connect = new Connection();

        int flag = 0;
        int key = 0;

        for (int i = 0; i < order.size();i++){
            if(id.equals(order.get(i).getProductID())){
                flag = 1;
                key = i;
            }
        }

        int flag1 = 0;
        try {
            Integer.parseInt(count);
            Integer.parseInt(id);
        } catch(Exception e) {
            flag1 = 1;
        }

        if(flag1 == 0) {
            info.setCount(count);
            info.setProductID(id);

            OrderInfo obj = (OrderInfo) connect.Connect(info, "add");

            if (flag == 0 && obj.getProductName() != null) {

                obj.setProductID(id);
                obj.setCount(count);

                obj.setPrice(Integer.toString(parseInt(count) * parseInt(obj.getProductCost())));

                order.add(obj);
                productsSelected.setItems(order);
            } else if (flag == 1) {
                order.get(key).setCount(Integer.toString(parseInt(order.get(key).getCount()) + parseInt(count)));
                order.get(key).setPrice(Integer.toString(parseInt(order.get(key).getPrice()) + parseInt(count) * parseInt(order.get(key).getProductCost())));
                System.out.println(order.size());

                productsSelected.refresh();
            } else {
                Shake userLoginAnim = new Shake(addProductButton);
                userLoginAnim.playAnim();
            }
        }
        else{
            Shake button = new Shake(addProductButton);
            button.playAnim();
        }
    }

    public void createTable(String string) throws InterruptedException {
        Connection connect = new Connection();
        Product product = new Product();

        product.setProductName(string);

        Object obj = connect.Connect(product ,string);
        System.out.println(obj);
        ArrayList<Product> product1 = (ArrayList<Product>) obj;
        Iterator<Product> iterator = product1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            products.add(new Product(product1.get(count).getProductName(),product1.get(count).getProductCost(),
                    product1.get(count).getProductID(), product1.get(count++).getProductInfo()));
            iterator.next();
        }
        productsTable.setItems(products);
    }
}
