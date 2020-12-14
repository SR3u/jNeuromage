package org.marasm.neuromage.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix implements Serializable {
    List<Vector> rows;
    final int r;
    final int c;

    private static VectorMath math = VectorMath.get();

    public Matrix(int rows, int columns) {
        this.r = rows;
        this.c = columns;
        this.rows = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            this.rows.add(math.vector(new double[columns]));
        }
    }

    public Matrix(VectorMath math, double[][] data, int r, int c) {
        this(Arrays.stream(data).map(math::vector).collect(Collectors.toList()), r, c);

    }

    public Matrix(List<Vector> data, int r, int c) {
        this.r = r;
        this.c = c;
        this.rows = new ArrayList<>(r);
        for (int i = 0; i < r; i++) {
            this.rows.add(data.get(i));
        }
    }

    public Matrix transpose() {
        double[][] data = new double[c][r];
        for (int i = 0; i < rows.size(); i++) {
            double[] row = rows.get(i).calculate().data();
            for (int j = 0; j < row.length; j++) {
                data[j][i] = row[j];
            }
        }
        return new Matrix(math, data, c, r);
    }

    public Stream<Vector> stream() {
        return rows.stream();
    }

    public long rows() {
        return r;
    }

    public Matrix mul(Vector v) {
        List<Vector> data = rows.stream().map(r -> math.mul(r, v)).collect(Collectors.toList());
        return new Matrix(data, r, c);
    }

    public Vector sumRows() {
        return math.vector(rows.stream().mapToDouble(math::sum).toArray());
    }

    public Matrix add(Vector d) {
        List<Vector> data = rows.stream().map(v -> math.add(v, d)).collect(Collectors.toList());
        return new Matrix(data, c, r);
    }

    public Matrix add(Matrix m) {
        assertEqualSize(this, m);
        List<Vector> rs = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            rs.add(math.add(rows.get(i), m.rows.get(i)));
        }
        return new Matrix(rs, r, c);
    }

    private static void assertEqualSize(Matrix a, Matrix b) {
        if (!(a.r == b.r) || !(a.c == b.c)) {
            throw new IllegalArgumentException("Matrix sizes are different!");
        }
    }
}
