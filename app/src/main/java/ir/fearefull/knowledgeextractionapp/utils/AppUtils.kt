package ir.fearefull.knowledgeextractionapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.reactivex.Observable
import ir.fearefull.knowledgeextractionapp.data.model.other.MyCard
import java.util.*

class AppUtils {

    companion object {
        fun hideSoftKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getMyCards(): Observable<List<MyCard>> {
            return Observable.defer {
                val list: MutableList<MyCard> =
                    ArrayList<MyCard>()

                list.add(MyCard("NAME1", "DESCRIPTION", "https://i.picsum.photos/id/518/536/354.jpg?hmac=p9kqSm12L1N_V4ye22J8XTUD_FZEaV-9nml2aRHH1dc", null))
                list.add(MyCard("NAME2", "DESCRIPTION", "https://i.picsum.photos/id/518/536/354.jpg?hmac=p9kqSm12L1N_V4ye22J8XTUD_FZEaV-9nml2aRHH1dc", null))
                list.add(MyCard("NAME3", "DESCRIPTION", "https://i.picsum.photos/id/518/536/354.jpg?hmac=p9kqSm12L1N_V4ye22J8XTUD_FZEaV-9nml2aRHH1dc", null))
                list.add(MyCard("NAME4", "DESCRIPTION", "https://i.picsum.photos/id/518/536/354.jpg?hmac=p9kqSm12L1N_V4ye22J8XTUD_FZEaV-9nml2aRHH1dc", null))
                list.add(MyCard("NAME5", "DESCRIPTION", "https://i.picsum.photos/id/518/536/354.jpg?hmac=p9kqSm12L1N_V4ye22J8XTUD_FZEaV-9nml2aRHH1dc", null))

                Observable.just<List<MyCard>>(list)
            }
        }
    }
}