package ma.fstt.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseDAO<Commande>{
    public CommandeDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Commande object) throws SQLException {

        String request = "INSERT INTO commande (quantite,date_D, date_F, etat, client,produit,livreur) VALUES (?,?, ?, ?, ?, ?, ?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setDouble(1, object.getQuantite());
        this.preparedStatement.setDate(2, new java.sql.Date(object.getDate_D().getTime())); // convert java.util.Date to java.sql.Date
        this.preparedStatement.setDate(3, new java.sql.Date(object.getDate_F().getTime())); // convert java.util.Date to java.sql.Date
        this.preparedStatement.setString(4, object.getEtat());
        this.preparedStatement.setString(5, object.getClient());
        this.preparedStatement.setString(6, object.getProduit());
        this.preparedStatement.setString(7, object.getLivreur());
        this.preparedStatement.executeUpdate(); // use executeUpdate() for INSERT statement
    }

    @Override
    public void update(Commande object) throws SQLException {

        String req = "UPDATE commande SET quantite = ?,date_D = ?,date_F = ?,etat = ?,client = ? WHERE id = ?;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(6, object.getId());
        this.preparedStatement.setDouble(1, object.getQuantite());
        this.preparedStatement.setDate(2, new java.sql.Date(object.getDate_D().getTime())); // convert java.util.Date to java.sql.Date
        this.preparedStatement.setDate(3, new java.sql.Date(object.getDate_F().getTime()));
        this.preparedStatement.setString(4, object.getEtat());
        this.preparedStatement.setString(5, object.getClient());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Commande object) throws SQLException {
        String req = "DELETE FROM commande WHERE id = ? ;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, object.getId());
        this.preparedStatement.execute();
    }

    @Override
    public List<Commande> getAll()  throws SQLException {

        List<Commande> mylist = new ArrayList<Commande>();

        String request = "select * from commande ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
       mylist.add(new Commande(this.resultSet.getLong(1) ,
         this.resultSet.getDouble(2) , this.resultSet.getDate(3),
         this.resultSet.getDate(4), this.resultSet.getString(5), this.resultSet.getString(6)
          ,this.resultSet.getString(7), this.resultSet.getString(8)));

        }
        return mylist;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        return null;
    }
}
