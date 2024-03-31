package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends VBox {


    IJeu jeu;

    ArrayList<Label> nomJoueur;
    ArrayList<Label> pionsB;
    ArrayList<Label> pionsW;

    ArrayList<VBox> boxJoueurs;

    ArrayList<Label> scoreJoueurs;

    public VueAutresJoueurs(IJeu jeu){
        this.jeu = jeu;
        nomJoueur = new ArrayList<>();
        pionsB = new ArrayList<>();
        pionsW = new ArrayList<>();
        scoreJoueurs = new ArrayList<>();
        boxJoueurs = new ArrayList<>();

        for (IJoueur j: jeu.getJoueurs()){
            boxJoueurs.add(new VBox());
            nomJoueur.add(new Label(j.getNom()));
            pionsB.add(new Label(String.valueOf(j.getNbPionsBateau())));
            pionsW.add(new Label(String.valueOf(j.getNbPionsWagon())));
            scoreJoueurs.add(new Label(String.valueOf(j.getScore())));
            boxJoueurs.get(boxJoueurs.size()-1).getChildren().addAll(nomJoueur.get(nomJoueur.size()-1), pionsB.get(pionsB.size()-1), pionsW.get(pionsW.size()-1), scoreJoueurs.get(scoreJoueurs.size()-1));
            getChildren().add(boxJoueurs.get(boxJoueurs.size()-1));

            setSpacing(10);

        }


    }
    ChangeListener<IJoueur> joueurChangez = (observableValue, ancienJ, jc) -> {
        int count = 0;
        for(IJoueur j : jeu.getJoueurs()){
            nomJoueur.get(count).setText(j.getNom());
            pionsB.get(count).setText("Pions bateaux : "+j.getNbPionsBateau()+"");
            pionsW.get(count).setText("Pions wagons : "+j.getNbPionsWagon()+"");
            scoreJoueurs.get(count).setText("Score: " +j.getScore()+"");
            count++;
        }

    };


    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(joueurChangez);
    }
}