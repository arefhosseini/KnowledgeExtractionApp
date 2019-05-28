package ir.fearefull.knowledgeextractionapp.graph

import ir.fearefull.knowledgeextractionapp.data.model.api.RelationsResponse
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppGraphManager @Inject constructor(): GraphManager {

    var graph: Graph = Graph()

    override fun createGraph(relationsResponse: RelationsResponse) {
        graph.clear()
        for (node in relationsResponse.relations.nodes) {
            graph.addNode(node.label)
        }
        for (edge in relationsResponse.relations.edges) {
            graph.addEdge(edge.label, edge.from, edge.to)
        }
    }

    override fun getMyGraph(): Graph = this.graph
}