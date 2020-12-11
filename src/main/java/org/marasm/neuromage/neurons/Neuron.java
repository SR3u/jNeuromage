package org.marasm.neuromage.neurons;

import java.util.List;

public interface Neuron {

    double output(List<Double> inputs);

    void learnBackPropagation(double learningRate);


    default void calculateErrorOutput(double expected) {
    }

    default void calculateError(double expected) {
    }

    default double getDelta() {
        return 0.0;
    }

    List<Double> getWeights();
}
