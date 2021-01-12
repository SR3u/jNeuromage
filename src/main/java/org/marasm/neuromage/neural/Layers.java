package org.marasm.neuromage.neural;

import sr3u.jvec.JMath;
import org.marasm.neuromage.neural.activation.Sigmoid;
import org.marasm.neuromage.neural.activation.Sum;

import java.io.Serializable;

public class Layers implements Serializable {
    private static final JMath math = JMath.get();

    public static Layer sigmoid(int size, int inputSize) {
        return new Layer(size, inputSize, new Sigmoid());
    }

    public static Layer sum(int size, int inputSize) {
        return new Layer(size, inputSize, new Sum());
    }
}