package classes;

import javafx.scene.paint.Color;

public class LastLayer {
    private ColorNode R;
    private ColorNode G;
    private ColorNode B;

    public LastLayer(Integer inputSize){
        this.R = new ColorNode(inputSize);
        this.G = new ColorNode(inputSize);
        this.B = new ColorNode(inputSize);
    }

    public Double[] getDeltas(Color c){
        return new Double[]{this.R.getDelta(c.getRed()),this.G.getDelta(c.getGreen()),this.B.getDelta(c.getBlue())};
    }

    public Double[][] getWeights(){
        return new Double[][]{this.R.getWeight(), this.G.getWeight(), this.B.getWeight()};
    }

    public void input(Double[] input) {
        this.R.input(input);
        this.G.input(input);
        this.B.input(input);
    }

    public Double[] getOutput(){
        return new Double[]{this.R.getOutput(), this.G.getOutput(), this.B.getOutput()};
    }

    public void repairWeights() {
        this.R.repairWeights();
        this.G.repairWeights();
        this.B.repairWeights();
    }
}
