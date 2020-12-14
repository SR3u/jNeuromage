package org.marasm.neuromage.math;

import org.marasm.neuromage.math.java.JavaVectorMath;
import org.marasm.neuromage.math.jocl.JoclVectorMath;

public class VectorMathHolder {
    private static ThreadLocal<VectorMath> INSTANCE = ThreadLocal.withInitial(VectorMathHolder::create);

    public static VectorMath get() {
        return INSTANCE.get();
    }

    private static VectorMath create() {
        try {
            return new JoclVectorMath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JavaVectorMath();
    }
}
