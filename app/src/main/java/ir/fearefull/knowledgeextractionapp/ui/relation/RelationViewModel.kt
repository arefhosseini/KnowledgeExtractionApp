package ir.fearefull.knowledgeextractionapp.ui.relation

import android.util.Log
import io.reactivex.disposables.Disposable
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationResponse
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.utils.AppLogger
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider

class RelationViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<RelationNavigator>(dataManager, schedulerProvider) {

    val onSearchClickListener: Function1<String, Unit> = this::onSearchClickListener

    private fun onSearchClickListener(text: String) {
        AppLogger.d("text", text)

        loadRelations(text)
    }

    private fun loadRelations(text: String) {
        val subscription: Disposable = getDataManager()?.getRelations(text)
            ?.subscribeOn(getSchedulerProvider()?.io())
            ?.observeOn(getSchedulerProvider()?.ui())
            ?.doOnSubscribe { setIsLoading(true) }
            ?.doOnTerminate { setIsLoading(false) }
            ?.subscribe(
                { result -> onRetrieveRelationListSuccess(result) },
                { throwable -> getNavigator()?.handleError(throwable)})!!

        getCompositeDisposable()?.add(subscription)
    }

    private fun onRetrieveRelationListSuccess(relationResponse: RelationResponse){
        if (relationResponse.relations.nodes.isNotEmpty())
            AppLogger.d(relationResponse.relations.nodes[0].label)
        else
            AppLogger.d("zero")
    }
}