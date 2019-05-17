package ir.fearefull.knowledgeextractionapp.model

import com.squareup.moshi.Json

/**
 * Class which provides a model for relation
 * @constructor Sets all properties of the relation
 * @property relations the list of the relation
 */
data class MyResponse(@field:Json(name = "relations") val relations: List<Relation>)