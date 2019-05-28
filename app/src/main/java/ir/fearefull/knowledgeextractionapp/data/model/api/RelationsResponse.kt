package ir.fearefull.knowledgeextractionapp.data.model.api

import com.squareup.moshi.Json

data class RelationsResponse(
    @Json(name = "relations") val relations: RelationResponse
)

data class RelationResponse(
    @Json(name = "nodes") var nodes: List<NodeResponse>,
    @Json(name = "edges") var edges: List<EdgeResponse>
)

/**
 * Class which provides a model for Node
 * @constructor Sets all properties of the Node
 * @property id the id of the Node
 * @property label the label of the Node
 */

data class NodeResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "label") val label: String)

/**
 * Class which provides a model for Edge
 * @constructor Sets all properties of the Edge
 * @property id the subject of the Edge
 * @property label the predicate of the Edge
 * @property from the predicate of the Edge
 * @property to the obj of the Edge
 */

data class EdgeResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "label") val label: String,
    @Json(name = "from") val from: Int,
    @Json(name = "to") val to: Int)
