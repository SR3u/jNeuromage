package org.marasm.neuromage.math;

import org.marasm.neuromage.math.java.JavaVectorMath;
import org.marasm.neuromage.math.jocl.JoclVectorMath;

public class VectorMathBuilder {
    public static VectorMath get() {
        try {
            return new JoclVectorMath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JavaVectorMath();
    }
}
