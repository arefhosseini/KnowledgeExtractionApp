package ir.fearefull.knowledgeextractionapp.graph

import ir.fearefull.knowledgeextractionapp.data.model.api.RelationsResponse
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph

interface GraphManager {
    fun createGraph(relationsResponse: RelationsResponse)
    fun getMyGraph(): Graph
}