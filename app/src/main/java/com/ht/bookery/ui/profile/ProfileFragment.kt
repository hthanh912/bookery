package com.ht.bookery.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ht.bookery.R
import com.ht.bookery.databinding.FragmentProfileBinding
import com.ht.bookery.ui.login.LoginFragment
import com.ht.bookery.ui.user.UserFragment
import com.ht.bookery.utils.ApiResponse
import com.ht.bookery.utils.UserManager
import com.ht.bookery.utils.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject lateinit var userManager: UserManager

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("ProfileFragment onCreateView")
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            println("onViewCreated " + userManager.getFromDataStore().first().accessToken)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            userManager.getFromDataStore().collect {
                println("***** collect *****")
                val fm = activity?.supportFragmentManager
                if (fm != null) {
                    println("fm != null")
                }
                if (it.accessToken.isNotEmpty()) {
                    fm?.beginTransaction()?.replace(R.id.profile_container, UserFragment(), "UserFragment")?.commit()
                } else {
                    fm?.beginTransaction()?.replace(R.id.profile_container, LoginFragment(), "LoginFragment")?.commit()
                }
            }
        }
    }

}