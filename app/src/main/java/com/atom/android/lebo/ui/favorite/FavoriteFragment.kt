package com.atom.android.lebo.ui.favorite

import android.app.Dialog
import androidx.navigation.fragment.findNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.data.local.FavoriteEntityLocal
import com.atom.android.lebo.databinding.FragmentFavoriteBinding
import com.atom.android.lebo.utils.extensions.hasItem
import com.atom.android.lebo.utils.extensions.openDialogQuestion
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    override val viewModel by viewModel<FavoriteViewModel>()

    private val bookAdapter by lazy {
        ListAdapterFavorite(::onClick, ::onLongClick)
    }

    override fun initData() {
        // late impl
    }

    override fun initView() {
        viewModel.getFavoriteItem()
        binding.recyclerViewFavoriteBook.adapter = bookAdapter
        viewModel.favoriteItem.observe(viewLifecycleOwner) {
            bookAdapter.submitList(it.hasItem())
        }
    }

    override fun initEvent() {
        // late impl
    }

    private fun onClick(book: FavoriteEntityLocal) {
        val action = FavoriteFragmentDirections
            .actionNavigationFavoriteToNavigationDetail(book.idBook.toString())
        findNavController().navigate(action)
    }

    private fun onLongClick(book: FavoriteEntityLocal) {
        context?.let {
            Dialog(it).openDialogQuestion(
                getString(R.string.text_confirm),
                getString(R.string.text_question_remove)
            ) {
                viewModel.deleteFavoriteItem(book)
            }
        }
    }
}
