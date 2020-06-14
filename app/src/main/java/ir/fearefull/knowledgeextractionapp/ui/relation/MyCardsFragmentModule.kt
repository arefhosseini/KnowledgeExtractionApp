package ir.fearefull.knowledgeextractionapp.ui.relation

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class MyCardsFragmentModule {

    @Provides
    fun provideMyCardAdapter(): MyCardAdapter {
        return MyCardAdapter()
    }

    @Provides
    fun provideLinearLayoutManager(fragmentMy: MyCardsFragment): LinearLayoutManager {
        return LinearLayoutManager(fragmentMy.activity)
    }
}