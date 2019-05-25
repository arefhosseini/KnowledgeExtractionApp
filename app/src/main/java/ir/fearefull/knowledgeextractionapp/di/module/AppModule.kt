package ir.fearefull.knowledgeextractionapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import ir.fearefull.knowledgeextractionapp.R
import ir.fearefull.knowledgeextractionapp.data.remote.ApiHelper
import ir.fearefull.knowledgeextractionapp.utils.AppConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import ir.fearefull.knowledgeextractionapp.utils.rx.AppSchedulerProvider
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import ir.fearefull.knowledgeextractionapp.data.AppDataManager
import ir.fearefull.knowledgeextractionapp.data.DataManager

/**
 * Module which provides all required dependencies
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application) = application

    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
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
     * Provides the RelationResponse service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the RelationResponse service implementation.
     */
    @Provides
    @Singleton
    fun provideRelationApi(retrofit: Retrofit): ApiHelper {
        return retrofit.create(ApiHelper::class.java)
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
