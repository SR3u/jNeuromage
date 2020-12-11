package org.marasm.neuromage;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.math.java.JavaVector;
import org.marasm.neuromage.math.java.JavaVectorMath;
import org.marasm.neuromage.neural.Layers;
import org.marasm.neuromage.neural.NeuralNetwork;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        VectorMath<JavaVector> math = new JavaVectorMath();
        Layers<JavaVector> layers = new Layers<>(math);
        NeuralNetwork<JavaVector> neuralNetwork = new NeuralNetwork<>(
                layers.sum(3, 2),
                layers.sigmoid(3, 3),
                layers.sigmoid(3, 3)
        );
        Vector output = neuralNetwork.output(math.vector(1, 2));
        System.out.println(output);
    }

   /* @NotNull
    private static ImageFrame imageFrame(Image image, String title) {
        final ImageFrame current = new ImageFrame(title, image);
        current.setSize(192, 192);
        current.setVisible(true);
        return current;
    }*/
}
