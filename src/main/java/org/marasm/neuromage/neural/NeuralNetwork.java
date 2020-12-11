package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;

import java.util.Arrays;
import java.util.List;

public class NeuralNetwork<V extends Vector> {
    private final List<Layer<V>> layers;

    public NeuralNetwork(List<Layer<V>> layers) {
        this.layers = layers;
    }

    @SafeVarargs
    public NeuralNetwork(Layer<V>... layers) {
        this(Arrays.asList(layers));
    }

    public V output(V input) {
        for (Layer<V> l : layers) {
            input = l.output(input);
        }
        return input;
    }
}
