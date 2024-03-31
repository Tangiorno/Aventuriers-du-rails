package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les cartes Transport visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private final IJeu jeu;
    private final VuePlateau plateau;

    private final Button passer;
    private final Label instruct;
    private VBox listeDestinations;
    private final VueJoueurCourant joueurCourant;

    private final VueAutresJoueurs scoreJoueurs;

    private final TextField texte;

    private HBox sousboxCartes;

    private int compteurPourPions;




    public VueDuJeu(IJeu jeu) {
        instruct = new Label();
        compteurPourPions = 0;
        Button echangerPionsB = new Button("Echanger des pions bateaux");
        Button echangerPionsW = new Button("Echanger des pions wagons");
        this.jeu = jeu;
        sousboxCartes = new HBox();
        HBox cartes = new HBox();
        texte = new TextField();
        //cartesVisibles = new VueCarteTransport( ,1);
        VBox tableau = new VBox();
        Button carteBateau = new Button();
        Button carteWagon = new Button();
        ImageView destiView = new ImageView("images/cartesWagons/destinations.png");
        destiView.setFitWidth(200);
        destiView.setFitHeight(125);
        ImageView waView = new ImageView("images/cartesWagons/dos-WAGON.png");
        ImageView baView = new ImageView("images/cartesWagons/dos-BATEAU.png");
        Button destiButt = new Button();
        waView.setFitHeight(150);
        waView.setFitWidth(110);
        baView.setFitWidth(100);
        baView.setFitHeight(150);
        carteWagon.setGraphic(waView);
        carteBateau.setGraphic(baView);
        destiButt.setGraphic(destiView);
        scoreJoueurs = new VueAutresJoueurs(jeu);
        plateau = new VuePlateau();
        plateau.setScaleX(1.1);
        plateau.setScaleY(1);
        passer = new Button("Passer");
        listeDestinations = new VBox();
        VBox controleDuJeu = new VBox();
        VBox echanges = new VBox();
        echanges.getChildren().addAll(echangerPionsW, echangerPionsB);
        controleDuJeu.getChildren().addAll(echanges , listeDestinations, instruct, texte, passer);
        instruct.setWrapText(true);
        controleDuJeu.setMaxWidth(300);
        controleDuJeu.setMinWidth(300);
        controleDuJeu.setSpacing(50);
        joueurCourant = new VueJoueurCourant();
        cartes.getChildren().addAll(destiButt, carteWagon, carteBateau, sousboxCartes);
        cartes.setSpacing(80);
        cartes.setAlignment(Pos.CENTER_LEFT);
        controleDuJeu.setAlignment(Pos.CENTER);
        joueurCourant.setAlignment(Pos.CENTER);
        BorderPane.setMargin(cartes, new Insets(0,0,40,0));
        BorderPane.setMargin(tableau, new Insets(0,10,0,0));
        BorderPane.setMargin(controleDuJeu, new Insets(20,0,0,30));
        BorderPane.setMargin(joueurCourant, new Insets(0,0,0,40));
        tableau.setMinWidth(250);
        tableau.setMaxWidth(250);
        tableau.setAlignment(Pos.CENTER);
        //controleDuJeu.setStyle("-fx-background-color: yellow");
        cartes.setMaxHeight(150);
        cartes.setMinHeight(150);
        cartes.setMaxWidth(1680);
        cartes.setMinWidth(1680);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        listeDestinations.setAlignment(Pos.CENTER);
        setBottom(cartes);
        //infoJoueurCourant.setStyle("-fx-background-color: green");
        setLeft(controleDuJeu);
        //plateau.setStyle("-fx-background-color: red");
        plateau.setMaxHeight(500);
        plateau.setMaxWidth(900);
        plateau.setMinHeight(500);
        plateau.setMinWidth(900);
        //cartes.setStyle("-fx-background-color: cyan");
        setTop(joueurCourant);
        tableau.getChildren().addAll(scoreJoueurs);
        tableau.setSpacing(30);
        setRight(tableau);
        echanges.setAlignment(Pos.CENTER);
        //droite.setStyle("-fx-background-color: blue");
        setCenter(plateau);
        passer.addEventHandler(MouseEvent.MOUSE_CLICKED, destEnlever);

        EventHandler<MouseEvent> echangeBateau = event -> getJeu().nouveauxPionsBateauxDemandes();
        echangerPionsB.addEventHandler(MouseEvent.MOUSE_CLICKED, echangeBateau);
        EventHandler<MouseEvent> echangeWagon = event -> {
            System.out.println("on est là " + getJeu().saisieNbPionsWagonAutoriseeProperty().get());

                getJeu().nouveauxPionsWagonsDemandes();

        };
        echangerPionsW.addEventHandler(MouseEvent.MOUSE_CLICKED, echangeWagon);
        EventHandler<MouseEvent> piocherBateau = event -> {

                getJeu().uneCarteBateauAEtePiochee();

        };
        carteBateau.addEventHandler(MouseEvent.MOUSE_CLICKED, piocherBateau);
        EventHandler<MouseEvent> piocherWagon = event -> {
                getJeu().uneCarteWagonAEtePiochee();

        };
        carteWagon.addEventHandler(MouseEvent.MOUSE_CLICKED, piocherWagon);
        EventHandler<MouseEvent> piocherDesti = event -> {
            getJeu().nouvelleDestinationDemandee();

        };
        destiButt.addEventHandler(MouseEvent.MOUSE_CLICKED, piocherDesti);
        texte.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(texte.getText());
            if(!texte.getText().equals("") && compteurPourPions < 4 &&Integer.parseInt(texte.getText()) >= 10 && Integer.parseInt(texte.getText()) <= 25) {
                getJeu().leNombreDePionsSouhaiteAEteRenseigne(newValue);
                compteurPourPions++;
            }
            else if(!texte.getText().equals("") && compteurPourPions >= 4){
                getJeu().leNombreDePionsSouhaiteAEteRenseigne(newValue);
            }
        });
        plateau.getStrRoute().addListener((observableValue, s, t1) -> {
            System.out.println("vue du jeu "+plateau.getStrRoute().getValue());
            System.out.println(t1);
            getJeu().uneRouteAEteChoisie(plateau.getStrRoute().getValue());
        });

        plateau.getStrPort().addListener((observableValue, s, t1) -> {
            getJeu().unPortAEteChoisi(plateau.getStrPort().getValue());
            System.out.println("cliquez  sur un port");
        });
    }

    EventHandler<MouseEvent> destEnlever = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(listeDestinations.getChildren().size() != 0){
                listeDestinations.getChildren().clear();
            }
        }
    };


    private final EventHandler<ActionEvent> piocherCarteVisible = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            VueCarteTransport sllt = (VueCarteTransport) actionEvent.getSource();
            getJeu().uneCarteTransportAEteChoisie(sllt.getCarte());
            for (int i = 0; i < sousboxCartes.getChildren().size(); i++) {
                if (sousboxCartes.getChildren().get(i) == sllt) {
                    sousboxCartes.getChildren().remove(i);
                    break;
                }
            }
        }
    };


    private final EventHandler<ActionEvent> piocherDestination = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            VueDestination sllt = (VueDestination) actionEvent.getSource();
            getJeu().uneDestinationAEteChoisie(sllt.getDestination()); //Une destinationAEteChoisie defausse la carte destination
            for (int i = 0; i < listeDestinations.getChildren().size(); i++) {
                if (listeDestinations.getChildren().get(i) == sllt) {
                    listeDestinations.getChildren().remove(i);
                    break;
                }
            }
        }
    };

    private final EventHandler<ActionEvent> retirerDestination = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            VueDestination retirer = (VueDestination) actionEvent.getSource();
            for (int i = 0; i < listeDestinations.getChildren().size(); i++) {
                if (listeDestinations.getChildren().get(i) == retirer) {
                    listeDestinations.getChildren().remove(i);
                    break;
                }
            }
        }
    };




    ListChangeListener<IDestination> initialD = change -> {
        while(change.next()){
            if(change.wasAdded()){
                if(listeDestinations.getChildren().size() < 5) {
                    for (IDestination destination : change.getAddedSubList()) {
                        VueDestination des = new VueDestination(destination);
                        des.setOnAction(piocherDestination);
                        listeDestinations.getChildren().add(des);
                    }
                }
            }
            else if(change.wasRemoved()){
                for(IDestination destination: change.getRemoved()){
                    VueDestination delete = new VueDestination(destination);
                    delete.setOnAction(retirerDestination);
                    listeDestinations.getChildren().remove(delete);
                    System.out.println(listeDestinations.getChildren().size());
                    if(listeDestinations.getChildren().size() == 4){
                        listeDestinations.getChildren().clear();
                    }
                }
            }
        }
    };



    ListChangeListener<ICarteTransport> setCartesVisibles = visibleCarte ->{
      while (visibleCarte.next()){
          if(visibleCarte.wasAdded()){
              for (ICarteTransport carteTransport : visibleCarte.getAddedSubList()){
                  VueCarteTransport cartesVisibles = new VueCarteTransport(carteTransport, 1);
                  cartesVisibles.setPrefSize(100,40);
                  cartesVisibles.setOnAction(piocherCarteVisible);
                  sousboxCartes.getChildren().addAll(cartesVisibles);
                  System.out.println("slt  "+sousboxCartes.getChildren().size());
              }
          }
      }
    };

    private Label trouverLabel(List<Node> list, IDestination dest){
        for(Node n: list){
            Label l = (Label) n;
            if(l.getText().equals(dest.getVilles().toString())  ){
                return l;
            }
        }
        return null;
    }

    public void creerBindings() {
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        jeu.destinationsInitialesProperty().addListener(initialD);
        jeu.cartesTransportVisiblesProperty().addListener(setCartesVisibles);
        passer.addEventHandler(MouseEvent.MOUSE_CLICKED, actionPasserParDefaut);
        passer.addEventHandler(KeyEvent.KEY_PRESSED, passerEntree);
        instruct.textProperty().bind(jeu.instructionProperty());
        joueurCourant.creerBindings();
        plateau.creerBindings();
        scoreJoueurs.creerBindings();

    }


    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());
    EventHandler<KeyEvent> passerEntree  = (keyEvent -> {
        if (keyEvent.getCode() == KeyCode.ENTER){
            getJeu().passerAEteChoisi();
        }
    });

    public Button getPasser() {
        return passer;
    }
}
