package com.study.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.study.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProvinceViewModel @Inject constructor(province: HealthUseCase) : ViewModel() {
    val getListProv = province.getListProvinceHome().asLiveData()
}
