package org.marasm.neuromage.neurons;

import java.util.ArrayList;
import java.util.List;

public class InputNeuron implements Neuron {

    protected List<Double> weights;

    public InputNeuron(int inputs) {
        this(inputs, 0);
    }

    public InputNeuron(int inputs, int number) {
        weights = new ArrayList<>(inputs);
        for (int i = 0; i < inputs; i++) {
            if (i == number) {
                weights.add(1.0);
            } else {
                weights.add(0.0);
            }
        }
    }

    @Override
    public double output(List<Double> inputs) {
        //calculating sum of input signals
        double sum = 0.0;
        for (int i = 0; i < Math.min(weights.size(), inputs.size()); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        return sum;
    }

    @Override
    public void learnBackPropagation(double learningRate) { //input neurons aren't learning
    }

    @Override
    public List<Double> getWeights() {
        return weights;
    }

}
