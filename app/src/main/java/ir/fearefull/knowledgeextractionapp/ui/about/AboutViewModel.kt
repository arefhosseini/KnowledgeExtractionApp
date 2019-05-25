package ir.fearefull.knowledgeextractionapp.ui.about

import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel

class AboutViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AboutNavigator>(dataManager, schedulerProvider) {

    fun onNavBackClick() {
        getNavigator()?.goBack()
    }
}