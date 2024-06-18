package com.ht.bookery.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ht.bookery.R
import com.ht.bookery.databinding.FragmentUserBinding
import com.ht.bookery.utils.ApiResponse
import com.ht.bookery.utils.UserManager
import com.ht.bookery.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    @Inject lateinit var userManager: UserManager

    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("UserFragment onCreateView")
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            userManager.getFromDataStore().collect {
                binding.textNotifications.text = "${it.accessToken} \n ${it.refreshToken}"
            }
        }

        userViewModel.userInfoResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> println("userInfoResponse Fail ... ")
                is ApiResponse.Loading -> println("userInfoResponse Loading ... ")
                is ApiResponse.Success -> {
                    println("userInfoResponse Load Successfully ${it.data.firstName}")
                    Toast.makeText(context, it.data.username + " " + it.data.firstName, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        println("UserFragment onDestroyView")
        super.onDestroyView()
    }
}