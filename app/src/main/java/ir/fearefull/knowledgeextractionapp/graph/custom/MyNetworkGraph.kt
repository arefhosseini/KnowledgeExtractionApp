package ir.fearefull.knowledgeextractionapp.graph.custom

import android.content.Context
import androidx.core.content.ContextCompat
import giwi.org.networkgraph.beans.NetworkGraph
import giwi.org.networkgraph.beans.Vertex
import ir.fearefull.knowledgeextractionapp.R
import net.xqhs.graphs.graph.SimpleNode

class MyNetworkGraph(var context: Context): NetworkGraph() {
    var edgeStrokeWidth = 20

    init {
        defaultColor = ContextCompat.getColor(context, R.color.default_graph)
        edgeColor = ContextCompat.getColor(context, R.color.graph_edge)
        nodeColor = ContextCompat.getColor(context, R.color.graph_node)
        nodeBgColor = ContextCompat.getColor(context, R.color.graph_node_background)
    }

    fun addVertex(node: SimpleNode) {
        vertex.add(Vertex(node, ContextCompat.getDrawable(context, R.mipmap.avatar)))
    }

    fun clearGraph() {
        this.nodes.clear()
        this.vertex.clear()
        this.edges.clear()
    }
}