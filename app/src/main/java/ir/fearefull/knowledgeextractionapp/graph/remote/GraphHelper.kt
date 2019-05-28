package ir.fearefull.knowledgeextractionapp.graph.remote

import android.graphics.drawable.Drawable
import giwi.org.networkgraph.beans.Vertex
import net.xqhs.graphs.graph.SimpleEdge
import net.xqhs.graphs.graph.SimpleNode

interface GraphHelper {
    fun createRawNode(label: String): SimpleNode
    fun createRawVertex(node: SimpleNode, drawable: Drawable): Vertex
    fun createRawEdge(label: String, from: SimpleNode, to: SimpleNode): SimpleEdge
}