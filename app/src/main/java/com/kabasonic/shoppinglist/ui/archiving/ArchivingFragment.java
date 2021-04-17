package com.kabasonic.shoppinglist.ui.archiving;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.adapters.ShoppingListAdapter;
import com.kabasonic.shoppinglist.ui.home.HomeViewModel;
import com.kabasonic.shoppinglist.util.Constants;


public class ArchivingFragment extends Fragment {

    private Context mContext;
    private ArchivingViewModel archivingViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Constants.TYPE_FRAGMENT_ADAPTER = 1;
        archivingViewModel = new ViewModelProvider(requireActivity()).get(ArchivingViewModel.class);
        return inflater.inflate(R.layout.archiwe_fragment_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_archiving);

        ShoppingListAdapter mAdapter = new ShoppingListAdapter(mContext);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }
}
