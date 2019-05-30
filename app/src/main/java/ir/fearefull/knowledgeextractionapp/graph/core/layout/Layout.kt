package ir.fearefull.knowledgeextractionapp.graph.core.layout

import ir.fearefull.knowledgeextractionapp.data.model.other.Node
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Point2D
import org.apache.commons.collections4.Transformer

internal interface Layout : Transformer<Node, Point2D> {

    /**
     * Initialize void.
     */
    fun initialize()

    /**
     * provides initial locations for all vertices.
     *
     * @param initializer the initializer
     */
    fun setInitializer(initializer: Transformer<Node, Point2D>)

    /**
     * Reset void.
     */
    fun reset()


    /**
     * Sets a flag which fixes this vertex in place.
     *
     * @param v     vertex
     * @param state the state
     */
    fun lock(v: Node, state: Boolean)

    /**
     * Returns `true` if the position of vertex `v`
     * is locked.
     *
     * @param v the v
     * @return the boolean
     */
    fun isLocked(v: Node): Boolean

    /**
     * set the location of a vertex
     *
     * @param v        the v
     * @param location the location
     */
    fun setLocation(v: Node, location: Point2D)
}
