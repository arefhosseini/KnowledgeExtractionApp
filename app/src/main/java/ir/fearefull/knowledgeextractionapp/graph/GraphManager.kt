package ir.fearefull.knowledgeextractionapp.graph

import giwi.org.networkgraph.beans.NetworkGraph
import ir.fearefull.knowledgeextractionapp.graph.remote.GraphHelper

interface GraphManager: GraphHelper {
    fun getGraphNetwork(): NetworkGraph
    fun createGraph()
    fun createEdge(label: String, from: Int, to: Int)
    fun createNode(label: String)
    fun clearGraph()
}