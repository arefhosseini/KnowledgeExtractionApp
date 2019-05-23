package ir.fearefull.knowledgeextractionapp.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider: SchedulerProvider {
    override fun computationScheduler() = Schedulers.computation()

    override fun ioScheduler() = Schedulers.io()

    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}