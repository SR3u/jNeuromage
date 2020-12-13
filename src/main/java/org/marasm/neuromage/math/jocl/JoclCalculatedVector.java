package org.marasm.neuromage.math.jocl;

import org.marasm.neuromage.math.CalculatedVector;
import org.marasm.neuromage.math.Vector;

import java.nio.DoubleBuffer;

public class JoclCalculatedVector implements CalculatedVector {

    private final double[] data;

    public JoclCalculatedVector(DoubleBuffer buffer) {
        this.data = buffer.array();
    }

    public JoclCalculatedVector(double[] data) {
        this.data = data;
    }

    @Override
    public double[] data() {
        return data;
    }

    @Override
    public Vector copy() {
        return new JoclCalculatedVector(data);
    }

    @Override
    public int size() {
        return 0;
    }
}
