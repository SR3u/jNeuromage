package org.marasm.neuromage.math.jocl;

import org.marasm.neuromage.math.CalculatedVector;

import java.nio.DoubleBuffer;

public class JoclCalculatedVector implements CalculatedVector {

    private final double[] data;

    public JoclCalculatedVector(DoubleBuffer buffer) {
        this.data = buffer.array();
    }

    @Override
    public double[] data() {
        return data;
    }
}
