package ir.fearefull.knowledgeextractionapp.base

import android.arch.lifecycle.ViewModel
import ir.fearefull.knowledgeextractionapp.injection.component.DaggerViewModelInjector
import ir.fearefull.knowledgeextractionapp.injection.component.ViewModelInjector
import ir.fearefull.knowledgeextractionapp.injection.module.NetworkModule
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationListViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RelationListViewModel -> injector.inject(this)
        }
    }
}