package com.atom.android.lebo.ui.main

import android.app.Dialog
import android.content.Intent
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.atom.android.lebo.R
import com.atom.android.lebo.base.BaseActivity
import com.atom.android.lebo.databinding.ActivityMainBinding
import com.atom.android.lebo.ui.home.HomeFragmentDirections
import com.atom.android.lebo.utils.constants.Constant
import com.atom.android.lebo.utils.extensions.openDialogQuestion
import com.atom.android.lebo.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel by viewModel<MainViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    override fun initData() {
        handleClickNotification(intent)
    }

    override fun handleEvent() {
        viewModel.hasError.observe(this) {
            showToast(it)
        }
    }

    override fun bindData() {
        setupWithNavController(binding.navView, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> setNavigationVisible(true)
                R.id.navigation_library -> setNavigationVisible(true)
                R.id.navigation_favorite -> setNavigationVisible(true)
                R.id.navigation_account -> setNavigationVisible(true)
                else -> setNavigationVisible()
            }
        }
    }

    private fun setNavigationVisible(isVisible: Boolean = false) {
        binding.navView.isVisible = isVisible
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            navController.handleDeepLink(it)
            handleClickNotification(it)
        }
    }

    private fun handleClickNotification(intent: Intent) {
        if (intent.hasExtra(Constant.BUNDLED.BILL_NOTIFICATION)) {
            val idBill = intent.getStringExtra(Constant.BUNDLED.BILL_NOTIFICATION)
                ?: Constant.BUNDLED.ERROR
            val action = HomeFragmentDirections
                .actionNavigationHomeToNavigationBill(idBill.toInt())
            navController.navigate(action)
        }
    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        val title = getString(R.string.title_exit_app)
        val message = getString(R.string.message_exit_app)
        if (navController.backQueue.size == Constant.DEFAULT.QUEUEBACKSTACK) {
            dialog.openDialogQuestion(title, message) { finish() }
        } else {
            super.onBackPressed()
        }
    }
}
