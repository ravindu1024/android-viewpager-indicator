package com.ravindu1024.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Intavu
 * <p>
 * Created by ravindu on 20/07/16.
 */
public class SimplePagerAdapter extends PagerAdapter
{
    private ArrayList<String> data;
    private LayoutInflater mInflator = null;
    private Context mContext = null;

    public SimplePagerAdapter(Context context, ArrayList<String> d)
    {
        mContext = context;
        data = d;

        mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public Object instantiateItem(ViewGroup collection, int position)
    {
        ViewGroup layout;
        layout = (ViewGroup) mInflator.inflate(R.layout.pager_layout, collection, false);
        TextView t = (TextView) layout.findViewById(R.id.textView);
        t.setText(data.get(position));

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view)
    {
        collection.removeView((View) view);
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}
