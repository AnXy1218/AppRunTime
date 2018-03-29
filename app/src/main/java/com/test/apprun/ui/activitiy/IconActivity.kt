package com.test.apprun.ui.activitiy

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.test.apprun.R
import com.test.apprun.model.UserLevel
import com.test.apprun.ui.adapter.IconAdapter
import java.util.*

/**
 * 类描述：
 * 创建时间：2018/3/29
 */
@Suppress("DEPRECATION")
class IconActivity: Activity() {
    private var recyclerView:RecyclerView? = null
    private var imageView:ImageView? = null
    private var tvLevel:TextView? = null

    private var mData: ArrayList<UserLevel>? = null

    private val ivIconPo = IntArray(2)

    private var iconAdapter: IconAdapter? = null
    private var recyclerScrollWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon)

        recyclerView = findViewById(R.id.recycler_icon)
        imageView = findViewById(R.id.iv_icon)
        tvLevel = findViewById(R.id.tvLevel_icon)


        initData()
        initView()
    }

    private fun initData() {
        if (mData == null) {
            mData = ArrayList()
            val userLevel = UserLevel()
            userLevel.level = "Lv1"
            userLevel.score = "60积分"
            mData!!.add(userLevel)
            val userLevel2 = UserLevel()
            userLevel2.level = "Lv2"
            userLevel2.score = "60积分"
            mData!!.add(userLevel2)
            val userLevel3 = UserLevel()
            userLevel3.level = "Lv3"
            userLevel3.score = "60积分"
            mData!!.add(userLevel3)
            val userLevel4 = UserLevel()
            userLevel4.level = "Lv4"
            userLevel4.score = "60积分"
            mData!!.add(userLevel4)
            val userLevel5 = UserLevel()
            userLevel5.level = "Lv5"
            userLevel5.score = "60积分"
            mData!!.add(userLevel5)
            val userLevel6 = UserLevel()
            userLevel6.level = "Lv6"
            userLevel6.score = "60积分"
            mData!!.add(userLevel6)
            val userLevel7 = UserLevel()
            userLevel7.level = "Lv7"
            userLevel7.score = "60积分"
            mData!!.add(userLevel7)
        }
    }

    private fun initView() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView!!.setLayoutManager(manager)
        iconAdapter = IconAdapter(this, mData)
        recyclerView!!.setAdapter(iconAdapter)

        recyclerScrollWidth = recyclerView!!.getLeft()


        imageView!!.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                imageView!!.getLocationInWindow(ivIconPo)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imageView!!.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                }
            }
        })

        recyclerView!!.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerScrollWidth += dx

                val index = centerPo()
                if (index != -1) {
                    tvLevel!!.setVisibility(View.VISIBLE)
                    tvLevel!!.setText(mData!!.get(index).level)
                } else {
                    tvLevel!!.setVisibility(View.GONE)
                }
            }
        })
    }

    private fun centerPo(): Int {
        val left = recyclerScrollWidth + ivIconPo[0]
        val right = left + imageView!!.getWidth()
        var target = 0
        val list = getViews()
        for (i in list.indices) {
            if (left <= target + list[i].width / 2 && target + list[i].width / 2 < right) {
                return i
            }
            target += list[i].width
        }
        return -1
    }

    private fun getViews(): List<View> {
        val viewList = ArrayList<View>()
        for (i in 0 until recyclerView!!.getChildCount()) {
            viewList.add(recyclerView!!.getChildAt(i))
        }
        return viewList
    }
}
