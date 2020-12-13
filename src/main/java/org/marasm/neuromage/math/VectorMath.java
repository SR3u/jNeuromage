package org.marasm.neuromage.math;

import java.util.stream.IntStream;

//Vectorized math interface
public interface VectorMath {

    static VectorMath get() {
        return VectorMathBuilder.get();
    }

    Vector vector(double... array);

    default Vector vector(int size, double fill) {
        return vector(IntStream.range(0, size).mapToDouble(i -> fill).toArray());
    }

    default Vector convert(Vector v) {
        return vector(v.calculate().data());
    }

    Vector add(Vector a, Vector b); // returns Vector X: Xi = Ai + Bi

    Vector sub(Vector a, Vector b); // returns Vector X: Xi = Ai - Bi

    Vector add(Vector a, double b); // returns Vector X: Xi = Ai + b

    default Vector add(double a, Vector b) { // returns Vector X: Xi = a +  Bi
        return add(b, a);
    }

    Vector sub(Vector a, double b); // returns Vector X: Xi = Ai - b

    Vector sub(double a, Vector b); // returns Vector X: Xi = a - Bi

    Vector mul(Vector a, Vector b); // returns Vector X: Xi = Ai * Bi

    Vector mul(Vector a, double b); // returns Vector X: Xi = Ai * b

    default Vector mul(double a, Vector b) { // returns Vector X: Xi = a *  Bi
        return mul(b, a);
    }

    Vector div(Vector a, Vector b); // returns Vector X: Xi = Ai / Bi

    Vector div(double a, Vector b); // returns Vector X: Xi = a / Bi

    Vector div(Vector a, double b); // returns Vector X: Xi = Ai / b

    Vector exp(Vector a); // returns Vector X: Xi = Math.exp(Ai)

    double sum(Vector a);

    default Vector sigmoid(Vector x) { // returns Vector Y: 1 / (1 + Math.exp(Xi))
        return div(1, add(1, exp(x)));
    }

    default Matrix matrix(double[][] data, int rows, int columns) {
        return new Matrix(this, data, rows, columns);
    }

    default Matrix matrix(int rows, int columns) {
        return new Matrix(this, rows, columns);
    }

}
