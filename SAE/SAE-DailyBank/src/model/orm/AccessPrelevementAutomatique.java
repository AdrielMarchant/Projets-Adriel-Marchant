package model.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import model.data.PrelevementAutomatique;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.Order;
import model.orm.exception.Table;

public class AccessPrelevementAutomatique {

    /**
     * Méthode pour récupérer tous les prélèvements automatiques d'un client
     * @param idNumCompte IN : id du compte concerné
     * @return ArrayList<PrelevementAutomatique> : liste des prélèvements automatiques
     * @throws DataAccessException
     * @throws DatabaseConnexionException
     */
    public ArrayList<PrelevementAutomatique> getPrelevementAutomatiques(int idNumCompte) throws DataAccessException, DatabaseConnexionException {
        ArrayList<PrelevementAutomatique> alResult = new ArrayList<>();
        try {
            Connection con = LogToDatabase.getConnexion();
            String query = "SELECT * FROM PrelevementAutomatique WHERE idNumCompte = ? ORDER BY idPrelev";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idNumCompte);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                alResult.add(new PrelevementAutomatique(rs.getInt("idPrelev"), rs.getInt("montant"), rs.getInt("dateRecurrente"), rs.getString("beneficiaire"), rs.getInt("idNumCompte")));
            }
        } catch (SQLException e) {
            throw new DataAccessException(Table.PrelevementAutomatique, Order.SELECT, "Erreur accès", e);
        }
        return alResult;
    }

    /**
     * Méthode pour ajouter un prélèvement automatique
     * @param montant IN : montant du prélèvement
     * @param dateRecurrente IN : date du prélèvement
     * @param beneficiaire IN : nom du bénéficiaire
     * @param idNumCompte IN : id du compte concerné
     * @throws DataAccessException
     * @throws DatabaseConnexionException
     */
    public void addPrelevementAutomatique(double montant, int dateRecurrente, String beneficiaire, int idNumCompte) throws DataAccessException, DatabaseConnexionException {
        try {
            Connection con = LogToDatabase.getConnexion();
            String query = "INSERT INTO PrelevementAutomatique VALUES (seq_id_prelevAuto.NEXTVAL,?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, montant);
            pst.setInt(2, dateRecurrente);
            pst.setString(3, beneficiaire);
            pst.setInt(4, idNumCompte);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(Table.Operation, Order.INSERT, "Erreur accès", e);
        }
    }

    /**
     * Méthode pour supprimer un prélèvement automatique
     * @param idPrelevementAutomatique IN : id du prélèvement à supprimer
     */
    public void deletePrelevementAutomatique(int idPrelevementAutomatique) throws DataAccessException, DatabaseConnexionException {
        try {
            Connection con = LogToDatabase.getConnexion();
            String query = "DELETE FROM PrelevementAutomatique WHERE idPrelev = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idPrelevementAutomatique);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(Table.Operation, Order.DELETE, "Erreur accès", e);
        }
    }

    /**
     * Méthode pour modifier un prélèvement automatique
     * @param idPrelevementAutomatique IN : id du prélèvement à modifier
     */
    public void updatePrelevementAutomatique(int idPrelevementAutomatique, double montant, int dateRecurrente, String beneficiaire, int idNumCompte) throws DataAccessException, DatabaseConnexionException {
        try {
            Connection con = LogToDatabase.getConnexion();
            String query = "UPDATE PrelevementAutomatique SET montant = ?, dateRecurrente = ?, beneficiaire = ?, idNumCompte = ? WHERE idPrelev = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, montant);
            pst.setInt(2, dateRecurrente);
            pst.setString(3, beneficiaire);
            pst.setInt(4, idNumCompte);
            pst.setInt(5, idPrelevementAutomatique);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(Table.Operation, Order.UPDATE, "Erreur accès", e);
        }
    }


}
