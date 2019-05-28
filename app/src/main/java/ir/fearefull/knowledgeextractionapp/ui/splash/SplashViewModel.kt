package ir.fearefull.knowledgeextractionapp.ui.splash

import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.utils.AppConstants.SPLASH_SCREEN_DURATION

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun getSplashScreenDuration() = SPLASH_SCREEN_DURATION

    fun scheduleSplashScreen() {
        getNavigator()?.scheduleSplashScreen()
    }

    fun openRelationActivity() {
        getNavigator()?.openRelationActivity()
    }
}