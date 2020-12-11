package org.marasm.neuromage.math;

//Vectorized math interface
public interface VectorMath<V extends Vector> {

    V vector(double... array);

    default V convert(Vector v) {
        return vector(v.calculate().data());
    }

    V add(V a, V b); // returns V X: Xi = Ai + Bi

    V sub(V a, V b); // returns V X: Xi = Ai - Bi

    V add(V a, double b); // returns V X: Xi = Ai + b

    default V add(double a, V b) { // returns V X: Xi = a +  Bi
        return add(b, a);
    }

    V sub(V a, double b); // returns V X: Xi = Ai - b

    V sub(double a, V b); // returns V X: Xi = a - Bi

    V mul(V a, V b); // returns V X: Xi = Ai * Bi

    V mul(V a, double b); // returns V X: Xi = Ai * b

    default V mul(double a, V b) { // returns V X: Xi = a *  Bi
        return mul(b, a);
    }

    V div(V a, V b); // returns V X: Xi = Ai / Bi

    V div(double a, V b); // returns V X: Xi = a / Bi

    V div(V a, double b); // returns V X: Xi = Ai / b

    V exp(V a); // returns V X: Xi = Math.exp(Ai)

    double sum(V a);

    default V sigmoid(V x) { // returns V Y: 1 / (1 + Math.exp(Xi))
        return div(1, add(1, exp(x)));
    }

    default Matrix<V> matrix(double[][] data, int rows, int columns) {
        return new Matrix<>(this, data, rows, columns);
    }
}
