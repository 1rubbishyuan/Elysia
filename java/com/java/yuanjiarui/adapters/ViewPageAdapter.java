package com.java.yuanjiarui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yuanjiarui.fragments.NewsPageFragement;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentStateAdapter {
    Context context;
    List<Fragment> fragments = new ArrayList<>();

    public ViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        for (int i = 0; i < this.fragments.size(); i++) {
            NewsPageFragement f = (NewsPageFragement) this.fragments.get(i);
            Log.d("theurl2", f.initURL.Url);
        }
    }
}
