package com.study.selamatkan.view.fragment.core.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.selamatkan.R
import com.study.selamatkan.data.source.local.model.ProvinceEntity
import com.study.selamatkan.databinding.FragmentHomeBinding
import com.study.selamatkan.utils.Constant
import com.study.selamatkan.utils.HelpUtil
import com.study.selamatkan.utils.HelpUtil.setStatusBarColor
import com.study.selamatkan.utils.HelpUtil.showProgressBar
import com.study.selamatkan.utils.InternetReceiver
import com.study.selamatkan.view.adapter.ProvinceAdapter
import com.study.selamatkan.viewmodel.ProvinceViewModel
import com.study.selamatkan.vo.Resource
import com.simform.refresh.SSPullToRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var handlerData: Handler

    private val provinceViewModel: ProvinceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized) {
            binding
        } else {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            setAdapter()
            setViewModel()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarColor(requireActivity(), R.color.powder_blue, binding.root, false)

//        binding.imgBurger.setOnClickListener { openNavigationDrawer() }

        binding.etSearch.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    HelpUtil.hideKeyboard(requireActivity())
                    clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val startTime = System.nanoTime()
                    provinceAdapter.filter.filter(newText)

                    provinceAdapter.filter.filter(newText) {
                        val endTime = System.nanoTime()
                        val timeTakenNano = endTime - startTime
                        val timeTakenSeconds = timeTakenNano / 1_000_000_000.0
                        binding.tvTimeTaken.text = String.format("Waktu Pencarian: %.3f s", timeTakenSeconds)
                    }
                    return false
                }
            })
        }

        Calendar.getInstance().also {
            with(binding.tvWeather) {
                when (it.get(Calendar.HOUR_OF_DAY)) {
                    in 0..11 -> text = "Selamat Pagi"
                    in 12..15 -> text = "Selamat Siang"
                    in 16..18 -> text = "Selamat Sore"
                    in 19..24 -> text = "Selamat Malam"
                }
            }
        }
    }

//    private fun openNavigationDrawer() {
//        val drawerLayout: DrawerLayout = requireActivity().findViewById(R.id.drawer_layout)
//        drawerLayout.openDrawer(GravityCompat.START)
//    }

    private fun setAdapter() {
        provinceAdapter = ProvinceAdapter()
        provinceAdapter.setOnItemClick(object : ProvinceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProvinceEntity) {
                Constant.provinceId = data.id
                Constant.provinceName = data.name
                findNavController().navigate(R.id.action_nav_home_to_cityFragment)
            }
        })

        binding.rvProvince.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = provinceAdapter
            setHasFixedSize(true)
        }
    }

    private fun setViewModel() {
        provinceViewModel.getListProv.observe(viewLifecycleOwner) {
            binding.apply {
                with(srlProvince) {
                    setLottieAnimation("loading.json")
                    setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT)
                    setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE)
                    setOnRefreshListener(object : SSPullToRefreshLayout.OnRefreshListener {
                        override fun onRefresh() {
                            val check = HelpUtil.isOnline(requireActivity())
                            if (check) {
                                handlerData = Handler(Looper.getMainLooper())
                                handlerData.postDelayed({
                                    setRefreshing(false)
                                }, 2000)

                                handlerData.postDelayed({
                                    if (it.data.isNullOrEmpty()) {
                                        InternetReceiver().onReceive(requireActivity(), Intent())
                                    } else {
                                        rvProvince.visibility = View.VISIBLE
                                        clNoInternet.visibility = View.GONE
                                    }
                                }, 2350)
                            } else {
                                rvProvince.visibility = View.GONE
                                clNoInternet.visibility = View.VISIBLE
                                setRefreshing(false)
                            }
                        }
                    })
                }

                when (it) {
                    is Resource.Loading -> progressBar.showProgressBar(true)
                    is Resource.Success -> {
                        progressBar.showProgressBar(false)
                        provinceAdapter.setProvinceAdapter(it.data!!)
                    }
                    is Resource.Error -> {
                        progressBar.showProgressBar(false)
                        clNoInternet.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}