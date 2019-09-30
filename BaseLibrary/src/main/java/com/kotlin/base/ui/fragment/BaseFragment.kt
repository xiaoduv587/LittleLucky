package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

/*
    Fragment基类，业务无关
 */
abstract class BaseFragment : RxFragment() {

    protected lateinit var mRootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mRootView = inflater.inflate(setLayout(), container, false)

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserve()
    }


    abstract fun setLayout(): Int

    /**
     * 需要的改
     */
    open protected fun init() {}


    //RxBus注册监听
    open protected fun initObserve() {}


}

