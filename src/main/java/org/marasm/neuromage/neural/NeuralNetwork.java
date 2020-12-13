package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;

import java.util.Arrays;
import java.util.List;

public class NeuralNetwork {
    private final List<Layer> layers;

    public NeuralNetwork(List<Layer> layers) {
        this.layers = layers;
    }

    @SafeVarargs
    public NeuralNetwork(Layer... layers) {
        this(Arrays.asList(layers));
    }

    public Vector output(Vector input) {
        for (Layer l : layers) {
            input = l.output(input);
        }
        return input;
    }

    public NeuralNetworkSummary summary() {
        return NeuralNetworkSummary.builder()
                .layers(layers.size())
                .neurons(layers.stream().mapToLong(Layer::getNeuronsCount).sum())
                .build();
    }
}
