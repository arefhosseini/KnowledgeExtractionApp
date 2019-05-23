package ir.fearefull.knowledgeextractionapp.ui.relation

import androidx.lifecycle.MutableLiveData
import ir.fearefull.knowledgeextractionapp.ui.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.data.model.api.RelationResponse


class RelationViewModel: BaseViewModel<Any?>() {
    private val relationSubject = MutableLiveData<String>()
    private val relationPredicate = MutableLiveData<String>()
    private val relationObject = MutableLiveData<String>()

    fun bind(relationResponse: RelationResponse){
        relationSubject.value = relationResponse.subject
        relationPredicate.value = relationResponse.predicate
        relationObject.value = relationResponse.obj
    }

    fun getRelationSubject(): MutableLiveData<String> {
        return relationSubject
    }

    fun getRelationPredicate(): MutableLiveData<String>{
        return relationPredicate
    }

    fun getRelationObject(): MutableLiveData<String>{
        return relationObject
    }
}