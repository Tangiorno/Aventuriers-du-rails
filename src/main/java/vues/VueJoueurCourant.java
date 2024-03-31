package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class    VueJoueurCourant extends VBox {
    private Label nomJoueur;

    private Label pionsW, pionsB;


    private VueCarteTransport cT;

    private HBox boxCartes;

    private ScrollPane scroll;

    private HBox boxDestinations;




    public VueJoueurCourant(){
        nomJoueur = new Label();
        boxDestinations = new HBox();
        pionsW = new Label();
        pionsB = new Label();
        boxCartes = new HBox();
        scroll = new ScrollPane(boxCartes);
        scroll.setMinHeight(115);
        getChildren().addAll(nomJoueur, pionsB, pionsW, scroll, boxDestinations);
    }


    private final EventHandler<MouseEvent> choisirCarte = new EventHandler<>() {
        @Override
        public void handle(MouseEvent actionEvent) {
            System.out.println("rentre");
            ((VueDuJeu)getScene().getRoot()).getJeu().uneCarteDuJoueurEstJouee(((VueCarteTransport)actionEvent.getSource()).getCarte());
        }
    };



    ChangeListener<IJoueur> joueurChange = (observableValue, ancienJ, jc) -> {
        boxCartes.getChildren().clear();
        boxDestinations.getChildren().clear();
        nomJoueur.setText(jc.getNom());
        pionsB.setText("Pions bateaux : "+jc.getNbPionsBateau());
        pionsW.setText("Pions wagons : "+jc.getNbPionsWagon());
        for(ICarteTransport carte : jc.getCartesTransport()){
            cT = new VueCarteTransport(carte, 1);
            cT.addEventHandler(MouseEvent.MOUSE_CLICKED ,choisirCarte);
            boxCartes.getChildren().addAll(cT);
        }
         for(IDestination carte : jc.getDestinations()){
            boxDestinations.getChildren().addAll(new VueDestination(carte));
        }
    };


    public void creerBindings(){
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener((observableValue, ancienJ, joueurCourant) ->
                nomJoueur.setText(joueurCourant.getNom()));
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(joueurChange);
    }
}
