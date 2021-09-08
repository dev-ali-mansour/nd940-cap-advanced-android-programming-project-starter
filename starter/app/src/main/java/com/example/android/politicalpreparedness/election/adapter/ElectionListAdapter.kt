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
    var elections: List<Election> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = elections.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        val binding =
            ItemElectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ElectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.election = elections[position]
            it.clickListener = clickListener
        }
    }
}

class ElectionViewHolder(val viewDataBinding: ItemElectionBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root)

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {

    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean =
        oldItem == newItem
}

class ElectionListener(val block: (Election) -> Unit) {
    fun onClick(election: Election) = block(election)
}