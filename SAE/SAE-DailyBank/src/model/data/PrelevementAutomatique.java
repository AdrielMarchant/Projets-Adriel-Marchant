package model.data;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PrelevementAutomatique {
    public int idPrelevementAutomatique;
    public double montant;
    public int datePrelevement;
    public String beneficiaire;
    public int idNumCompte;


    public PrelevementAutomatique(int idPrelevementAutomatique, double montant, int datePrelevement, String beneficiaire, int idNumCompte) {
        super();
        this.idPrelevementAutomatique = idPrelevementAutomatique;
        this.montant = montant;
        this.datePrelevement = datePrelevement;
        this.beneficiaire = beneficiaire;
        this.idNumCompte = idNumCompte;
    }

    public PrelevementAutomatique(PrelevementAutomatique p) {
        this(p.idPrelevementAutomatique, p.montant, p.datePrelevement, p.beneficiaire, p.idNumCompte);
    }

    public PrelevementAutomatique() {
        super();
        this.idPrelevementAutomatique = 0;
        this.montant = 0;
        this.datePrelevement = 0;
        this.beneficiaire = "";
        this.idNumCompte = 0;
    }


    @Override
    public String toString() {
        return "["+this.idPrelevementAutomatique +"] "+ beneficiaire + ": montant=" + this.montant + ", dateRecurrence=" + this.datePrelevement;
    }

}
