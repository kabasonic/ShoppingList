package com.kabasonic.shoppinglist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.adapters.ViewPagerAdapter;
import com.kabasonic.shoppinglist.ui.archiving.ArchivingFragment;
import com.kabasonic.shoppinglist.ui.home.HomeFragment;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    private ViewPagerAdapter viewPagerAdapter;
    public static final String[] titleTab = {"Shopping lists", "Arcived Shopping Lists"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager,container,false);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new ArchivingFragment());

        ViewPager2 viewPager2 = (ViewPager2) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(fragmentArrayList,this.getChildFragmentManager(),getLifecycle());
        viewPager2.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0)
                    tab.setText(getResources().getString(R.string.shopping_list));
                else
                    tab.setText(getResources().getString(R.string.archiving));
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }




}
