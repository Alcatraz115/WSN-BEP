package classes;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Network {
    private Label frameLabel;
    private Label iteratorLabel;

    private ArrayList<Layer> layers = new ArrayList<>();
    private LastLayer lastLayer;

    private Thread learnThread;
    private GraphicsContext context;
    private Image image;

    private Integer iterator = 0;
    private Integer frame = 0;

    public Network(GraphicsContext context, Image imgInput, Label itLab, Label frLab){
        this.context = context;
        this.image = imgInput;
        this.iteratorLabel = itLab;
        this.frameLabel = frLab;


        this.learnThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!learnThread.isInterrupted()){

                    synchronized (iterator){
                        iterator++;
                    }
                    learn();

                    synchronized (iterator){
                        if(iterator >= 100000){
                            iterator = 0;
                            frame++;
                            draw();
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                iteratorLabel.setText(String.valueOf(iterator));
                            }
                        });
                    }

                    synchronized (frame){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                frameLabel.setText(String.valueOf(frame));
                            }
                        });
                    }
                }
                System.out.println("interupted");
            }
        });
    }

    public void addLayer(Layer l){
        this.layers.add(l);
        this.lastLayer = new LastLayer(this.layers.get(this.layers.size() - 1).getNeuronSize());
    }

    public void startLearning(){
        this.learnThread.start();
    }

    public void stopLearning(){
        this.learnThread.interrupt();
    }

    private Color check(Double x,Double y){
        Double[] input = new Double[]{x,y,1.0};

        for (int i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).input(input);
            input = this.layers.get(i).getOutput();
        }

        this.lastLayer.input(input);

        Double[] o = this.lastLayer.getOutput();

        return new Color(o[0],o[1],o[2],1.0);
    }

    private void draw(){
        WritableImage imgRes = new WritableImage((int) this.image.getWidth(),(int) this.image.getHeight());

        for(int y = 0; y<this.image.getHeight(); y++){
            for(int x = 0; x<this.image.getWidth(); x++){
                Double xInput = (x / this.image.getWidth()) * 2.0 - 1.0;
                Double yInput = (y / this.image.getHeight()) * 2.0 - 1.0;

                Color c = this.check(xInput,yInput);

                imgRes.getPixelWriter().setColor(x,y,c);
            }
        }

        this.context.drawImage(imgRes,0,0);
    }

    private void learn(){
        Double xInput = MathHelper.getRandom(-1.0,1.0);
        Double yInput = MathHelper.getRandom(-1.0,1.0);

        Double[] input = new Double[]{xInput,yInput,1.0};

        for (int i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).input(input);
            input = this.layers.get(i).getOutput();
        }

        this.lastLayer.input(input);

        Integer x = Double.valueOf((xInput + 1.0) / 2.0 * 225.0).intValue();
        Integer y = Double.valueOf((yInput + 1.0) / 2.0 * 225.0).intValue();

        Color c = this.image.getPixelReader().getColor(x,y);

        Double[] deltas = this.lastLayer.getDeltas(c);
        Double[][] lastWeights = this.lastLayer.getWeights();

        for (int i = this.layers.size() - 1; i >= 0; i--) {
            deltas = this.layers.get(i).getDeltas(deltas,lastWeights);
            lastWeights = this.layers.get(i).getWeights();
        }

        for (int i = 0; i < this.layers.size(); i++) {
            this.layers.get(i).repairWeights();
        }

        this.lastLayer.repairWeights();
    }
}
