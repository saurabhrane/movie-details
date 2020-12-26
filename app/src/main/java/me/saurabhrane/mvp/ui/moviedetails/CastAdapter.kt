package me.saurabhrane.mvp.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import me.saurabhrane.mvp.base.BaseAdapter
import me.saurabhrane.mvp.data.remote.Cast
import me.saurabhrane.mvp.databinding.ItemCastBinding

/**
 * Adapter class to inflate cast items in Movie Details Screen
 */
class CastAdapter : BaseAdapter<Cast>(DIFF_CALLBACK) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = CastViewModel()
        mBinding.vm = viewModel
        return mBinding

    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ItemCastBinding) {
            binding.vm?.item?.set(getItem(position))
            binding.executePendingBindings()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem == newItem
        }
    }
}