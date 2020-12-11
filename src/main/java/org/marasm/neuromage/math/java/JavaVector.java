package org.marasm.neuromage.math.java;

import org.marasm.neuromage.math.Vector;

import java.util.Arrays;

public class JavaVector implements Vector {
    private double[] array;

    public JavaVector(double[] array) {
        this.array = array;
    }

    public JavaVector(int size) {
        this(size, 0.0);
    }

    public JavaVector(int size, double fill) {
        this.array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = fill;
        }
    }

    public int size() {
        return array.length;
    }

    public double[] get() {
        return array;
    }


    public double get(int i) {
        return array[i];
    }

    public void set(int i, double value) {
        array[i] = value;
    }

    public void set(double[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public double[] data() {
        return array;
    }
}
