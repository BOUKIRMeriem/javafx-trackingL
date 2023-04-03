package ma.fstt.trackingl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.fstt.model.*;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Arrays;
import java.time.ZoneId;

public class CommandeController implements Initializable {
    @FXML
    private TextField quantite;

    @FXML
    private TextField client;
    @FXML
    private ComboBox<String> sltLivreur;
    @FXML
    private ComboBox<String> stlProduit;
    @FXML
    private ComboBox<String> sltEtat;
    @FXML
    private DatePicker  sltDateD;
    @FXML
    private DatePicker sltDateF;
    @FXML
    private TableView<Commande> mytableCmd;
    @FXML
    private TableColumn<Commande,Long> col_id;
    @FXML
    private TableColumn<Commande, Double> col_qte;
    @FXML
    private TableColumn <Commande , Date> col_dateD;
    @FXML
    private TableColumn <Commande ,Date> col_dateF;
    @FXML
    private TableColumn <Commande ,String> col_etat;
    @FXML
    private TableColumn <Commande ,String> col_client;

    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    protected void onSaveButtonClick() {
        if (fieldsAreNotEmpty()) {
            if (quantite.getText() != null &&!sltEtat.getValue().isEmpty() && client.getText() != null  &&sltDateD.getValue()!= null &&
            sltDateF.getValue()!= null && sltEtat.getValue() != null ) {
                try {
                    CommandeDAO commandeDAO = new CommandeDAO();
                    Commande commande = new Commande(0l, Double.parseDouble(quantite.getText()),
                           Date.from(sltDateD.getValue().atStartOfDay(defaultZoneId).toInstant()),
                            Date.from(sltDateF.getValue().atStartOfDay(defaultZoneId).toInstant()),
                            sltEtat.getValue(), client.getText(),stlProduit.getValue(),sltLivreur.getValue());
                    commandeDAO.save(commande);
                    UpdateTable();
                    quantite.setText("");
                    sltEtat.setValue("");
                    client.setText("");
                    sltLivreur.setValue("");
                    stlProduit.setValue("");
                    sltDateF.setValue(LocalDate.parse(""));
                    sltDateD.setValue(LocalDate.parse(""));

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean fieldsAreNotEmpty() {
        return !quantite.getText().isEmpty() &&!sltEtat.getValue().isEmpty() && !client.getText().isEmpty() && sltDateD.getValue()!= null &&
        sltDateF.getValue()!= null && sltEtat.getValue() != null ;
    }
    @FXML
    protected void onDeleteButtonClick() {
        if (quantite.getText() != null && sltDateD.getValue()!= null &&
                sltDateF.getValue() != null && sltEtat.getValue() != null && client.getText() != null){
            try {
                CommandeDAO commandeDAO = new CommandeDAO();
                Commande commande = mytableCmd.getSelectionModel().getSelectedItem();
                commandeDAO.delete(commande);
                UpdateTable();
                quantite.setText("");
                sltEtat.setValue("");
                client.setText("");
                sltLivreur.setValue("");
                stlProduit.setValue("");
                sltDateF.setValue(LocalDate.parse(""));
                sltDateD.setValue(LocalDate.parse(""));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    protected void onUpdateButtonClick() {
        if (fieldsAreNotEmpty()) {
        Commande selectedCommande = mytableCmd.getSelectionModel().getSelectedItem();
            if (quantite.getText() != null &&  sltDateD.getValue() != null &&
                    sltDateF.getValue()!= null && sltEtat.getValue() != null && client.getText() != null) {
                try {
                    CommandeDAO commandeDAO = new CommandeDAO();
                    selectedCommande.setQuantite(Double.parseDouble(quantite.getText()));
                    selectedCommande.setDate_D( Date.from(sltDateD.getValue().atStartOfDay(defaultZoneId).toInstant()));
                    selectedCommande.setDate_F( Date.from(sltDateF.getValue().atStartOfDay(defaultZoneId).toInstant()));
                    selectedCommande.setEtat(sltEtat.getValue());
                    selectedCommande.setClient(client.getText());
                    selectedCommande.setProduit(stlProduit.getValue());
                    selectedCommande.setLivreur(sltLivreur.getValue());
                    commandeDAO.update(selectedCommande);
                    UpdateTable();
                    quantite.setText("");
                    sltEtat.setValue("");
                    client.setText("");
                    sltLivreur.setValue("");
                    stlProduit.setValue("");
                    sltDateF.setValue(LocalDate.parse(""));
                    sltDateD.setValue(LocalDate.parse(""));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
        public void UpdateTable(){
            col_id.setCellValueFactory(new PropertyValueFactory<Commande,Long>("id"));
            col_qte.setCellValueFactory(new PropertyValueFactory<Commande,Double>("quantite"));
            col_dateD.setCellValueFactory(new PropertyValueFactory<Commande,Date>("date_D"));
            col_dateF.setCellValueFactory(new PropertyValueFactory<Commande,Date>("date_F"));
            col_etat.setCellValueFactory(new PropertyValueFactory<Commande,String>("etat"));
            col_client.setCellValueFactory(new PropertyValueFactory<Commande,String>("client"));
            mytableCmd.setItems(this.getDataCommandes());
        }
    @FXML
    public static ObservableList<Commande> getDataCommandes(){
        CommandeDAO commandeDAO = null;
        ObservableList<Commande> listfx = FXCollections.observableArrayList();
        try {
            commandeDAO = new CommandeDAO();
            for (Commande ettemp : commandeDAO.getAll())
                listfx.add(ettemp);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx ;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        getLivreur();
        getProduit();
        remplirEtat();
        mytableCmd.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Commande selectedCommande;
                selectedCommande = mytableCmd.getSelectionModel().getSelectedItem();
                quantite.setText(selectedCommande.getQuantite().toString());
                sltDateD.setValue(LocalDate.parse(selectedCommande.getDate_D().toString()));
                sltDateF.setValue(LocalDate.parse(selectedCommande.getDate_F().toString()));
                sltEtat.setValue(selectedCommande.getEtat());
                client.setText(selectedCommande.getClient());
                sltLivreur.setValue(selectedCommande.getLivreur());
                stlProduit.setValue(selectedCommande.getProduit());
            }
        });
    }

    public void getLivreur() {
        LivreurDAO livreurDAO = null;
        ObservableList<String> listfx = FXCollections.observableArrayList();
        try {
            livreurDAO = new LivreurDAO();
            for (Livreur ettemp : livreurDAO.getAll())
                listfx.add(ettemp.getNom());

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sltLivreur.setItems(listfx);
    }

    public void getProduit() {
        ProduitDAO produitDAO = null;
        ObservableList<String> listfx = FXCollections.observableArrayList();
        try {
            produitDAO = new ProduitDAO();
            for (Produit ettemp : produitDAO.getAll())
                listfx.add(ettemp.getNom());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stlProduit.setItems(listfx);
    }

    public void remplirEtat() {
        List<String> options = Arrays.asList("terminé", "encours");
        sltEtat.setItems(FXCollections.observableList(options));
 }



}

