package ir.fearefull.knowledgeextractionapp.graph.core.bean

import org.apache.commons.collections4.Transformer
import java.util.*

class RandomLocationTransformer<V>
/**
 * Creates an instance with the specified dimension and random seed.
 *
 * @param d    the d
 * @param seed the seed
 */
private constructor(
    /**
     * The D.
     */
    private val d: Dimension, seed: Long
) : Transformer<V, Point2D> {

    /**
     * The Random.
     */
    private val random: Random = Random(seed)

    /**
     * Creates an instance with the specified size which uses the current time
     * as the random seed.
     *
     * @param d the d
     */
    constructor(d: Dimension) : this(d, Date().time)

    /**
     * Transform point 2 d.
     *
     * @param v the v
     * @return the point 2 d
     */
    override fun transform(v: V): Point2D {
        return Point2D(random.nextDouble() * d.width, random.nextDouble() * d.height)
    }
}
