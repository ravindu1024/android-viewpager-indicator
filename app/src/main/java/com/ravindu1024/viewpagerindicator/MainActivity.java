package com.ravindu1024.viewpagerindicator;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        ViewPagerIndicator indicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);

        ArrayList<String> list = new ArrayList<>();
        list.add("page 1");
        list.add("page 2");

        SimplePagerAdapter adapter = new SimplePagerAdapter(this, list);
        pager.setAdapter(adapter);

        indicator.setPager(pager);

        list.add("page 3");

        adapter.notifyDataSetChanged();

        //indicator.setPager(pager);

        pager.setCurrentItem(1);

    }
}
