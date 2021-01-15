package org.marasm.neuromage.neural;

import org.marasm.neuromage.neural.activation.Sigmoid;
import org.marasm.neuromage.neural.activation.Sum;

import java.io.Serializable;

public class Layers implements Serializable {

    public static Layer sigmoid(int size) {
        return new Layer(size, new Sigmoid());
    }

    public static Layer sum(int size) {
        return new Layer(size, new Sum());
    }
}