package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit>{
    public ProduitDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Produit object) throws SQLException {
          String request = "insert into produit (nom , prix_unitaire) values (? , ?)";
          this.preparedStatement = this.connection.prepareStatement(request);
          this.preparedStatement.setString(1 , object.getNom());
          this.preparedStatement.setDouble(2 , object.getPrix());
          this.preparedStatement.execute();
    }

    @Override
    public void update(Produit object) throws SQLException {

        String req = "UPDATE produit SET nom = ?, prix_unitaire = ? WHERE id = ?;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1, object.getNom());
        this.preparedStatement.setDouble(2, object.getPrix());
        this.preparedStatement.setLong(3, object.getId_produit());
        this.preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Produit object) throws SQLException {
        String req = "DELETE FROM produit WHERE id = ? ;";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setLong(1, object.getId_produit());
        this.preparedStatement.execute();
    }


    @Override
    public List<Produit> getAll()  throws SQLException {

        List<Produit> mylist = new ArrayList<Produit>();

        String request = "select * from produit; ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Produit(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) , this.resultSet.getDouble(3)));

        }

        return mylist;
    }

    @Override
    public Produit getOne(Long id) throws SQLException {
        return null;
    }
}
