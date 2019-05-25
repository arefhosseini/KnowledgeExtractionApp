package ir.fearefull.knowledgeextractionapp.di.component

import dagger.Component
import ir.fearefull.knowledgeextractionapp.App
import ir.fearefull.knowledgeextractionapp.di.module.AppModule
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
import android.app.Application
import dagger.BindsInstance
import ir.fearefull.knowledgeextractionapp.di.builder.ActivityBuilder

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}