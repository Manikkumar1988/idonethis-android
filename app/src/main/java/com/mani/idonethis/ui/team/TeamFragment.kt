package com.mani.idonethis.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mani.idonethis.R
import com.mani.idonethis.databinding.FragmentGalleryBinding
import com.mani.idonethis.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamFragment : Fragment() {

    private val teamViewModel: TeamViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentGalleryBinding>(
            inflater,
            R.layout.fragment_gallery, container, false
        )

        teamViewModel.getTeam()

        return binding.root
    }
}