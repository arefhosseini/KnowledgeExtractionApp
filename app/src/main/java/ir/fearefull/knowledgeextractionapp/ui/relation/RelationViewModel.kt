package ir.fearefull.knowledgeextractionapp.ui.relation

import io.reactivex.disposables.Disposable
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationResponse
import ir.fearefull.knowledgeextractionapp.graph.GraphManager
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import timber.log.Timber

class RelationViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, var graphManager: GraphManager) :
    BaseViewModel<RelationNavigator>(dataManager, schedulerProvider) {

    val onSearchClickListener: Function1<String, Unit> = this::onSearchClickListener

    init {
        this.graphManager.createGraph()
    }

    private fun onSearchClickListener(text: String) {
        Timber.d(text)
        loadRelations(text)
    }

    private fun loadRelations(text: String) {
        val subscription: Disposable = getDataManager()?.getRelations(text)
            ?.subscribeOn(getSchedulerProvider()?.io())
            ?.observeOn(getSchedulerProvider()?.ui())
            ?.doOnSubscribe {
                getNavigator()?.removeGraph()
                setIsLoading(true) }
            ?.subscribe(
                { result -> onRetrieveRelationListSuccess(result)
                    setIsLoading(false) },
                { throwable -> getNavigator()?.handleError(throwable)
                    setIsLoading(false) })!!

        getCompositeDisposable()?.add(subscription)
    }

    private fun onRetrieveRelationListSuccess(relationResponse: RelationResponse){
        if (relationResponse.relations.nodes.isNotEmpty()) {
            Timber.d("StartingRetrieve")
            this.graphManager.clearGraph()
            Timber.d("StartingRetrieve2")
            /*for (node in relationResponse.relations.nodes) {
                this.graphManager?.createNode(node.label)
            }
            for (edge in relationResponse.relations.edges) {
                this.graphManager?.createEdge(edge.label, edge.from, edge.to)
            }*/
            this.graphManager.createNode("1")
            this.graphManager.createNode("2")
            this.graphManager.createEdge("kir", 0, 1)
            getNavigator()?.createGraph()
        }
        else
            Timber.d("zero")
    }

    fun getNetworkGraph() = this.graphManager.getGraphNetwork()
}