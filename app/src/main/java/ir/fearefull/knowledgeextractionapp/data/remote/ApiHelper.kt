package ir.fearefull.knowledgeextractionapp.data.remote

import io.reactivex.Observable
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationsResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * The interface which provides methods to get result of webservices
 */
interface ApiHelper {
    /**
     * Get the list of the relationResponses from the API
     */
    @POST("/text/")
    fun getData(@Body request: RequestBody): Observable<RelationsResponse>
}