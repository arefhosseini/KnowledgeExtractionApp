package ir.fearefull.knowledgeextractionapp.graph.core.layout

import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import ir.fearefull.knowledgeextractionapp.graph.core.bean.Dimension
import ir.fearefull.knowledgeextractionapp.graph.core.bean.RandomLocationTransformer

class FRLayout
/**
 * Creates an instance of size `d` for the specified graph.
 *
 * @param g the g
 * @param d the d
 */(g: Graph, d: Dimension) : AbstractLayout(g, RandomLocationTransformer(d), d) {

    private var temperature: Double = 0.toDouble()

    private var currentIteration: Int = 0

    private var attraction_multiplier = 0.75

    private var attraction_constant: Double = 0.toDouble()

    private var repulsion_multiplier = 0.75

    private var repulsion_constant: Double = 0.toDouble()

    private var max_dimension: Double = 0.toDouble()

    init {
        initialize()
        max_dimension = Math.max(d.height, d.width).toDouble()
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    override fun setSize(size: Dimension?) {
        if (!initialized) {
            setInitializer(RandomLocationTransformer(size!!))
        }
        super.setSize(size)
        max_dimension = Math.max(size!!.height, size.width).toDouble()
    }

    /**
     * Reset void.
     */
    override fun reset() {
        doInit()
    }

    /**
     * Initialize void.
     */
    override fun initialize() {
        doInit()
    }

    /**
     * Do init.
     */
    private fun doInit() {
        val graph = getGraph()
        val d = getSize()
        currentIteration = 0
        temperature = (d.width / 10).toDouble()
        val forceConstant = Math.sqrt((d.height * d.width / graph.nodes.size).toDouble())
        attraction_constant = attraction_multiplier * forceConstant
        repulsion_constant = repulsion_multiplier * forceConstant
    }

}