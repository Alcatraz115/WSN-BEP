package classes;

public class ColorNode {
    private Double[] weights;
    private Double[] lastInput;
    private Double lConst = 0.1;
    private Double inputSum = 0.0;
    private Double output = 0.0;
    private Double delta = 0.0;

    public ColorNode(Integer inputSize){
        this.weights = new Double[inputSize + 1];
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = MathHelper.getRandom(-1.0,1.0);
        }
    }

    public void input(Double[] val){
        this.lastInput = new Double[val.length];
        this.inputSum = 0.0;
        for (int i = 0; i < val.length; i++) {
            lastInput[i] = val[i];
            this.inputSum += val[i] * this.weights[i];
        }

        this.output = MathHelper.sigmaFunc(this.inputSum);
    }

    public Double getDelta(Double value){
        this.delta = (this.output - value) * MathHelper.sigmaPochodnaFunc(this.inputSum);
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
