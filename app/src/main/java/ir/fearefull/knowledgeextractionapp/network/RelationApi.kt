package ir.fearefull.knowledgeextractionapp.network

import io.reactivex.Observable
import ir.fearefull.knowledgeextractionapp.model.MyResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * The interface which provides methods to get result of webservices
 */
interface RelationApi {
    /**
     * Get the list of the relations from the API
     */
    @POST("/text/")
    fun getRelations(@Body request: RequestBody): Observable<MyResponse>
}