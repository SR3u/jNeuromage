package org.marasm.neuromage.math.java;

import org.jetbrains.annotations.NotNull;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.Arrays;

public class JavaVectorMath implements VectorMath<JavaVector> {

    @Override
    public JavaVector vector(double... array) {
        return new JavaVector(array);
    }

    @Override
    public JavaVector convert(Vector v) {
        if (v instanceof JavaVector) {
            return (JavaVector) v;
        }
        return VectorMath.super.convert(v);
    }

    @Override
    public JavaVector add(JavaVector a, JavaVector b) {
        JavaVector r = createResultVector(a, b);
        loop(r, (i) -> a.get(i) + b.get(i));
        return r;
    }

    @Override
    public JavaVector sub(JavaVector a, JavaVector b) {
        JavaVector r = createResultVector(a, b);
        loop(r, (i) -> a.get(i) - b.get(i));
        return r;
    }

    @Override
    public JavaVector add(JavaVector a, double b) {
        JavaVector r = createResultVector(a);
        loop(r, (i) -> a.get(i) + b);
        return r;
    }

    @Override
    public JavaVector sub(JavaVector a, double b) {
        JavaVector r = createResultVector(a);
        loop(r, (i) -> a.get(i) - b);
        return r;
    }

    @Override
    public JavaVector sub(double a, JavaVector b) {
        JavaVector r = createResultVector(b);
        loop(r, (i) -> a - b.get(i));
        return r;
    }

    @Override
    public JavaVector mul(JavaVector a, JavaVector b) {
        JavaVector r = createResultVector(a, b);
        loop(r, (i) -> a.get(i) * b.get(i));
        return r;
    }

    @Override
    public JavaVector mul(JavaVector a, double b) {
        JavaVector r = createResultVector(a);
        loop(r, (i) -> a.get(i) * b);
        return r;
    }

    @Override
    public JavaVector div(JavaVector a, JavaVector b) {
        JavaVector r = createResultVector(a, b);
        loop(r, (i) -> a.get(i) / b.get(i));
        return r;
    }

    @Override
    public JavaVector div(double a, JavaVector b) {
        JavaVector r = createResultVector(b);
        loop(r, (i) -> a / b.get(i));
        return r;
    }

    @Override
    public JavaVector div(JavaVector a, double b) {
        JavaVector r = createResultVector(a);
        loop(r, (i) -> a.get(i) / b);
        return r;
    }

    @Override
    public JavaVector exp(JavaVector a) {
        JavaVector r = createResultVector(a);
        loop(r, (i) -> Math.exp(a.get(i)));
        return r;
    }

    @Override
    public double sum(JavaVector a) {
        return Arrays.stream(a.get()).sum();
    }

    private JavaVector createResultVector(JavaVector a) {
        return new JavaVector(a.size());
    }

    private void loop(JavaVector r, Op op) {
        for (int i = 0; i < r.size(); i++) {
            r.set(i, op.apply(i));
        }
    }

    @NotNull
    private JavaVector createResultVector(JavaVector a, JavaVector b) {
        assertEqualSize(a, b);
        return createResultVector(a);
    }

    private void assertEqualSize(JavaVector a, JavaVector b) {
        if (a.size() != b.size()) {
            try {
                throw new IllegalAccessException("Expected equal sized vectors!");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
