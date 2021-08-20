package com.example.moviesapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class AutoScrollViewPager : ViewPager {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }


    private var mHandler: Handler = Handler(Looper.getMainLooper())

    private var scrollPosition = 0
    private var interval: Long = 3000

    private val runnable = object : Runnable {

        override fun run() {

            /**
             * Calculate "scroll position" with
             * adapter pages count and current
             * value of scrollPosition.
             */
            val count = adapter?.count ?: 0
            if (count > 0)
                setCurrentItem(scrollPosition++ % count, true)

            mHandler.postDelayed(this, interval)
        }
    }


    fun startAutoScroll(interval: Long = 3000) {
        this.interval = interval

        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // Updating "scroll position" when user scrolls manually
                scrollPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not necessary
            }
        })

        mHandler.post(runnable)
    }

    fun cancelAutoScroll() {
        mHandler.removeCallbacks(runnable)
    }
}