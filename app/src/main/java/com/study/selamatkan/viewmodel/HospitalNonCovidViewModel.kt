package com.study.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.study.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HospitalNonCovidViewModel @Inject constructor(
    private val nonCovidHospital: HealthUseCase
) : ViewModel() {

    fun nonCovidHospital(
        provinceId: String,
        cityId: String
    ) = nonCovidHospital.getListNonCovidHospital(provinceId, cityId).asLiveData()
}