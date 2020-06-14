package ir.fearefull.knowledgeextractionapp.ui.relation

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MyCardsFragmentProvider {
    @ContributesAndroidInjector(modules = [
        MyCardsFragmentModule::class
    ])
    abstract fun provideMyCardsFragmentProvider(): MyCardsFragment?
}
