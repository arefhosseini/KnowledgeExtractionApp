package ir.fearefull.knowledgeextractionapp

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ir.fearefull.knowledgeextractionapp.di.component.DaggerAppComponent

import javax.inject.Inject
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import ir.fearefull.knowledgeextractionapp.utils.AppLogger

class App: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var calligraphyConfig: CalligraphyConfig

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        AppLogger.init()

        CalligraphyConfig.initDefault(calligraphyConfig)
    }

}