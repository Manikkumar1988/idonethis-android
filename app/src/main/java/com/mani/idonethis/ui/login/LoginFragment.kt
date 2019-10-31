package com.mani.idonethis.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mani.idonethis.R
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mani.idonethis.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )

        binding.viewmodel = loginViewModel
        binding.lifecycleOwner = this
        this.lifecycle.addObserver(loginViewModel)

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        return binding.root
    }
}