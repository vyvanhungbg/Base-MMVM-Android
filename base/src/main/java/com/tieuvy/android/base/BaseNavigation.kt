package com.tieuvy.android.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

abstract class BaseNavigation {

    abstract fun fragment(): BaseFragment<*, *>

    val navController: NavController?
        get() {
            return try {
                if (fragment().activity != null && fragment().isAdded) {
                    fragment().findNavController()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


    private val currentNavDestination: NavDestination?
        get() {
            return navController?.currentDestination
        }


    private var defaultLifecycleObserver: DefaultLifecycleObserver? = null


    /**
     * Xử lý chuyển màn hình
     * @param currentNavId -> truyền vào id màn hình hiện tại để kiểm tra xem có đúng đang đứng ở màn hình hiện tại k
     * @param directions -> sử dụng navigation safe
     */
    fun navigateTo(currentNavId: Int, directions: NavDirections, navOptions: NavOptions? = null, navOnResumed: Boolean = true) {
        fun executeNavigate() {
            if (navController != null && currentNavDestination?.id == currentNavId) {
                navController?.navigate(directions, navOptions)
                return
            }
        }

        if (!navOnResumed) {
            executeNavigate()
        } else {
            try {
                navController?.navigate(directions, navOptions)
            } catch (e: Exception) {
                val lifeCycleState = fragment().lifecycle.currentState
                if (lifeCycleState == Lifecycle.State.RESUMED) {
                    executeNavigate()
                } else {
                    if (defaultLifecycleObserver != null) {
                        fragment().lifecycle.removeObserver(defaultLifecycleObserver!!)
                    }
                    defaultLifecycleObserver = object : DefaultLifecycleObserver {
                        override fun onResume(owner: LifecycleOwner) {
                            super.onResume(owner)
                            fragment().lifecycle.removeObserver(this)
                            executeNavigate()
                        }
                    }

                    fragment().lifecycle.addObserver(defaultLifecycleObserver as DefaultLifecycleObserver)
                }
            }
        }
    }

    fun popBackStack() = navController?.popBackStack() ?: false

}