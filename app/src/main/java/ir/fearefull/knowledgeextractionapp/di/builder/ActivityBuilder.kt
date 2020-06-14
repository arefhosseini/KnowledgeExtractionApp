package ir.fearefull.knowledgeextractionapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.fearefull.knowledgeextractionapp.ui.about.AboutFragmentProvider
import ir.fearefull.knowledgeextractionapp.ui.relation.MyCardsFragmentProvider
import ir.fearefull.knowledgeextractionapp.ui.relation.RelationActivity
import ir.fearefull.knowledgeextractionapp.ui.splash.SplashActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        MyCardsFragmentProvider::class,
        AboutFragmentProvider::class
    ])
    internal abstract fun bindRelationActivity(): RelationActivity

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity
}