package ir.fearefull.knowledgeextractionapp.ui.relation

import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.data.remote.ApiHelper
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class RelationListViewModel: BaseViewModel<Any?>(){
    @Inject
    lateinit var apiHelper: ApiHelper

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { onLoadRelations() }
    val onSearchClickListener: Function1<String, Unit> = this::onSearchClickListener
    val relationListAdapter: RelationListAdapter = RelationListAdapter()

    private var text: String = ""

    private fun onLoadRelations() {
        subscription = apiHelper.getRelations(createRequestBody())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRelationListStart() }
            .doOnTerminate { onRetrieveRelationListFinish() }
            .subscribe(
                { result -> onRetrieveRelationListSuccess(result) },
                this::onRetrieveRelationListError
            )
    }

    private fun onSearchClickListener(text: String) {
        this.text = text
        Log.d("text", text)
        onLoadRelations()
    }

    private fun onRetrieveRelationListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRelationListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRelationListSuccess(myResponse: MyResponse){
        relationListAdapter.updateRelationList(myResponse.relationResponses)
    }

    private fun onRetrieveRelationListError(error: Throwable){
        Log.d(TAG, error.localizedMessage)
        errorMessage.value = R.string.post_error
    }

    private fun createRequestBody(): RequestBody{
        val json = JSONObject()
        json.put("text", this.text)

        return RequestBody.create(MediaType.parse("application/json"), json.toString())
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}