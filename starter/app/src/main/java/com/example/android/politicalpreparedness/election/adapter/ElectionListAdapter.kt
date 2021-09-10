package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder =
        ElectionViewHolder.from(parent)

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }
}

class ElectionViewHolder(val binding: ItemElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: ElectionListener, item: Election) {
        binding.election = item
        binding.electionNameTxt.text = item.name
        binding.electionDateTxt.text = item.electionDay.toString()
        binding.clickListener = clickListener

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemElectionBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {

    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean =
        oldItem.electionDay == newItem.electionDay && oldItem.name == newItem.name
}

class ElectionListener(val block: (Election) -> Unit) {
    fun onClick(election: Election) = block(election)
}