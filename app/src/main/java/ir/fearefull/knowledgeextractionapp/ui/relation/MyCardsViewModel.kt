package ir.fearefull.knowledgeextractionapp.ui.relation

import androidx.lifecycle.MutableLiveData
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.utils.AppUtils
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import timber.log.Timber

class MyCardsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MyCardsNavigator>(dataManager, schedulerProvider) {

    var myCardItemsLiveData: MutableLiveData<List<MyCard>> = MutableLiveData()

    init {
        myCardItemsLiveData.value = ArrayList()
        getCompositeDisposable()!!.add(
            AppUtils.getMyCards()
                .subscribeOn(getSchedulerProvider()!!.io())
                .observeOn(getSchedulerProvider()!!.ui())
                .subscribe(myCardItemsLiveData::postValue, Timber::e)
        )
    }
}