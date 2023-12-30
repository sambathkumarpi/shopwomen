package com.example.shopwomen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShopController implements Initializable {
    DBConnector db;
    ResultSet rs;
//
//    public HelloController() {
//        db=new DBConnector();
//        rs=db.getRs();
//    }
//
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() throws SQLException {
//        rs.next();
//        welcomeText.setText(rs.getString(2));
//    }

    @FXML
    private Label lbCaptial;

    @FXML
    private Label lbCost;

    @FXML
    private Label lbIncome;
    @FXML
    private Button btnAccess;
    @FXML
    private Button btnPurchase;

    @FXML
    private Button btnShoes;
    @FXML
    private Button btnDress;

    @FXML
    private Button btnShoe;

    @FXML
    private Button btndelete;

    @FXML
    private Button btninsert;

    @FXML
    private Button btnupdate;

    @FXML
    private TableView<Products> tvproduct;

    @FXML
    private TableColumn<Products, Integer> colID;

    @FXML
    private TableColumn<Products, String> colSize;

    @FXML
    private TableColumn<Products, String> colName;

    @FXML
    private TableColumn<Products, Integer> colPrice;

    @FXML
    private TableColumn<Products, Integer> colQty;

    private List<Products> productList;
//    @FXML
//    private TextField tfcategory;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfname;

    @FXML
    private TextField tfprice;

    @FXML
    private TextField tfqty;

    @FXML
    private TextField tfsize;

    @FXML
    private ComboBox<String> comCategory;

    @FXML
    private TextField tfprodId;

    @FXML
    private TextField tfprodQty;

    @FXML
    private TextField tfpurchId;

    @FXML
    private TextField tfpurchPrice;

    @FXML
    private TextField tfpurchQty;

    @FXML
    private RadioButton radDiscount;

    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {

        if (event.getSource() == btninsert) {
            insertRecord();
        } else if (event.getSource() == btnupdate) {
            updateRecord();
        } else if (event.getSource() == btndelete) {
            deleteButton();
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showProducts("ALL");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addproductCategorylist();
        lbCaptial.setText("20000.00");
        lbIncome.setText("0.00");
        lbCost.setText("0.00");


    }

    private String[] sizeList = {"Dress", "Shoes", "Accessory"};

    public void addproductCategorylist() {
        List<String> listS = new ArrayList<>();
        for (String data : sizeList) listS.add(data);
        ObservableList listD = FXCollections.observableArrayList(listS);
        comCategory.setItems(listD);

    }

    private void executeQuery(String query) throws SQLException {
        Connection conn = DBConnector.getConnection();
        Statement st;
        System.out.println(query);
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public ObservableList<Products> getProductsList(String category) throws SQLException {
        ObservableList<Products> productList = FXCollections.observableArrayList();
        Connection conn = DBConnector.getConnection();
        String query = "SELECT * FROM product WHERE category = '" + category + "'";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            if(category == "ALL"){
                query = "SELECT * FROM product";
            }
            rs = st.executeQuery(query);

            while (rs.next()) {
                Products product;
                switch (category.toLowerCase()) {
                    case "dress":
                        product = new Dress(
                                rs.getInt("Id"),
                                rs.getString("Name"),
                                rs.getString("size"),
                                rs.getInt("qty"),
                                rs.getString("category"),
                                rs.getInt("price")
                        );
                        break;
                    case "shoes":
                        product = new Shoes(
                                rs.getInt("Id"),
                                rs.getString("Name"),
                                rs.getString("size"),
                                rs.getInt("qty"),
                                rs.getString("category"),
                                rs.getInt("price")
                        );
                        break;
                    case "accessory":
                        product = new Accessories(
                                rs.getInt("Id"),
                                rs.getString("Name"),
                                rs.getString("size"),
                                rs.getInt("qty"),
                                rs.getString("category"),
                                rs.getInt("price")
                        );
                        break;
                    default:
                        product = new Products(
                                rs.getInt("Id"),
                                rs.getString("Name"),
                                rs.getString("size"),
                                rs.getInt("qty"),
                                rs.getString("category"),
                                rs.getInt("price")
                        );
                        break;
                }
                productList.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return productList;
    }

    public void showProducts(String category) throws SQLException {
        System.out.println("Show " + category + " Products");
        ObservableList<Products> productList = getProductsList(category);

        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tvproduct.setItems(productList);
    }

    private Products findProductById(int id) throws SQLException {
        for (Products product : getProductsList("ALL")) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; // Product not found
    }

    public void insertRecord() throws SQLException {
        try {
            int id = Integer.parseInt(tfid.getText());
            String name = tfname.getText();
            String size = tfsize.getText();
            double price = tfprice.getText().isEmpty() ? 0.0: Double.parseDouble(tfprice.getText());
            String category = comCategory.getValue().toString();
            int qty = tfqty.getText().isEmpty() ? 0 : Integer.parseInt(tfqty.getText());

            String query = "INSERT INTO product (id, name, size, price, category, qty) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, size);
                pstmt.setDouble(4, price);
                pstmt.setString(5, category);
                pstmt.setInt(6, qty);

                // Execute the update
                pstmt.executeUpdate();

                // Show updated products
                showProducts("ALL");
                AlertUtils.showInformationAlert("Updated Dialog", "Product id:" + id, "Product added successfully");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error message, log, etc.
            AlertUtils.showWarningAlert("Error", "Invalid input", "Please enter valid numeric values for ID, Price, and Quantity.");
        }
    }


    private void updateRecord() {
        try {
            int productId = Integer.parseInt(tfid.getText());
            Products product = findProductById(productId);

            // Validate the product
            if (product == null) {
                AlertUtils.showWarningAlert("Error", "Invalid Product", "Product not found with ID: " + productId);
                return;
            }

            // Parse input values
            String name = tfname.getText().isEmpty() ? product.getName() : tfname.getText();
            String size = tfsize.getText().isEmpty() ? product.getSize() : tfsize.getText();
            double price = tfprice.getText().isEmpty() ? product.getPrice():Double.parseDouble(tfprice.getText());
            int qty = tfqty.getText().isEmpty() ? product.getQty() : Integer.parseInt(tfqty.getText());

            // Check if the price is negative
            if (price < 0) {
                AlertUtils.showWarningAlert("Error", "Invalid Price", "Price cannot be negative.");
                return;
            }

            // Check if the updated quantity will be negative
            if (product.getQty() - qty < 0) {
                AlertUtils.showWarningAlert("Error", "Invalid Quantity", "Updated quantity cannot be negative.");
                return;
            }

            // Use a prepared statement to avoid SQL injection
            String query = "UPDATE product SET name = ?, size = ?, price = ?, qty = ? WHERE id = ?";
            try (Connection conn = DBConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, name);
                pstmt.setString(2, size);
                pstmt.setDouble(3, price);
                pstmt.setInt(4, qty);
                pstmt.setInt(5, productId);

                // Execute the update
                pstmt.executeUpdate();

                // Show updated products
                showProducts(product.getCategory());
                AlertUtils.showInformationAlert("Updated Dialog", "Product id:" + productId, "Updated Successfully ");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error message, log, etc.
            AlertUtils.showWarningAlert("Error", "Invalid Input", "Please enter valid numeric values for Price and Quantity.");
        }
    }


    private void deleteButton() throws SQLException {
        int productId = Integer.parseInt(tfid.getText());
        Products product = findProductById(productId);
        if (product.getPrice() > 0){
            System.out.println("delete record");
            String query = "DELETE FROM product WHERE id =" + tfid.getText() + "";
            executeQuery(query);
            showProducts(product.getCategory());
            AlertUtils.showInformationAlert("Deleted", "Caution", product.getName() +" Has Deleted Successfully");
        }else {
            AlertUtils.showWarningAlert("Warning", "Caution", "price is less then 0");
        }

    }

    public void sell() throws SQLException {
        System.out.println("sell record");
        int productId = Integer.parseInt(tfprodId.getText());
        Products product = findProductById(productId);

        if (product != null) {
            if (Integer.parseInt(tfprodQty.getText()) > 0 && Integer.parseInt(tfprodQty.getText()) <= product.getQty()) {
                // Update product quantity

                String query = "UPDATE Product SET qty = qty -" + tfprodQty.getText() + " WHERE id = " + tfprodId.getText() + "";
                System.out.println(query);
                executeQuery(query);



                if (radDiscount.isSelected()) {
                    // Calculate discount for product
                    double discountedPrice = getDiscountedPrice(product);
                    double disIncome = Double.parseDouble(tfprodQty.getText()) * discountedPrice;
                    updateFinancialIncome(disIncome);
                }else {
                    // Calculate income
                    double income = Double.parseDouble(tfprodQty.getText()) * product.getPrice();
                    // Update financial metrics
                    updateFinancialIncome(income);
                }

                // Notify observers
                showProducts("ALL");
                AlertUtils.showInformationAlert("Sales Dialog", "sales has made", "Successfully sold : " + product.getName() + " Qty: " + tfprodQty.getText());
            } else {
                AlertUtils.showWarningAlert("Warning", "Caution", "Invalid quantity, available = " + tfprodQty.getText());
            }
        } else {
            AlertUtils.showWarningAlert("Warning", "Caution", "Product not found");
        }

    }

    private static double getDiscountedPrice(Products product) {
        double discount = 0.0;
        switch (product.getCategory().toLowerCase()) {
            case "dress":
                discount = 0.3;
                break;
            case "shoes":
                discount = 0.2;
                break;
            case "accessory":
                discount = 0.5;
                break;
            default:
                break;
        }

        double discountedPrice = product.getPrice() * (1 - discount);
        return discountedPrice;
    }

    public void purchase() throws SQLException {
        System.out.println("purchase record");

        int productId = Integer.parseInt(tfpurchId.getText());
        Products product = findProductById(productId);

        if (product != null) {
            if (Integer.parseInt(tfpurchQty.getText()) > 0 && Double.parseDouble(tfpurchPrice.getText()) <= product.getPrice()) {
                // Update product quantity
                String query = "UPDATE Product SET qty = qty +" + tfpurchQty.getText() + " WHERE id = " + tfpurchId.getText() + "";
                System.out.println(query);
                executeQuery(query);

                // Calculate income
                double cost = Double.parseDouble(tfpurchQty.getText()) * Double.parseDouble(tfpurchPrice.getText());

                // Update financial metrics
                updateFinancialCost(cost);

                // Notify observers
                showProducts("ALL");
                AlertUtils.showInformationAlert("Purchase Dialog", "Purchase has made", "Successfully Buyed : " + product.getName() + " Qty: " + tfpurchQty.getText());
            } else {
                AlertUtils.showWarningAlert("Warning", "Caution", "Invalid quantity or buy price less then : "+ product.getPrice()+"");
            }
        } else {
            AlertUtils.showWarningAlert("Warning", "Caution", "Product not found");
        }

    }

    private void updateFinancialIncome(double income) {
        double Fincome = Double.parseDouble(lbIncome.getText()) + income;
        double capital = Double.parseDouble(lbCaptial.getText());

        // Update capital
        capital = capital + income ;

        // Log or update any other financial metrics as needed
        System.out.println("Income: " + income);
        System.out.println("Capital: " + capital);
        lbCaptial.setText(String.valueOf(capital));
        lbIncome.setText(String.valueOf(Fincome));
    }


    private void updateFinancialCost(double cost) {
        double Fcost = Double.parseDouble(lbCost.getText()) + cost;
        double capital = Double.parseDouble(lbCaptial.getText());

        // Update capital
        capital = capital - cost;

        // Log or update any other financial metrics as needed
        System.out.println("Capital: " + capital);
        lbCost.setText(String.valueOf(Fcost));
        lbCaptial.setText(String.valueOf(capital));
    }


    public void btnShowDressClicked() {
        try {
            showProducts("dress");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public void btnShowshoesClicked() {
        try {
            showProducts("shoes");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public void btnShowAccesseryClicked() {
        try {
            showProducts("accessory");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

}
