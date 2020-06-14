package ir.fearefull.knowledgeextractionapp.ui.relation

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationsResponse
import ir.fearefull.knowledgeextractionapp.data.model.other.Graph
import ir.fearefull.knowledgeextractionapp.ui.about.AboutFragment
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import timber.log.Timber

class RelationViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<RelationNavigator>(dataManager, schedulerProvider) {

    var searchText: String? = null
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val onSearchClickListener: Function1<String, Unit> = this::onSearchClickListener
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadRelations(searchText!!) }


    private fun onSearchClickListener(text: String) {
        searchText = text
        loadRelations(text)
    }

    private fun loadRelations(text: String) {
        val subscription: Disposable = getDataManager()?.getRelations(text)
            ?.subscribeOn(getSchedulerProvider()?.io())
            ?.observeOn(getSchedulerProvider()?.ui())
            ?.doOnSubscribe { onRetrieveRelationsStart() }
            ?.doOnTerminate { onRetrieveRelationsFinish() }
            ?.subscribe(
                { result -> onRetrieveRelationsSuccess(result) },
                this::onRetrieveRelationsError
            )!!

        getCompositeDisposable()?.add(subscription)
    }

    fun onShowFragmentInfo() {
        getNavigator()?.showAboutFragment()
    }

    private fun onRetrieveRelationsStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
        getNavigator()?.removeGraph()
    }

    private fun onRetrieveRelationsFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRelationsSuccess(relationsResponse: RelationsResponse){
    }

    private fun onRetrieveRelationsError(error: Throwable){
        errorMessage.value = R.string.error_no_internet
    }
}