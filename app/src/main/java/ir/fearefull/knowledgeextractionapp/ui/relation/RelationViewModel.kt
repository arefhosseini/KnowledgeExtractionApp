package ir.fearefull.knowledgeextractionapp.ui.relation

import android.arch.lifecycle.MutableLiveData
import ir.fearefull.knowledgeextractionapp.base.BaseViewModel
import ir.fearefull.knowledgeextractionapp.model.Relation


class RelationViewModel: BaseViewModel() {
    private val relationSubject = MutableLiveData<String>()
    private val relationPredicate = MutableLiveData<String>()
    private val relationObject = MutableLiveData<String>()

    fun bind(relation: Relation){
        relationSubject.value = relation.subject
        relationPredicate.value = relation.predicate
        relationObject.value = relation.obj
    }

    fun getRelationSubject():MutableLiveData<String>{
        return relationSubject
    }

    fun getRelationPredicate():MutableLiveData<String>{
        return relationPredicate
    }

    fun getRelationObject():MutableLiveData<String>{
        return relationObject
    }
}