package views;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import classes.Layer;
import classes.Network;

import java.io.File;

public class mainView {

    @FXML
    private Canvas imageCanvas;
    private GraphicsContext imageContext;

    @FXML
    private Canvas resCanvas;
    private GraphicsContext resContext;

    @FXML
    private Label iteratorLabel;

    @FXML
    private Label frameLabel;

    private Image image;

    private Network network;

    public mainView() {
        File imgFile = new File("resources/jane.png");
        this.image = new Image(imgFile.toURI().toString());
        if (image.isError()){
            System.out.println(image.getException().getMessage());
        }
    }

    @FXML
    public void initialize(){
        this.iteratorLabel.setText("0");
        this.frameLabel.setText("0");

        this.imageContext = imageCanvas.getGraphicsContext2D();
        this.imageContext.drawImage(this.image,0,0);

        this.resContext = resCanvas.getGraphicsContext2D();
        this.resContext.setFill(Color.WHITE);
        this.resContext.fillRect(0,0, resCanvas.getWidth(), resCanvas.getHeight());

        this.network = new Network(this.resContext,this.image,this.iteratorLabel,this.frameLabel);
        this.network.addLayer(new Layer(60,2));
        this.network.addLayer(new Layer(60,60));
        this.network.addLayer(new Layer(60,60));
        this.network.addLayer(new Layer(60,60));
    }

    @FXML
    private void startLearn(){
        this.network.startLearning();
    }

    @FXML
    private void stopLearn(){
        this.network.stopLearning();
    }

}
