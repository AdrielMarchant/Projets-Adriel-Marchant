package model.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.data.Client;
import model.data.Employe;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.Order;
import model.orm.exception.RowNotFoundOrTooManyRowsException;
import model.orm.exception.Table;

public class AccessEmploye {

	public AccessEmploye() {
	}

	/**
	 * Recherche d'un employe par son login / mot de passe.
	 *
	 * @param login    login de l'employé recherché
	 * @param password mot de passe donné
	 * @return un Employe ou null si non trouvé
	 * @throws RowNotFoundOrTooManyRowsException
	 * @throws DataAccessException
	 * @throws DatabaseConnexionException
	 */
	public Employe getEmploye(String login, String password)
			throws RowNotFoundOrTooManyRowsException, DataAccessException, DatabaseConnexionException {
		Employe employeTrouve;

		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "SELECT * FROM Employe WHERE" + " login = ?" + " AND motPasse = ?";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, login);
			pst.setString(2, password);

			ResultSet rs = pst.executeQuery();

			System.err.println(query);

			if (rs.next()) {
				int idEmployeTrouve = rs.getInt("idEmploye");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String droitsAccess = rs.getString("droitsAccess");
				String loginTROUVE = rs.getString("login");
				String motPasseTROUVE = rs.getString("motPasse");
				int idAgEmploye = rs.getInt("idAg");

				employeTrouve = new Employe(idEmployeTrouve, nom, prenom, droitsAccess, loginTROUVE, motPasseTROUVE,
						idAgEmploye);
			} else {
				rs.close();
				pst.close();
				// Non trouvé
				return null;
			}

