package ir.fearefull.knowledgeextractionapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.data.AppDataManager
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.data.remote.ApiHelper
import ir.fearefull.knowledgeextractionapp.utils.AppConstants.BASE_URL
import ir.fearefull.knowledgeextractionapp.utils.rx.AppSchedulerProvider
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

/**
 * Module which provides all required dependencies
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/b_nazanin.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    /**
     * Provides the RelationsResponse service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the RelationsResponse service implementation.
     */
    @Provides
    @Singleton
    fun provideApiHelper(retrofit: Retrofit): ApiHelper = retrofit.create(ApiHelper::class.java)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
