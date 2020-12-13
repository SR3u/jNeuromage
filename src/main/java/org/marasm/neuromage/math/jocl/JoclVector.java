package org.marasm.neuromage.math.jocl;


import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;
import org.marasm.neuromage.math.CalculatedVector;
import org.marasm.neuromage.math.Vector;

import java.nio.DoubleBuffer;

import static org.jocl.CL.clSetKernelArg;

public class JoclVector implements Vector {
    private final DoubleBuffer buffer;
    private final int size;

    public JoclVector(double... array) {
        size = array.length;
        buffer = createDoubleBuffer(array);
    }

    private DoubleBuffer createDoubleBuffer(double[] array) {
        return DoubleBuffer.wrap(array);
    }

    void setKernelArgument(cl_kernel kernel, int i) {
        clSetKernelArg(kernel, i, Sizeof.cl_mem, Pointer.to(buffer));
    }

    @Override
    public CalculatedVector calculate() {
        return new JoclCalculatedVector(buffer);
    }

    @Override
    public Vector copy() {
        return new JoclVector(buffer.array());
    }

    public int size() {
        return size;
    }
}
