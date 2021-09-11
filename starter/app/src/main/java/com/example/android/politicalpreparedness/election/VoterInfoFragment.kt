package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class VoterInfoFragment : Fragment() {
    private lateinit var binding: FragmentVoterInfoBinding
    private val args: VoterInfoFragmentArgs by navArgs()
    private val voterInfoViewModel: VoterInfoViewModel by lazy {
        val viewModelFactory = VoterInfoViewModelFactory(requireActivity().application)
        ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)
    }
    private lateinit var snackBar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVoterInfoBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = voterInfoViewModel
        val election = args.argElection

        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */

        voterInfoViewModel.voterInfoAvailable.observe(viewLifecycleOwner, { isAvailable ->
            if (!isAvailable) {
                Timber.e(getString(R.string.elections_details_error))
                snackBar.show()
            }
        })

        voterInfoViewModel.url.observe(viewLifecycleOwner, { url ->
            url?.let { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it))) }
        })

        voterInfoViewModel.apply {
            getElection(election.id)
            getVoterInfo(election)
        }

        /**
         * Handle views visibility, save button UI state and clicks was made in layout file
         * using Data Binding with the ViewModel
         */

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snackBar = Snackbar.make(
            binding.mainLayout,
            getString(R.string.elections_details_error),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(android.R.string.ok) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        snackBar.dismiss()
    }
}