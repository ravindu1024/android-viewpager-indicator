package com.ravindu1024.indicatorlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ViewPagerIndicator
 * <p/>
 * Created by ravindu on 20/07/16.
 *
 */
public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener
{
    private Context mContext = null;
    private ViewPager mPager;

    @ColorInt private int mSelectedColor = -1;
    @ColorInt private int mDeselectedColor = -1;
    @DrawableRes private int mSelectedDrawable = -1;
    @DrawableRes private int mDeselectedDrawable = -1;
    @Dimension private int mIndicatorSpacing = 5;

    /**
     * Set this after setting the adapter to the pager.
     * @param pager the connected viewpager
     */
    public void setPager(ViewPager pager)
    {
        if(mPager != null)
        {
            mPager.removeOnPageChangeListener(this);
            mPager.removeOnAdapterChangeListener(this);
            mPager = null;
        }

        mPager = pager;

        initializeIndicatorBar(mPager.getAdapter().getCount());
        mPager.addOnPageChangeListener(this);
        mPager.addOnAdapterChangeListener(this);
        mPager.getAdapter().registerDataSetObserver(mDatasetObserver);

    }


    public ViewPagerIndicator(Context context)
    {
        super(context);
        initializeViews(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }


    private void initializeViews(Context context, AttributeSet attrs)
    {
        mContext = context;

        if(attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

            mSelectedColor = a.getColor(R.styleable.ViewPagerIndicator_selectedColor, getThemeColor(context, R.attr.colorAccent));
            mDeselectedColor = a.getColor(R.styleable.ViewPagerIndicator_deselectedColor, Color.LTGRAY);
            mSelectedDrawable = a.getResourceId(R.styleable.ViewPagerIndicator_selectedDrawable, -1);
            mDeselectedDrawable = a.getResourceId(R.styleable.ViewPagerIndicator_deselectedDrawable, -1);
            mIndicatorSpacing = (int) a.getDimension(R.styleable.ViewPagerIndicator_indicatorSpacing, 5);

            a.recycle();
        }
    }

    @ColorInt
    private int getThemeColor(@NonNull final Context context, @AttrRes final int attributeColor)
    {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute (attributeColor, value, true);

        return value.data;
    }

    private void initializeIndicatorBar(int num)
    {
        removeAllViewsInLayout();

        for(int i = 0; i < num; i++)
        {
            ImageView img = new ImageView(mContext);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(mIndicatorSpacing/2, 0, mIndicatorSpacing/2, 0);
            lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
            addView(img, lp);
        }

        setSelectedIndicator(mPager.getCurrentItem());
    }

    private void setSelectedIndicator(int selected)
    {
        int num = getChildCount();

        for(int i = 0; i < num; i++)
        {
            ImageView img = (ImageView) getChildAt(i);

            img.clearColorFilter();

            if(mDeselectedDrawable != -1)
            {
                img.setImageResource(mDeselectedDrawable);
            }
            else if(mSelectedDrawable != -1)
            {
                img.setImageResource(mSelectedDrawable);
                img.setColorFilter(new LightingColorFilter(0, mDeselectedColor));
            }
            else
            {
                img.setImageResource(R.drawable.circle_drawable);
                img.setColorFilter(new LightingColorFilter(0, mDeselectedColor));
            }
        }

        ImageView selectedView = (ImageView) getChildAt(selected);

        if(mSelectedDrawable != -1)
        {
            selectedView.clearColorFilter();
            selectedView.setImageResource(mSelectedDrawable);
        }
        else
        {
            selectedView.setColorFilter(new LightingColorFilter(0, mSelectedColor));
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        setSelectedIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter)
    {
        if(oldAdapter != null)
        {
            oldAdapter.unregisterDataSetObserver(mDatasetObserver);
        }
        if(newAdapter != null)
        {
            initializeIndicatorBar(newAdapter.getCount());
            newAdapter.registerDataSetObserver(mDatasetObserver);
        }
    }

    private DataSetObserver mDatasetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();

            initializeIndicatorBar(mPager.getAdapter().getCount());
        }
    };
}
