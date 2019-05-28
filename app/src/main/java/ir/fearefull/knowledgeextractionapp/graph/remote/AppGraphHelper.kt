package ir.fearefull.knowledgeextractionapp.graph.remote

import android.graphics.drawable.Drawable
import dagger.Provides
import giwi.org.networkgraph.beans.Vertex
import net.xqhs.graphs.graph.SimpleEdge
import net.xqhs.graphs.graph.SimpleNode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppGraphHelper @Inject constructor() : GraphHelper {
    override fun createRawNode(label: String): SimpleNode = SimpleNode(label)
    override fun createRawVertex(node: SimpleNode, drawable: Drawable) = Vertex(node, drawable)
    override fun createRawEdge(label: String, from: SimpleNode, to: SimpleNode): SimpleEdge = SimpleEdge(from, to, label)

}