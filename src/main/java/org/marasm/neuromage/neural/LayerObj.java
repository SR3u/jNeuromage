package org.marasm.neuromage.neural;

import java.io.Serializable;

public class LayerObj implements Serializable {
    private final int inputSize;
    private final int size;
    private final double[] data;
    private final ActivationFunction activationFunc;

    public LayerObj(int inputSize, int size, double[] data, ActivationFunction activationFunc) {
        this.inputSize = inputSize;
        this.size = size;
        this.data = data;
        this.activationFunc = activationFunc;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getSize() {
        return size;
    }

    public double[] getData() {
        return data;
    }

    public ActivationFunction getActivationFunc() {
        return activationFunc;
    }
}
