package com.mani.idonethis.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mani.idonethis.R
import com.mani.idonethis.databinding.FragmentGalleryBinding
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

        binding.teamMembers.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        teamViewModel.teamMutableLiveData.observe(viewLifecycleOwner, Observer {
            binding.teamMembers.adapter = TeamAdapter(it, context!!)
        })

        teamViewModel.getTeam()

        binding.addToTeam.setOnClickListener{
            teamViewModel.addToTeam(binding.newTeamEditText.text.toString())

            Toast.makeText(context!!,"Refreshing the team", Toast.LENGTH_SHORT).show()
            binding.newTeamEditText.text.clear()
            binding.newTeamEditText.clearFocus()
        }

        return binding.root
    }
}