			if (rs.next()) {
				// Trouvé plus de 1 ... bizarre ...
				rs.close();
				pst.close();
				throw new RowNotFoundOrTooManyRowsException(Table.Employe, Order.SELECT,
						"Recherche anormale (en trouve au moins 2)", null, 2);
			}
			rs.close();
			pst.close();
			return employeTrouve;
		} catch (SQLException e) {
			throw new DataAccessException(Table.Employe, Order.SELECT, "Erreur accès", e);
		}

	}

	/**
	 * Récupère tous les employés existants dans l'agence.
	 * @param idAg id de l'agence
	 * @param idEmploye id de l'employé qui fait la requête
	 * @param debutNom nom de l'employé à partir duquel on recherche
	 * @param debutPrenom prenom de l'employé à partir duquel on recherche
	 * @return une liste d'employés
	 * @throws DataAccessException
	 * @throws DatabaseConnexionException
	 */
	public ArrayList<Employe> getEmployes(int idAg, int idEmploye, String debutNom, String debutPrenom)
					throws DataAccessException, DatabaseConnexionException {
			ArrayList<Employe> alResult = new ArrayList<>();

			try {
				Connection con = LogToDatabase.getConnexion();

				PreparedStatement pst;

				String query;
				if (idEmploye != -1) {
					query = "SELECT * FROM Employe where idAg = ?";
					query += " AND idNumCli = ?";
					query += " ORDER BY nom";
					pst = con.prepareStatement(query);
					pst.setInt(1, idAg);
					pst.setInt(2, idEmploye);

				} else if (!debutNom.equals("")) {
					debutNom = debutNom.toUpperCase() + "%";
					debutPrenom = debutPrenom.toUpperCase() + "%";
					query = "SELECT * FROM Employe where idAg = ?";
					query += " AND UPPER(nom) like ?" + " AND UPPER(prenom) like ?";
					query += " ORDER BY nom";
					pst = con.prepareStatement(query);
					pst.setInt(1, idAg);
					pst.setString(2, debutNom);
					pst.setString(3, debutPrenom);
				} else {
					query = "SELECT * FROM Employe where idAg = ?";
					query += " ORDER BY nom";
					pst = con.prepareStatement(query);
					pst.setInt(1, idAg);
				}
				System.err.println(query + " nom : " + debutNom + " prenom : " + debutPrenom + "#");

				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					int idEmployeQ = rs.getInt("idEmploye");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String droitsAccess = rs.getString("droitsAccess");
					droitsAccess = (droitsAccess == null ? "" : droitsAccess);
					String login = rs.getString("login");
					login = (login == null ? "" : login);
					String motpasse = rs.getString("motpasse");
					motpasse = (motpasse == null ? "" : motpasse);
					int idAgCli = rs.getInt("idAg");

					alResult.add(
							new Employe(idEmployeQ, nom, prenom, droitsAccess, login, motpasse, idAgCli));
				}
				rs.close();
				pst.close();
			} catch (SQLException e) {
				throw new DataAccessException(Table.Employe, Order.SELECT, "Erreur accès", e);
			}

			return alResult;
	}

	/**
	 * Ajoute un employé dans la base de données.
	 *
	 * @param employe
	 * @throws DataAccessException
	 * @throws DatabaseConnexionException
	 */
	public void updateEmploye(Employe employe) throws DataAccessException, DatabaseConnexionException, SQLException {
		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "UPDATE Employe SET" + " nom = ?" + " , prenom = ?" + " , droitsAccess = ?" + " , login = ?"
					+ " , motPasse = ?" + " , idAg = ?" + " WHERE idEmploye = ?";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, employe.nom);
			pst.setString(2, employe.prenom);
			pst.setString(3, employe.droitsAccess);
			pst.setString(4, employe.login);
			pst.setString(5, employe.motPasse);
			pst.setInt(6, employe.idAg);
			pst.setInt(7, employe.idEmploye);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new DataAccessException(Table.Employe, Order.UPDATE, "Erreur accès", e);
		}
	}

	/**
	 * Rajoute un employé dans la base de données.
	 * @param employe employé à rajouter
	 * @throws DataAccessException
	 */
	public void insertEmploye(Employe employe) throws DataAccessException {
		//INSERT INTO Employe VALUES (seq_id_employe.NEXTVAL,'?','?','?','?','?',?);
		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "INSERT INTO Employe VALUES (seq_id_employe.NEXTVAL,?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, employe.nom);
			pst.setString(2, employe.prenom);
			pst.setString(3, employe.droitsAccess);
			pst.setString(4, employe.login);
			pst.setString(5, employe.motPasse);
			pst.setInt(6, employe.idAg);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new DataAccessException(Table.Employe, Order.INSERT, "Erreur accès", e);

		} catch (DatabaseConnexionException e) {
			throw new DataAccessException(Table.Employe, Order.INSERT, "Erreur accès", e);
		}
	}

	/**
	 * Supprime un employé dans la base de données.
	 * @param emplDel employé à supprimer
	 */
	public void deleteEmploye(Employe emplDel) throws DataAccessException {
		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "DELETE FROM Employe WHERE idEmploye = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, emplDel.idEmploye);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new DataAccessException(Table.Employe, Order.DELETE, "Erreur accès", e);
		} catch (DatabaseConnexionException e) {
			throw new DataAccessException(Table.Employe, Order.DELETE, "Erreur accès", e);
		}
	}

	/**
	 * Teste si un nom d'utilisateur existe déjà dans la base de données pour tout autre employé que celui en rentré
	 * @param username nom d'utilisateur à tester
	 * @param id id de l'employé à tester
	 * @return true si le nom d'utilisateur existe déjà, false sinon
	 * @throws DataAccessException
	 */
	public boolean isUsernameTaken(String username, int id) throws DataAccessException {
		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "SELECT COUNT(*) FROM Employe WHERE login = ? AND idEmploye != ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setInt(2, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				rs.close();
				pst.close();
				return true;
			}
			rs.close();
			pst.close();
			return false;
		} catch (SQLException e) {
			throw new DataAccessException(Table.Employe, Order.SELECT, "Erreur accès", e);
		} catch (DatabaseConnexionException e) {
			throw new DataAccessException(Table.Employe, Order.SELECT, "Erreur accès", e);
		}
	}
}
