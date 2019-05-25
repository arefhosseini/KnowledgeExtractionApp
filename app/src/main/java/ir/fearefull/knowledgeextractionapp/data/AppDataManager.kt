package ir.fearefull.knowledgeextractionapp.data

import io.reactivex.Observable
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationResponse
import ir.fearefull.knowledgeextractionapp.data.remote.ApiHelper
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataManager @Inject constructor(
    private val apiHelper: ApiHelper) : DataManager {
    override fun getData(request: RequestBody): Observable<RelationResponse> = apiHelper.getData(request)

    override fun getRelations(text: String): Observable<RelationResponse> {
        return apiHelper.getData(createRequestBody(text))
    }

    override fun createRequestBody(text: String): RequestBody {
        val json = JSONObject()
        json.put("text", text)
        return RequestBody.create(MediaType.parse("application/json"), json.toString())
    }


}