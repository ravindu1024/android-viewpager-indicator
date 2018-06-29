package com.ravindu1024.indicatorlib

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.annotation.DrawableRes
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout

/**
 * ViewPagerIndicator
 *
 *
 * Created by ravindu on 20/07/16.
 *
 */
class ViewPagerIndicator : LinearLayout, ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
    private var mContext: Context? = null
    private var mPager: ViewPager? = null

    @ColorInt
    private var mSelectedColor = -1
    @ColorInt
    private var mDeselectedColor = -1
    @DrawableRes
    private var mSelectedDrawable = -1
    @DrawableRes
    private var mDeselectedDrawable = -1
    @Dimension
    private var mIndicatorSpacing = 5
    private var mAnimationDuration = 150
    private var mAnimScaleMultiplier = 1.5f
    private var mAnimate = false
    @ColorInt private var mBackgroundColorCustom = -1

    private val mDatasetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()

            initializeIndicatorBar(mPager!!.adapter!!.count)
        }
    }

    /**
     * Set this after setting the adapter to the pager.
     * @param pager the connected viewpager
     */
    fun setPager(pager: ViewPager) {

        mPager?.let{
            it.removeOnPageChangeListener(this@ViewPagerIndicator)
            it.removeOnAdapterChangeListener(this@ViewPagerIndicator)
            it.adapter?.unregisterDataSetObserver(mDatasetObserver)
        }

        mPager = pager

        initializeIndicatorBar(mPager!!.adapter?.count ?: 0)
        mPager!!.addOnPageChangeListener(this)
        mPager!!.addOnAdapterChangeListener(this)
        mPager!!.adapter!!.registerDataSetObserver(mDatasetObserver)

    }


    constructor(context: Context) : super(context) {
        initializeViews(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeViews(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeViews(context, attrs)
    }


    private fun initializeViews(context: Context, attrs: AttributeSet?) {
        mContext = context

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator)

            mSelectedColor = a.getColor(R.styleable.ViewPagerIndicator_selectedColor, getThemeColor(context, R.attr.colorAccent))
            mDeselectedColor = a.getColor(R.styleable.ViewPagerIndicator_deselectedColor, Color.LTGRAY)
            mSelectedDrawable = a.getResourceId(R.styleable.ViewPagerIndicator_selectedDrawable, -1)
            mDeselectedDrawable = a.getResourceId(R.styleable.ViewPagerIndicator_deselectedDrawable, -1)
            mIndicatorSpacing = a.getDimension(R.styleable.ViewPagerIndicator_indicatorSpacing, 5f).toInt()
            mAnimate = a.getBoolean(R.styleable.ViewPagerIndicator_enableAnimation, false)
            mAnimationDuration = a.getInteger(R.styleable.ViewPagerIndicator_animationDuration, 150)
            mAnimScaleMultiplier = a.getFloat(R.styleable.ViewPagerIndicator_animationScale, 1.5f)
            mBackgroundColorCustom = a.getColor(R.styleable.ViewPagerIndicator_backgroundColor, Color.TRANSPARENT)

            a.recycle()
        }

        setBackgroundColor(mBackgroundColorCustom)
    }

    @ColorInt
    private fun getThemeColor(context: Context, @AttrRes attributeColor: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attributeColor, value, true)

        return value.data
    }

    private fun initializeIndicatorBar(num: Int) {
        removeAllViewsInLayout()

        for (i in 0 until num) {
            val img = ImageView(mContext)
            img.tag = i

            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(mIndicatorSpacing / 2, 0, mIndicatorSpacing / 2, 0)
            lp.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            addView(img, lp)
        }

        setSelectedIndicator(mPager?.currentItem ?: 0)
    }

    private fun setSelectedIndicator(selected: Int) {
        val num = childCount

        for (i in 0 until num) {
            val img = getChildAt(i) as ImageView?

            img?.let {
                if (mAnimate) {
                    img.clearAnimation()

                    img.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(mAnimationDuration.toLong())
                            .start()
                }


                img.clearColorFilter()

                if (mDeselectedDrawable != -1) {
                    img.setImageResource(mDeselectedDrawable)
                } else if (mSelectedDrawable != -1) {
                    img.setImageResource(mSelectedDrawable)
                    img.colorFilter = LightingColorFilter(0, mDeselectedColor)
                } else {
                    img.setImageResource(R.drawable.circle_drawable)
                    img.colorFilter = LightingColorFilter(0, mDeselectedColor)
                }
            }
        }

        val selectedView = getChildAt(selected) as ImageView?
        selectedView?.let {
            if (mAnimate) {
                selectedView.animate()
                        .scaleX(mAnimScaleMultiplier)
                        .scaleY(mAnimScaleMultiplier)
                        .setDuration(mAnimationDuration.toLong())
                        .start()
            }

            if (mSelectedDrawable != -1) {
                selectedView.clearColorFilter()
                selectedView.setImageResource(mSelectedDrawable)
            } else {
                selectedView.colorFilter = LightingColorFilter(0, mSelectedColor)
            }
        }


    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        setSelectedIndicator(position)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
        oldAdapter?.unregisterDataSetObserver(mDatasetObserver)
        newAdapter?.let {
            initializeIndicatorBar(it.count)
            it.registerDataSetObserver(mDatasetObserver)
        }
    }

}
