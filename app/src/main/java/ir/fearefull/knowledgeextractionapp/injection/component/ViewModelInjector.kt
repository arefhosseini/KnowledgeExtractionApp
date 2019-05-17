package ir.fearefull.knowledgeextractionapp.injection.component

import dagger.Component
import ir.fearefull.knowledgeextractionapp.injection.module.NetworkModule
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationListViewModel
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(postListViewModel: RelationListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}