package Controller;

import Database.DBCustomers;
import Model.CollectionManagement;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**This class controls the add customer menu.*/
public class AddCustomerController implements Initializable {

    /**This variable creates the stage for the GUI of the add customer menu.*/
    Stage stage;

    /**This variable creates the scene for the GUI of the add customer menu.*/
    Parent scene;


    /**This FXML variable handles the address text field.*/
    @FXML
    private TextField customerAddressTxt;

    /**This FXML variable handles the country combo box.*/
    @FXML
    private ComboBox<String> customerCountryComboBox;

    /**This FXML variable handles the division combo box*/
    @FXML
    private ComboBox<String> customerDivisionComboBox;

    /**This FXML variable handles the id text field*/
    @FXML
    private TextField customerIdTxt;

    /**This FXML variable handles the name text field*/
    @FXML
    private TextField customerNameTxt;

    /**This FXML variable handles the phone text field*/
    @FXML
    private TextField customerPhoneTxt;

    /**This FXML variable handles the postal code text field*/
    @FXML
    private TextField customerPostalCodeTxt;






    /**This FXML method allows the cancel button to send the user back to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    /**This FXML method saves the new customer to the database.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {

        String name = customerNameTxt.getText();
        String phone = customerPhoneTxt.getText();
        String address = customerAddressTxt.getText();
        String division = customerDivisionComboBox.getSelectionModel().getSelectedItem();
        String country = customerCountryComboBox.getSelectionModel().getSelectedItem();
        String postalCode = customerPostalCodeTxt.getText();

        Customer customer = new Customer(1, name, phone, address, division, country, postalCode);
        DBCustomers.insert(customer);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**This FXML method filters the division based on the selected country.
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionFilterDivisions(ActionEvent event) throws SQLException {
        String name = customerCountryComboBox.getSelectionModel().getSelectedItem();
        CollectionManagement.filterDivisions(name);
        customerDivisionComboBox.setItems(CollectionManagement.getFilteredDivisions());
    }





    /**This method initializes the add customer menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIdTxt.setText("Auto Gen- disabled");
        customerIdTxt.setDisable(true);

        customerCountryComboBox.setItems(CollectionManagement.getAllCountries());
        try {
            CollectionManagement.refreshAllDivisions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerDivisionComboBox.setItems(CollectionManagement.getAllDivisions());


    }

}
