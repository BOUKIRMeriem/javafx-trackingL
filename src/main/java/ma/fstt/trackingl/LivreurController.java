package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LivreurController implements Initializable {


    @FXML
    private TextField nom ;


    @FXML
    private TextField tele ;


    @FXML
    private TableView<Livreur> mytable ;


    @FXML
    private TableColumn<Livreur ,Long> col_id ;

    @FXML
    private TableColumn <Livreur ,String> col_nom ;

    @FXML
    private TableColumn <Livreur ,String> col_tele ;


    @FXML
    protected void onSaveButtonClick() {
        if (fieldsAreNotEmpty()) {
            if (nom.getText() != null && tele.getText() != null) {
                try {
                    LivreurDAO livreurDAO = new LivreurDAO();
                     Livreur liv = new Livreur(0l, nom.getText(), tele.getText());
                    livreurDAO.save(liv);
                    UpdateTable();
                    nom.setText("");
                    tele.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean fieldsAreNotEmpty() {
        return !nom.getText().isEmpty() && !tele.getText().isEmpty();
    }
    @FXML
    protected void onDeleteButtonClick() {
        if (nom.getText() != null && tele.getText() != null) {
            try {
                LivreurDAO livreurDAO = new LivreurDAO();
                Livreur liv = mytable.getSelectionModel().getSelectedItem();
                livreurDAO.delete(liv);
                UpdateTable();
                nom.setText("");
                tele.setText("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    protected void onUpdateButtonClick() {
        if (fieldsAreNotEmpty()) {
        Livreur selectedLivreur = mytable.getSelectionModel().getSelectedItem();
                if (nom.getText() != null && tele.getText() != null) {
                try {
                    LivreurDAO livreurDAO = new LivreurDAO();
                    selectedLivreur.setNom(nom.getText());
                    selectedLivreur.setTelephone(tele.getText());
                    livreurDAO.update(selectedLivreur);
                    UpdateTable();
                    nom.setText("");
                    tele.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Livreur,Long>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("nom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Livreur,String>("telephone"));
        mytable.setItems(this.getDataLivreurs());
    }

    public static ObservableList<Livreur> getDataLivreurs(){
        LivreurDAO livreurDAO = null;
        ObservableList<Livreur> listfx = FXCollections.observableArrayList();
          try {
            livreurDAO = new LivreurDAO();
            for (Livreur ettemp : livreurDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
          return listfx ;
    }

       @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        mytable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Livreur selectedLivreur;
                selectedLivreur = mytable.getSelectionModel().getSelectedItem();
                nom.setText(selectedLivreur.getNom());
                tele.setText(selectedLivreur.getTelephone());
            }
        });
    }
}