package ir.fearefull.knowledgeextractionapp.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun computationScheduler(): Scheduler
    fun uiScheduler(): Scheduler
    fun ioScheduler(): Scheduler
}