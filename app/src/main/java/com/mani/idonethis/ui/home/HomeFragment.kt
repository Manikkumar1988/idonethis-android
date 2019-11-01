package com.mani.idonethis.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mani.idonethis.R
import com.mani.idonethis.databinding.FragmentHomeBinding
import com.mani.idonethis.databinding.FragmentLoginBinding
import com.mani.idonethis.ui.home.model.DoneItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home, container, false
        )

        binding.addButton.setOnClickListener{
            homeViewModel.addTodo(DoneItem(binding.todoEditText.text.toString() ,0))
            Toast.makeText(context!!,"Added",Toast.LENGTH_SHORT).show()
        }

        (activity as? AppCompatActivity)?.supportActionBar?.show()

        return binding.root
    }
}