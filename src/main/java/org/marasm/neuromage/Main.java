package org.marasm.neuromage;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.math.VectorMathBuilder;
import org.marasm.neuromage.neural.Layers;
import org.marasm.neuromage.neural.NeuralNetwork;

import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        VectorMath math = VectorMathBuilder.get();
        Layers layers = new Layers(math);
        NeuralNetwork neuralNetwork = new NeuralNetwork(
                layers.sum(3, 2),
                layers.sigmoid(128, 3).learning(),
                layers.sigmoid(1024, 128).learning(),
                layers.sigmoid(1024, 1024).learning(),
                layers.sigmoid(1024, 1024).learning(),
                layers.sigmoid(128, 1024).learning(),
                layers.sigmoid(2, 128).learning()
        );
        long time = benchmark(math, neuralNetwork);
        System.out.println(time + "ms");
    }

    private static long benchmark(VectorMath math, NeuralNetwork neuralNetwork) {
        Vector output = neuralNetwork.output(math.vector(1, 1));
        long neurons = neuralNetwork.summary().getNeurons();
        System.out.println(neurons);
        long N = 10;
        if (neurons < 10000) {
            N = neurons / 3000;
        }
        for (long i = 0; i < N; i++) { // waiting for JIT
            output = neuralNetwork.output(math.vector(1, 1));
        }
        long start = new Date().getTime();
        for (long i = 0; i < N; i++) {
            output = neuralNetwork.output(math.vector(1, 1));
        }
        long end = new Date().getTime();
        System.out.println(output);
        return (end - start) / N;
    }

   /* @NotNull
    private static ImageFrame imageFrame(Image image, String title) {
        final ImageFrame current = new ImageFrame(title, image);
        current.setSize(192, 192);
        current.setVisible(true);
        return current;
    }*/
}
