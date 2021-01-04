package classes;

import java.util.ArrayList;

public class Layer {
    private ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(Integer sizeOfNeurons, Integer inputSize) {
        for(int i=0;i<sizeOfNeurons;i++){
            this.neurons.add(new Neuron(inputSize));
        }
    }

    public Integer getNeuronSize() {
        return this.neurons.size();
    }

    public void input(Double[] input) {
        for (int i = 0; i < this.neurons.size(); i++) {
            this.neurons.get(i).input(input);
        }
    }

    public Double[] getOutput() {
        Double[] resArray = new Double[this.neurons.size() + 1];
        for (int i = 0; i < this.neurons.size(); i++) {
            resArray[i] = this.neurons.get(i).getOutput();
        }
        resArray[this.neurons.size()] = 1.0;
        return resArray;
    }

    public Double[] getDeltas(Double[] deltas, Double[][] lastWeights) {
        Double[] resArray = new Double[this.neurons.size()];
        for (int i = 0; i < this.neurons.size(); i++) {

            Double[] lastWeight = new Double[lastWeights.length];
            for (int j = 0; j < lastWeights.length; j++) {
                lastWeight[j] = lastWeights[j][i];
            }

            resArray[i] = this.neurons.get(i).getDelta(deltas,lastWeight);
        }
        return resArray;
    }

    public Double[][] getWeights() {
        Double[][] resArray = new Double[this.neurons.size()][];
        for (int i = 0; i < this.neurons.size(); i++) {
            resArray[i] = this.neurons.get(i).getWeight();
        }
        return resArray;
    }

    public void repairWeights() {
        for (int i = 0; i < this.neurons.size(); i++) {
            this.neurons.get(i).repairWeights();
        }
    }
}
