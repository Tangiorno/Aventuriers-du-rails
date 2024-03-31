package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Transport.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteTransport extends Button {

    private final ICarteTransport carteTransport;

    public VueCarteTransport(ICarteTransport carteTransport, int nbCartes) {

        this.carteTransport = carteTransport;
        ImageView sqesque = new ImageView("images/cartesWagons/carte-"+(carteTransport.estBateau() ? "BATEAU" : carteTransport.estWagon() ? "WAGON" : carteTransport.estJoker() ? "JOKER" : carteTransport.estDouble() ? "DOUBLE" : "")+"-"+carteTransport.getStringCouleur()+(carteTransport.getAncre() ? "-A" : "")+".png");
        this.setGraphic(sqesque);
        sqesque.setFitHeight(105);
        sqesque.setFitWidth(115);
        addEventHandlers();
    }

    public ICarteTransport getCarteTransport() {
        return carteTransport;
    }
    private void addEventHandlers() {
        setOnMousePressed(this::handleMousePressed);
        setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        setScaleX(0.9);
        setScaleY(0.9);
    }

    private void handleMouseReleased(MouseEvent event) {
        setScaleX(1.0);
        setScaleY(1.0);
        System.out.println("Button clicked: " + getText());
    }

    public ICarteTransport getCarte(){
        return getCarteTransport();
    }
}
