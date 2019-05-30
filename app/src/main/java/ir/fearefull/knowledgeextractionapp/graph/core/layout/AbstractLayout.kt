package ir.fearefull.knowledgeextractionapp.graph.core.layout

import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import ir.fearefull.knowledgeextractionapp.data.model.other.Node
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Dimension
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Point2D
import org.apache.commons.collections4.Transformer
import org.apache.commons.collections4.map.LazyMap
import timber.log.Timber
import java.util.*

abstract class AbstractLayout : Layout {

    private val dontmove = HashSet<Node>()

    private lateinit var size: Dimension

    private var graph: Graph

    var initialized: Boolean = false

    private var locations: Map<Node, Point2D> = LazyMap.lazyMap<Point2D, Node>(
        HashMap<Node, Point2D>()
    ) { (_) -> Point2D() }

    /**
     * Creates an instance which does not initialize the vertex locations.
     *
     * @param graph the graph for which the layout algorithm is to be created.
     */
    constructor(graph: Graph?) {
        if (graph == null) {
            throw IllegalArgumentException("Graph must be non-null")
        }
        this.graph = graph
    }

    /**
     * Instantiates a new Abstract layout.
     *
     * @param graph       the graph
     * @param initializer the initializer
     * @param size        the size
     */
    constructor(graph: Graph, initializer: Transformer<Node, Point2D>, size: Dimension) {
        this.graph = graph
        this.locations = LazyMap.lazyMap<Point2D, Node>(HashMap<Node, Point2D>(), initializer)
        this.size = size
    }

    /**
     * When a visualization is resized, it presumably wants to fix the
     * locations of the vertices and possibly to reinitialize its data. The
     * current method calls <tt>initializeLocations</tt> followed by <tt>initialize_local</tt>.
     *
     * @param size the size
     */
    open fun setSize(size: Dimension?) {
        if (size != null) {
            val oldSize = this.size
            this.size = size
            initialize()
            adjustLocations(oldSize, size)
        }
    }

    /**
     * Adjust locations.
     *
     * @param oldSize the old size
     * @param size    the size
     */
    private fun adjustLocations(oldSize: Dimension, size: Dimension) {
        val xOffset = (size.width - oldSize.width) / 2
        val yOffset = (size.height - oldSize.height) / 2
        // now, move each vertex to be at the new screen center
        while (true) {
            try {
                for (v in getGraph().nodes) {
                    offsetVertex(v, xOffset.toDouble(), yOffset.toDouble())
                }
                break
            } catch (cme: ConcurrentModificationException) {
                Timber.tag(AbstractLayout::class.java.name).e(cme)
            }

        }
    }

    /**
     * Is locked.
     *
     * @param v the v
     * @return the boolean
     */
    override fun isLocked(v: Node): Boolean {
        return dontmove.contains(v)
    }

    /**
     * Sets initializer.
     *
     * @param initializer the initializer
     */
    override fun setInitializer(initializer: Transformer<Node, Point2D>) {
        if (this == initializer) {
            throw IllegalArgumentException("Layout cannot be initialized with itself")
        }
        this.locations = LazyMap.lazyMap<Point2D, Node>(HashMap<Node, Point2D>(), initializer)
        initialized = true
    }

    /**
     * Returns the current size of the visualization space, according to the
     * last call to resize().
     *
     * @return the current size of the screen
     */
    fun getSize(): Dimension {
        return this.size
    }

    /**
     * Returns the Coordinates object that stores the vertex' x and y location.
     *
     * @param v A Vertex that is a part of the Graph being visualized.
     * @return A Coordinates object with x and y locations.
     */
    private fun getCoordinates(v: Node): Point2D? {
        return locations[v]
    }

    /**
     * Transform point 2 d.
     *
     * @param v the v
     * @return the point 2 d
     */
    override fun transform(v: Node): Point2D? {
        return getCoordinates(v)
    }

    /**
     * Offset vertex.
     *
     * @param v       the v
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    private fun offsetVertex(v: Node, xOffset: Double, yOffset: Double) {
        val c = getCoordinates(v)
        c!!.setLocation(c.x + xOffset, c.y + yOffset)
        setLocation(v, c)
    }

    /**
     * Accessor for the graph that represets all vertices.
     *
     * @return the graph that contains all vertices.
     */
    fun getGraph(): Graph {
        return this.graph
    }

    /**
     * Sets location.
     *
     * @param v the picked
     * @param location      the p
     */
    override fun setLocation(v: Node, location: Point2D) {
        val coord = getCoordinates(v)
        coord!!.setLocation(location)
    }

    /**
     * Locks `v` in place if `state` is `true`, otherwise unlocks it.
     *
     * @param v     the v
     * @param state the state
     */
    override fun lock(v: Node, state: Boolean) {
        if (state) {
            dontmove.add(v)
        } else {
            dontmove.remove(v)
        }
    }

}
