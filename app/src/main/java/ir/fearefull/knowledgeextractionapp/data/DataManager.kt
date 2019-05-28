package ir.fearefull.knowledgeextractionapp.data

import io.reactivex.Observable
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationsResponse
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import ir.fearefull.knowledgeextractionapp.data.remote.ApiHelper
import okhttp3.RequestBody

interface DataManager: ApiHelper {
    fun getRelations(text: String): Observable<RelationsResponse>
    fun createRequestBody(text: String): RequestBody
}