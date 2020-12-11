package org.marasm.neuromage.neurons;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RegularNeuron extends InputNeuron {

    public RegularNeuron(int inputs) {
        super(inputs);
        weights = this.weights.stream()
                .map(this::randomize)
                .collect(Collectors.toList());
    }

    private Double randomize(Double w) {
        return ThreadLocalRandom.current().nextDouble(-1, 1);
    }

    @Override
    public double output(List<Double> inputs) {
        return sigmoid(super.output(inputs));
    }

    @Override
    public void learnBackPropagation(double learningRate) {
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
