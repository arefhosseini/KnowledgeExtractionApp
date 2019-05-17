package ir.fearefull.knowledgeextractionapp.model

import com.squareup.moshi.Json

/**
 * Class which provides a model for relation
 * @constructor Sets all properties of the relation
 * @property subject the subject of the relation
 * @property predicate the predicate of the relation
 * @property obj the obj of the relation
 */
data class Relation(@field:Json(name = "subject") val subject: String,
                    @field:Json(name = "predicate") val predicate: String,
                    @field:Json(name = "object") val obj: String)