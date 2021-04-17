package com.kabasonic.shoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private Context mContext;
    private List<ShoppingListWithItems> mRowList;

    public interface OnItemClickListener{
        void onClickItemView(int position);
    }

    OnItemClickListener mListener;

    public ShoppingListAdapter(Context mContext){
        this.mContext = mContext;
        if(mRowList == null){
            this.mRowList = new ArrayList<>();
        }
    }

    public void setRowList(List<ShoppingListWithItems> mRowList) {
        this.mRowList = mRowList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_row_shopping_list,parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        if(Constants.TYPE_FRAGMENT_ADAPTER == Constants.HOME ||
            Constants.TYPE_FRAGMENT_ADAPTER == Constants.ARCHIVING){
            if(Constants.TYPE_FRAGMENT_ADAPTER == Constants.HOME){
                Glide.with(mContext).load(R.drawable.ic_outline_shopping_cart_24).into(holder.rowIcon);
            }else{
                Glide.with(mContext).load(R.drawable.ic_outline_archive_24).into(holder.rowIcon);
            }
            holder.rowMainTitle.setText(mRowList.get(position).shoppingList.getTitle());
            String subTitle = "Groceries done " + String.valueOf(mRowList.get(position).shoppingList.getCompletedTasks());
            holder.rowSubTitle.setText(subTitle);
            holder.rowAmountLayout.setVisibility(View.GONE);
            holder.rowMainBt.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if(mRowList == null){
            return 0;
        }
        return mRowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView rowIcon, rowUpCount, rowDownCount, rowMainBt;
        public TextView rowMainTitle, rowSubTitle;
        public EditText rowAmount;
        public LinearLayout rowAmountLayout;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            //imageView
            rowIcon = itemView.findViewById(R.id.rv_icon);
            rowUpCount = itemView.findViewById(R.id.rv_up_count);
            rowDownCount = itemView.findViewById(R.id.rv_down_count);
            rowMainBt = itemView.findViewById(R.id.rv_main_bt);
            //editText
            rowAmount = itemView.findViewById(R.id.rv_amount);
            //LinearLayout
            rowAmountLayout = itemView.findViewById(R.id.rv_amount_layout);
            //textView
            rowMainTitle = itemView.findViewById(R.id.rv_main_title);
            rowSubTitle = itemView.findViewById(R.id.rv_sub_title);

            if(Constants.TYPE_FRAGMENT_ADAPTER != Constants.ARCHIVING){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListener!=null){
                            int position = getAdapterPosition();
                            if(position!=RecyclerView.NO_POSITION){
                                mListener.onClickItemView(position);
                            }
                        }
                    }
                });
            }
        }
    }
}
