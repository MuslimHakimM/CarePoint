package com.study.selamatkan.view.adapter.pager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.study.selamatkan.view.fragment.core.hospital.list.CovidHospitalFragment
import com.study.selamatkan.view.fragment.core.hospital.list.NonCovidHospitalFragment

class HospitalPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> CovidHospitalFragment()
            1 -> NonCovidHospitalFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
}