package com.ht.bookery.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ht.bookery.databinding.FragmentHomeBinding
import com.ht.bookery.utils.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("HomeFragment onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.txtHello
        homeViewModel.userInfoResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    textView.text = "Hello, ${it.data?.firstName}"
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        println("onDestroyView")
        super.onDestroyView()
        _binding = null
    }
}