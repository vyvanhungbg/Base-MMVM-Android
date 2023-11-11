package com.atom.android.lebo.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atom.android.lebo.BuildConfig
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseFragment
import com.atom.android.lebo.data.local.CartEntityLocal
import com.atom.android.lebo.databinding.FragmentDetailBinding
import com.atom.android.lebo.model.Book
import com.atom.android.lebo.ui.cart.CartViewModel
import com.atom.android.lebo.ui.detail.adapter.ListAdapterAuthorSmall
import com.atom.android.lebo.ui.detail.adapter.ListAdapterBookVertical
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.actionCart
import com.atom.android.lebo.utils.extensions.convertStrToMoney
import com.atom.android.lebo.utils.extensions.hasItem
import com.atom.android.lebo.utils.extensions.loadImage
import com.atom.android.lebo.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override val viewModel by viewModel<DetailViewModel>()

    private val cartViewModel by activityViewModel<CartViewModel>()

    private val authorAdapter by lazy { ListAdapterAuthorSmall({}) }

    private val bookRelateAdapter by lazy {
        ListAdapterBookVertical(::onClick)
    }

    private val safeArgs: DetailFragmentArgs by navArgs()

    private var itemBook: Book? = null

    override fun initData() {
        arguments?.let {
            try {
                viewModel.getBook(safeArgs.bundleBook.toInt())
                viewModel.getBookRelate(safeArgs.bundleBook.toInt())
                viewModel.getFavoriteItem(safeArgs.bundleBook)
                cartViewModel.getCartLocalBooks()
            } catch (ex: NumberFormatException) {
                context?.showToast(R.string.message_id_book_invalid)
                findNavController().popBackStack()
            }
        }
    }

    override fun initView() {
        binding.recyclerViewAnotherBook.adapter = bookRelateAdapter
        binding.recyclerViewAnotherBook.animation = null
        viewModel.apply {
            book.observe(viewLifecycleOwner) {
                itemBook = it
                it?.let {
                    binding.apply {
                        imageViewItem.loadImage(Uri.parse(it.image))
                        textViewNameItem.text = it.title
                        textViewPriceItem.text = it.price.toString().convertStrToMoney()
                        textViewDescription.text = it.description
                        textViewGenre.text = it.getAllNameGenres()
                        recyclerViewAuthor.adapter = authorAdapter
                        textViewLanguage.text = it.language
                    }
                }
                authorAdapter.submitList(it?.bookAuthors)
            }

            favoriteItem.observe(viewLifecycleOwner) {
                if (it == null) {
                    binding.imageViewFavorite.setImageDrawable(
                        context?.getDrawable(R.drawable.ic_baseline_favorite_border_green_24)
                    )
                } else {
                    binding.imageViewFavorite.setImageDrawable(
                        context?.getDrawable(R.drawable.ic_baseline_favorite_green_24)
                    )
                }
            }

            bookRelative.observe(viewLifecycleOwner) {
                bookRelateAdapter.submitList(it.hasItem())
            }
        }

        cartViewModel.apply {

            cart.observe(viewLifecycleOwner) {
                val totalItem = it?.sumOf { item -> item.amount } ?: Constant.DEFAULT.ITEM_CART
                val amountItem =
                    it?.find { item -> item.idBook == safeArgs.bundleBook.toInt() }?.amount
                        ?: Constant.DEFAULT.ITEM_CART
                binding.btnAmount.number = amountItem.toString()
                binding.textViewAmountItemInCart.text = totalItem.toString()
            }

            books.observe(viewLifecycleOwner) {
                val totalItem = it?.sumOf { it.amount } ?: Constant.DEFAULT.ITEM_CART
                binding.textViewAmountItemInCart.text = totalItem.toString()
            }
        }
    }

    override fun initEvent() {

        binding.apply {
            textViewDescriptionReadMore.setOnClickListener {
                if (binding.textViewDescription.maxLines == Constant.EXPAND_MAX_LINE) {
                    binding.textViewDescription.maxLines = Constant.COLLAPSE_MAX_LINE
                    binding.textViewDescriptionReadMore.text =
                        context?.getText(R.string.text_view_more)
                } else {
                    binding.textViewDescription.maxLines = Constant.EXPAND_MAX_LINE
                    binding.textViewDescriptionReadMore.text = context?.getText(R.string.text_close)
                }
            }

            imageViewBack.setOnClickListener {
                findNavController().popBackStack()
            }

            imageViewShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, BuildConfig.BASE_URL + safeArgs.bundleBook)
                    type = Constant.DEFAULT.TYPE_SHARE
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            imageViewFavorite.setOnClickListener {
                viewModel.setFavorite(itemBook)
            }

            btnAmount.setOnValueChangeListener { _, oldValue, newValue ->
                if (oldValue != newValue) {
                    itemBook?.let {
                        val item = CartEntityLocal(it.id, newValue)
                        val action = actionCart(oldValue, newValue)
                        cartViewModel.setItemInCart(context, item, action)
                    }
                }
            }

            floatingBtnCart.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_detail_to_navigation_cart)
            }
        }
    }

    private fun onClick(book: Book) {
        findNavController().navigate(
            R.id.action_navigation_detail_self,
            bundleOf(Constant.BUNDLED.BOOK to book.id.toString())
        )
    }
}
