package org.marasm.neuromage.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix {
    private final VectorMath math;
    List<Vector> rows;
    final int r;
    final int c;

    public Matrix(VectorMath math, int rows, int columns) {
        this.math = math;
        this.r = rows;
        this.c = columns;
        this.rows = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            this.rows.add(math.vector(new double[columns]));
        }
    }

    public Matrix(VectorMath math, double[][] data, int r, int c) {
        this(math, Arrays.stream(data).map(math::vector).collect(Collectors.toList()), r, c);

    }

    public Matrix(VectorMath math, List<Vector> data, int r, int c) {
        this.math = math;
        this.r = r;
        this.c = c;
        this.rows = new ArrayList<>(r);
        for (int i = 0; i < r; i++) {
            this.rows.add(data.get(i));
        }
    }

    public Matrix transpose() {
        double[][] data = new double[r][c];
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
        return new Matrix(math, data, r, c);
    }

    public Vector sumRows() {
        return math.vector(rows.stream().mapToDouble(math::sum).toArray());
    }

    public Matrix add(Vector d) {
        List<Vector> data = rows.stream().map(v -> math.add(v, d)).collect(Collectors.toList());
        return new Matrix(math, data, c, r);
    }
}
