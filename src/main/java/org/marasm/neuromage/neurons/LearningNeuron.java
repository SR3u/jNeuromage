package org.marasm.neuromage.neurons;

import java.util.List;

public class LearningNeuron extends RegularNeuron {

    private List<Double> inputs;
    private double output;
    private double delta;

    public LearningNeuron(int inputs) {
        super(inputs);
    }

    public LearningNeuron(Neuron n) {
        super(n.getWeights().size());
        this.weights = n.getWeights();
    }

    @Override
    public double output(List<Double> inputs) {
        this.inputs = inputs;
        double output = super.output(inputs);
        this.output = output;
        return output;
    }

    @Override
    public void learnBackPropagation(double learningRate) {
        for (int i = 0; i < weights.size(); i++) {
            double w = weights.get(i) + learningRate * delta * inputs.get(i);
            weights.set(i, w);
        }
    }

    @Override
    public void calculateErrorOutput(double expectedOutput) {
        delta = output * (1 - output) * (expectedOutput - output);
    }

    @Override
    public void calculateError(double expectedOutput) {
        delta = output * (1 - output) * expectedOutput;
    }

    @Override
    public double getDelta() {
        return delta;
    }
}
