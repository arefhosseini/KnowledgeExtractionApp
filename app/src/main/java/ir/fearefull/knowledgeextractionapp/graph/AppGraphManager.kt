package ir.fearefull.knowledgeextractionapp.graph

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import giwi.org.networkgraph.beans.NetworkGraph
import giwi.org.networkgraph.beans.Vertex
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.graph.custom.MyNetworkGraph
import ir.fearefull.knowledgeextractionapp.graph.remote.GraphHelper
import net.xqhs.graphs.graph.Node
import net.xqhs.graphs.graph.SimpleEdge
import net.xqhs.graphs.graph.SimpleNode
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppGraphManager @Inject constructor(
    private val context: Context,
    private val graphHelper: GraphHelper): GraphManager {

    lateinit var graph: MyNetworkGraph

    init {
        createGraph()
    }

    override fun createRawNode(label: String): SimpleNode = graphHelper.createRawNode(label)

    override fun createRawEdge(label: String, from: SimpleNode, to: SimpleNode): SimpleEdge {
        return graphHelper.createRawEdge(label, from, to)
    }

    override fun createRawVertex(node: SimpleNode, drawable: Drawable): Vertex {
        return graphHelper.createRawVertex(node, drawable)
    }

    override fun createGraph() {
        val graph = MyNetworkGraph(context)
        Timber.i("KIR")
        this.graph = graph
    }

    override fun createNode(label: String) {
        val node = SimpleNode(label)
        graph.addVertex(node)
        this.graph.addNode(node)
    }

    override fun createEdge(label: String, from: Int, to: Int) {
        val fromNode: Node = graph.nodes.elementAt(from)!!
        val toNode: Node = graph.nodes.elementAt(to)!!
        Timber.d(fromNode.label)
        Timber.d(toNode.label)
        this.graph.addEdge(SimpleEdge(fromNode, toNode, label))
        Timber.d("createEdge")
    }

    override fun clearGraph() {
        this.graph.clearGraph()
    }

    override fun getGraphNetwork(): NetworkGraph = this.graph
}