package ma.fstt.trackingl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ma.fstt.model.*;
import javafx.scene.control.Label;
import org.controlsfx.control.action.Action;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView<Commande> tableEncours;
    @FXML
    private TableColumn<Commande, Long> idEncours;
    @FXML
    private TableColumn<Commande, Date> dateEncours;
    @FXML
    private TableColumn<Commande, String> livreurEncours;
    @FXML
    private TableColumn<Commande, String> clientEncours;
    @FXML
    private TableColumn<Commande, String> clientLivres;
    @FXML
    private TableView<Commande> tableLivres;
    @FXML
    private TableColumn<Commande, Long> idLivres;
    @FXML
    private TableColumn<Commande, Date> dateLivres;
    @FXML
    private TableColumn<Commande, String> livreurLivres;
    @FXML private Label prdTotale;
    @FXML private Label cmdTotale;
    @FXML private Label lvrTotale;
    @FXML private Label cmdEncoursTtl;
    @FXML private Label cmdLivresTtl;
    private PreparedStatement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;

    @FXML
    void handleButtonActionLvr(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void handleButtonActionPrd(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("produit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @FXML
    void handleButtonActionCmd(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("commande.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getTableEncours();
        getTableActive();
        getTotalePrd();
        getTotaleCmd();
        getTotaleLvr();
        getTotaleCmdLivres();
        getTotaleCmdEncours();
    }

    public ObservableList<Commande> data = FXCollections.observableArrayList();

    public ObservableList<Commande> listCommandeEnCours = FXCollections.observableArrayList();

    public void getTableEncours() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT id,date_D,client,livreur FROM commande WHERE etat='encours';";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()) {
                listCommandeEnCours.add(new Commande(resultSet.getLong(1), resultSet.getDate(2),
                        resultSet.getString(3),resultSet.getString(4)));
            }
            idEncours.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id"));
            dateEncours.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_D"));
            clientEncours.setCellValueFactory(new PropertyValueFactory<Commande, String>("client"));
            livreurEncours.setCellValueFactory(new PropertyValueFactory<Commande, String>("livreur"));
            tableEncours.setItems(listCommandeEnCours);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTableActive() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT id,date_F,client,livreur FROM commande WHERE etat='Active';";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()) {
                data.add(new Commande(resultSet.getLong(1),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDate(2)));
            }
            idLivres.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id"));
            dateLivres.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date_F"));
            clientLivres.setCellValueFactory(new PropertyValueFactory<Commande, String>("client"));
            livreurLivres.setCellValueFactory(new PropertyValueFactory<Commande, String>("livreur"));
            tableLivres.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotalePrd() {
      try {
           Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
           String rqt = "SELECT COUNT(id) FROM produit;";
           PreparedStatement stat = connection.prepareStatement(rqt);
           ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    prdTotale.setText(Integer.toString(count));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public void getTotaleCmd() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT COUNT(id) FROM commande;";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                cmdTotale.setText(Integer.toString(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotaleLvr() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT COUNT(id) FROM livreur;";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                lvrTotale.setText(Integer.toString(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getTotaleCmdLivres() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT COUNT(id) FROM commande WHERE etat='Active';";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                cmdLivresTtl.setText(Integer.toString(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotaleCmdEncours() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/glovo", "root", "");
            String rqt = "SELECT COUNT(id) FROM commande WHERE etat='encours';";
            PreparedStatement stat = connection.prepareStatement(rqt);
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                cmdEncoursTtl.setText(Integer.toString(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}