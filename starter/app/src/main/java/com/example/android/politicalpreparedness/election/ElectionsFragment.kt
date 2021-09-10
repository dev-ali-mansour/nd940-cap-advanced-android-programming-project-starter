package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment : Fragment() {
    private lateinit var binding: FragmentElectionBinding
    private val electionsViewModel: ElectionsViewModel by lazy {
        val viewModelFactory = ElectionsViewModelFactory(requireActivity().application)
        ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = electionsViewModel

        electionsViewModel.navigateToElection.observe(viewLifecycleOwner, { election ->
            election?.let {
                findNavController().navigate(
                    ElectionsFragmentDirections
                        .actionElectionsFragmentToVoterInfoFragment(it)
                )

                electionsViewModel.navigateToElectionDone()
            }
        })

        val upcomingAdapter = ElectionListAdapter(ElectionListener { election ->
            electionsViewModel.navigateToElection(election)
        })
        val savedAdapter = ElectionListAdapter(ElectionListener { election ->
            electionsViewModel.navigateToElection(election)
        })

        binding.upcomingRecyclerView.adapter = upcomingAdapter
        binding.savedRecyclerView.adapter = savedAdapter

        electionsViewModel.upcomingElections.observe(viewLifecycleOwner) { elections ->
            upcomingAdapter.submitList(elections)
        }

        electionsViewModel.savedElections.observe(viewLifecycleOwner) { elections ->
            savedAdapter.submitList(elections)
        }

        return binding.root
    }
}