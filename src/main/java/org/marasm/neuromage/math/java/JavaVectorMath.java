package org.marasm.neuromage.math.java;

import org.jetbrains.annotations.NotNull;
import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;

import java.util.Arrays;
import java.util.stream.IntStream;

public class JavaVectorMath implements VectorMath {

    @Override
    public JavaVector vector(double... array) {
        return new JavaVector(array);
    }

    @Override
    public JavaVector convert(Vector v) {
        if (v instanceof JavaVector) {
            return (JavaVector) v;
        }
        return vector(v.calculate().data());
    }

    @Override
    public Vector add(Vector a, Vector b) {
        JavaVector A = convert(a);
        JavaVector B = convert(b);
        JavaVector r = createResultVector(A, B);
        loop(r, (i) -> A.get(i) + B.get(i));
        return r;
    }

    @Override
    public Vector sub(Vector a, Vector b) {
        JavaVector A = convert(a);
        JavaVector B = convert(b);
        JavaVector r = createResultVector(A, B);
        loop(r, (i) -> A.get(i) - B.get(i));
        return r;
    }

    @Override
    public Vector add(Vector a, double b) {
        JavaVector A = convert(a);
        JavaVector r = createResultVector(A);
        loop(r, (i) -> A.get(i) + b);
        return r;
    }

    @Override
    public Vector sub(Vector a, double b) {
        JavaVector A = convert(a);
        JavaVector r = createResultVector(A);
        loop(r, (i) -> A.get(i) - b);
        return r;
    }

    @Override
    public Vector sub(double a, Vector b) {
        JavaVector B = convert(b);
        JavaVector r = createResultVector(B);
        loop(r, (i) -> a - B.get(i));
        return r;
    }

    @Override
    public Vector mul(Vector a, Vector b) {
        JavaVector A = convert(a);
        JavaVector B = convert(b);
        JavaVector r = createResultVector(A, B);
        loop(r, (i) -> A.get(i) * B.get(i));
        return r;
    }

    @Override
    public Vector mul(Vector a, double b) {
        JavaVector A = convert(a);
        JavaVector r = createResultVector(A);
        loop(r, (i) -> A.get(i) * b);
        return r;
    }

    @Override
    public Vector div(Vector a, Vector b) {
        JavaVector A = convert(a);
        JavaVector B = convert(b);
        JavaVector r = createResultVector(A, B);
        loop(r, (i) -> A.get(i) / B.get(i));
        return r;
    }

    @Override
    public Vector div(double a, Vector b) {
        JavaVector B = convert(b);
        JavaVector r = createResultVector(B);
        loop(r, (i) -> a / B.get(i));
        return r;
    }

    @Override
    public Vector div(Vector a, double b) {
        JavaVector A = convert(a);
        JavaVector r = createResultVector(A);
        loop(r, (i) -> A.get(i) / b);
        return r;
    }

    @Override
    public JavaVector exp(Vector a) {
        JavaVector A = convert(a);
        JavaVector r = createResultVector(A);
        loop(r, (i) -> Math.exp(A.get(i)));
        return r;
    }

    @Override
    public double sum(Vector a) {
        JavaVector A = convert(a);
        return Arrays.stream(A.get()).sum();
    }

    private JavaVector createResultVector(JavaVector a) {
        return new JavaVector(a.size());
    }

    private void loop(JavaVector r, Op op) {
        IntStream range = IntStream.range(0, r.size());
        if (r.size() > 20000) {
            range = range.parallel();
        }
        range.forEach(i -> r.set(i, op.apply(i)));
    }

    @NotNull
    private JavaVector createResultVector(JavaVector a, JavaVector b) {
        assertEqualSize(a, b);
        return createResultVector(a);
    }

    private void assertEqualSize(JavaVector a, JavaVector b) {
        if (a.size() != b.size()) {
            try {
                throw new IllegalArgumentException("Expected equal sized vectors!");
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
