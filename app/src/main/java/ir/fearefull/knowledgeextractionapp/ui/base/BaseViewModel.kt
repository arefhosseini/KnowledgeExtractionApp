package ir.fearefull.knowledgeextractionapp.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ir.fearefull.knowledgeextractionapp.data.DataManager
import ir.fearefull.knowledgeextractionapp.utils.rx.SchedulerProvider
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(dataManager: DataManager, schedulerProvider: SchedulerProvider): ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    private val isLoading = ObservableBoolean()

    private var schedulerProvider: SchedulerProvider? = null

    private var dataManager: DataManager? = null

    private var navigator: WeakReference<N>? = null

    init {
        this.schedulerProvider = schedulerProvider
        this.dataManager = dataManager
        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        compositeDisposable?.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable() = compositeDisposable

    fun getIsLoading() = isLoading

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getNavigator() = navigator?.get()

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getDataManager() = dataManager

    fun getSchedulerProvider() = schedulerProvider
}