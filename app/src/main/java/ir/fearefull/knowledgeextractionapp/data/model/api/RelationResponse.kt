package ir.fearefull.knowledgeextractionapp.data.model.api

import com.squareup.moshi.Json

class RelationResponse(@field:Json(name = "relations") var relations: List<Relation>) {

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
}

