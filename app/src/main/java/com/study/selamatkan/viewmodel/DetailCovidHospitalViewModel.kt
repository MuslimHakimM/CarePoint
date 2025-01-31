package com.study.selamatkan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.study.selamatkan.data.domain.usecase.HealthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCovidHospitalViewModel @Inject constructor(
    private val detailCovidHospital: HealthUseCase
) : ViewModel() {

    fun dataDetailCovidHospital(
        hospitalId: String
    ) = detailCovidHospital.getDetailCovidHospital(hospitalId).asLiveData()
}