package ir.fearefull.knowledgeextractionapp.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun computation(): Scheduler
    fun ui(): Scheduler
    fun io(): Scheduler
}