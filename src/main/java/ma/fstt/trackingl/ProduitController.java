package ma.fstt.trackingl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    @FXML
    private TextField nom ;

    @FXML
    private TextField prix;

    @FXML
    private TableView<Produit> mytablePrd ;

    @FXML
    private TableColumn<Produit ,Long> col_id ;

    @FXML
    private TableColumn<Produit, String> col_nom ;

    @FXML
    private TableColumn <Produit ,Double> col_prix ;

    @FXML
    protected void onSaveButtonClick() {
        if (fieldsAreNotEmpty()) {
            if (nom.getText() != null && prix.getText() != null) {
                try {
                    ProduitDAO produitDAO = new ProduitDAO();
                    Produit produit = new Produit(0l, nom.getText(), Double.parseDouble(prix.getText()));
                    produitDAO.save(produit);
                    UpdateTable();
                    nom.setText("");
                    prix.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean fieldsAreNotEmpty() {
        return !nom.getText().isEmpty() && !prix.getText().isEmpty();
    }
    @FXML
    protected void onDeleteButtonClick() {
        if (nom.getText() != null && prix.getText() != null) {
            try {
                ProduitDAO produitDAO = new ProduitDAO();
                Produit produit = mytablePrd.getSelectionModel().getSelectedItem();
                produitDAO.delete(produit);
                UpdateTable();
                nom.setText("");
                prix.setText("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    protected void onUpdateButtonClick() {
        if (fieldsAreNotEmpty()) {
        Produit selectedProduit = mytablePrd.getSelectionModel().getSelectedItem();
            if (nom.getText() != null && prix.getText() != null) {
                try {
                    ProduitDAO produitDAO = new ProduitDAO();
                    selectedProduit.setNom(nom.getText());
                    selectedProduit.setPrix(Double.parseDouble(prix.getText()));
                    produitDAO.update(selectedProduit);
                    UpdateTable();
                    nom.setText("");
                    prix.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
        public void UpdateTable(){
            col_id.setCellValueFactory(new PropertyValueFactory<Produit,Long>("id_produit"));
            col_nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nom"));
            col_prix.setCellValueFactory(new PropertyValueFactory<Produit,Double>("prix"));
            mytablePrd.setItems(this.getDataProduits());
        }
    @FXML
    public static ObservableList<Produit> getDataProduits(){
        ProduitDAO produitDAO = null;
        ObservableList<Produit> listfx = FXCollections.observableArrayList();
        try {
            produitDAO = new ProduitDAO();
            for (Produit ettemp : produitDAO.getAll())
                listfx.add(ettemp);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        mytablePrd.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Produit selectedProduit;
                selectedProduit = mytablePrd.getSelectionModel().getSelectedItem();
                nom.setText(selectedProduit.getNom());
                prix.setText(selectedProduit.getPrix().toString());
            }
        });
    }
}