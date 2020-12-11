package org.marasm.neuromage.neural;

import org.marasm.neuromage.math.Vector;

public class Neuron<V extends Vector> {

    private final V weighs;

    public Neuron(V weighs) {
        this.weighs = weighs;
    }

    public V getWeighs() {
        return weighs;
    }

}
