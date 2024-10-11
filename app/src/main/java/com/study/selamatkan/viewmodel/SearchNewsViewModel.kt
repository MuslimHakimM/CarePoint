package com.study.selamatkan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.study.selamatkan.data.domain.model.SearchNews
import com.study.selamatkan.data.domain.usecase.HealthUseCase
import com.study.selamatkan.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsUseCase: HealthUseCase
) : ViewModel() {
    fun getNewsSearch(query: String): LiveData<Resource<List<SearchNews>>> {
        return newsUseCase.getNewsSearch(query).asLiveData()
    }
}