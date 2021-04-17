package com.kabasonic.shoppinglist.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kabasonic.shoppinglist.ui.ArchiweFragment;
import com.kabasonic.shoppinglist.ui.home.HomeFragment;
import com.kabasonic.shoppinglist.ui.ViewPagerFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> mFragmentList;

    public ViewPagerAdapter(ArrayList<Fragment> fragmentArrayList, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        if (mFragmentList == null) {
            this.mFragmentList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HomeFragment();
        }else if(position == 1){
            return new ArchiweFragment();
        }
        return new ViewPagerFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
