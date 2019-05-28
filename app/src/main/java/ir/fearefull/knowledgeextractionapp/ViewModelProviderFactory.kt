package ir.fearefull.knowledgeextractionapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.graph.GraphManager
import ir.fearefull.knowledgeextractionapp.ui.about.AboutViewModel
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationViewModel
import ir.fearefull.knowledgeextractionapp.ui.splash.SplashViewModel
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject constructor(
    private val dataManager: DataManager,
    private val schedulerProvider: SchedulerProvider,
    private val graphManager: GraphManager) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel(dataManager, schedulerProvider) as T
            modelClass.isAssignableFrom(RelationViewModel::class.java) -> RelationViewModel(dataManager, schedulerProvider, graphManager) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(dataManager, schedulerProvider) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}