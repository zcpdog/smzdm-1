package com.ppjun.android.smzdm.mvp.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.ppjun.android.smzdm.R
import com.ppjun.android.smzdm.app.base.BaseUI
import com.ppjun.android.smzdm.app.utils.BottomNavigationHelper
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.ArticleFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.MainFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.PriceFragment
import com.ppjun.android.smzdm.mvp.ui.activity.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseUI<IPresenter>() {
    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    private var currentFragment: Fragment? = null
    private lateinit var mainFragment: Fragment
    private lateinit var mPriceListFragment: Fragment
    private lateinit var mArticleListFragment: Fragment
    private lateinit var mSettingFragment: Fragment
    private var firstTimeStamp = 0L
    override fun initData(savedInstanceState: Bundle?) {
        mainFragment = MainFragment()
        mPriceListFragment = PriceFragment()
        mArticleListFragment = ArticleFragment()
        mSettingFragment = SettingFragment()
        BottomNavigationHelper.disableShiftModel(navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setBackInvisible()
        switchFragment(0)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_setting -> {
                switchFragment(3)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    private fun switchFragment(position: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (position) {
            0 -> {
                val mainTag = "mMainFragment"
                showFragment(transaction, mainFragment, mainTag)
            }
            1 -> {

                val priceTag = "mPriceListFragment"
                showFragment(transaction, mPriceListFragment, priceTag)

            }
            2 -> {

                val articleTag = "mArticleListFragment"
                showFragment(transaction, mArticleListFragment, articleTag)

            }
            3 -> {
                val settingTag = "mSettingFragment"
                showFragment(transaction, mSettingFragment, settingTag)
            }
        }
        transaction.setTransitionStyle(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()


    }


    override fun onPause() {
        Log.d("onPause", "onPause")
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun showFragment(transaction: android.support.v4.app.FragmentTransaction, targetFragment: Fragment, tag: String) {

        if (targetFragment.isAdded) {
            if (currentFragment != targetFragment) {
                transaction.show(targetFragment).hide(currentFragment)
            }
        } else {
            if (currentFragment == null) {
                transaction.add(R.id.mainFrame, targetFragment, tag)
            } else {
                transaction.add(R.id.mainFrame, targetFragment, tag).hide(currentFragment)
            }

        }
        currentFragment = targetFragment
    }


    override fun onBackPressed() {


        if (System.currentTimeMillis() - firstTimeStamp > 2000) {

            ArmsUtils.snackbarText(getString(R.string.exit_tip))
            firstTimeStamp = System.currentTimeMillis()
        } else {
            finish()


        }
    }

    private fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

}
