package ir.fearefull.knowledgeextractionapp.graph.core.bean;

import org.apache.commons.collections4.Transformer;

import java.util.Date;
import java.util.Random;

public class RandomLocationTransformer<V> implements Transformer<V, Point2D> {

    /**
     * The D.
     */
    private Dimension d;

    /**
     * The Random.
     */
    private Random random;

    /**
     * Creates an instance with the specified size which uses the current time
     * as the random seed.
     *
     * @param d the d
     */
    public RandomLocationTransformer(Dimension d) {
        this(d, new Date().getTime());
    }

    /**
     * Creates an instance with the specified dimension and random seed.
     *
     * @param d    the d
     * @param seed the seed
     */
    private RandomLocationTransformer(final Dimension d, long seed) {
        this.d = d;
        this.random = new Random(seed);
    }

    /**
     * Transform point 2 d.
     *
     * @param v the v
     * @return the point 2 d
     */
    public Point2D transform(V v) {
        return new Point2D(random.nextDouble() * d.width, random.nextDouble() * d.height);
    }
}
