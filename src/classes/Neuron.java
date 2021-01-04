package classes;

public class Neuron {
    private Double[] weights;
    private Double[] lastInput;
    private Double inputSum;
    private Double output;
    private Double lConst = 0.1;
    private Double delta;

    public Neuron(Integer inputSize) {
        this.weights = new Double[inputSize + 1];
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = MathHelper.getRandom(-1.0,1.0);
        }
    }

    public void input(Double[] val){
        this.lastInput = new Double[val.length];
        if(val.length != this.weights.length){
            System.out.println("err: " + String.valueOf(val.length) + " " + String.valueOf(this.weights.length));
        }
        this.inputSum = 0.0;
        for (int i = 0; i < this.weights.length; i++) {
            lastInput[i] = val[i];
            this.inputSum += val[i] * this.weights[i];
        }

        this.output = MathHelper.sigmaFunc(this.inputSum);
    }

    public Double getDelta(Double[] deltas,Double[] lastWeights){
        this.delta = 0.0;
        for (int i = 0; i < deltas.length; i++) {
            this.delta += deltas[i] * lastWeights[i];
        }
        this.delta *= MathHelper.sigmaPochodnaFunc(this.inputSum);
        return this.delta;
    }

    public void repairWeights(){
        for (int i = 0; i < this.weights.length; i++) {
            synchronized (this.weights[i]){
                this.weights[i] -= this.lConst * this.delta * this.lastInput[i];
            }
        }
    }

    public Double getOutput() {
        return this.output;
    }

    public Double[] getWeight() {
        return this.weights;
    }
}
