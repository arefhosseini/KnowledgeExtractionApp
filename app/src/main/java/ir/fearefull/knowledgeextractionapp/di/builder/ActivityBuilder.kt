package ir.fearefull.knowledgeextractionapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationListActivity
import ir.fearefull.knowledgeextractionapp.ui.splash.SplashActivity


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [AboutFragmentProvider::class])
    internal abstract fun bindMainActivity(): RelationListActivity

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}