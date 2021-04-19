package com.kabasonic.shoppinglist.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private Context mContext;
    private List<ShoppingListWithItems> mRowList;
    private List<ItemList> mSubList;
    private ItemList itemList;
    public interface OnItemClickListener{
        void onClickItemView(int position,int id);
    }

    OnItemClickListener mListener;

    public ShoppingListAdapter(Context mContext){
        this.mContext = mContext;
        if(mRowList == null){
            this.mRowList = new ArrayList<>();
        }
        if(mSubList == null){
            this.mSubList = new ArrayList<>();
        }
    }

    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }

    //set home fragment
    public void setRowListHome(List<ShoppingListWithItems> mRowList) {
        this.mRowList = mRowList;
    }
    //set shopping fragment
    public void setRowListShopping(ShoppingListWithItems shoppingListWithItems){
        this.mSubList = shoppingListWithItems.itemListShoppingList;
    }

    //set shopping fragment
    public void addItemToRowList(ItemList itemList){
        this.mSubList.add(0,itemList);
        notifyItemInserted(0);
        Log.d("TAG","List size: " + mSubList.size());
    }

    public List<ShoppingListWithItems> getRowList() {
        return mRowList;
    }

    public void removeItemFromItemsList(int position){
        this.mSubList.remove(position);
    }

    private int getIdAtRow(int position){
        if(mRowList != null){
            return mRowList.get(position).shoppingList.getId();
        }
        return 0;
    }
    protected int countCompletedTask(){
        int i = 0;
        for(ItemList item: mSubList){
            if(item.isCompleted()){
                i++;
            }
        }
        return i;
    }

    public ItemList getAtItemList(int position){
        return mSubList.get(position);
    }

    public List<ItemList> getSubList() {
        return mSubList;
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
                holder.rowIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_outline_shopping_cart_24));
            }else{
                holder.rowIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_outline_archive_24));
            }
            holder.rowMainTitle.setText(mRowList.get(position).shoppingList.getTitle());
            //String subTitle = "Groceries done " + String.valueOf(mRowList.get(position).shoppingList.getCompletedTasks());
            String subTitle = getCompletedTask(mRowList.get(position));
            holder.rowSubTitle.setText(subTitle);
            holder.rowAmountLayout.setVisibility(View.GONE);
            holder.rowMainBt.setVisibility(View.GONE);
        }
        if(Constants.TYPE_FRAGMENT_ADAPTER == Constants.CREATING_LIST){
            holder.rowIcon.setImageResource(mSubList.get(position).getImage());
            holder.rowMainTitle.setText(mSubList.get(position).getTitle());
            holder.rowSubTitle.setVisibility(View.GONE);
            holder.rowAmountLayout.setVisibility(View.VISIBLE);
            String amount = String.valueOf(mSubList.get(position).getAmount());
            holder.rowAmount.setText(amount);
            if(mSubList.get(position).isCompleted()){
                holder.rowMainBt.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_baseline_check_circle_24));
                holder.rowMainTitle.setPaintFlags(holder.rowMainTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                holder.rowMainTitle.setPaintFlags(0);
                holder.rowMainBt.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_baseline_check_circle_outline_24));
            }
        }
    }

    @Override
    public int getItemCount() {
        if(Constants.TYPE_FRAGMENT_ADAPTER != Constants.CREATING_LIST){
            if(mRowList != null){
                return mRowList.size();
            }else{
                return 0;
            }
        }else{
            if(mSubList!=null){
                return mSubList.size();
            }else {
                return 0;
            }
        }
    }

    public String getCompletedTask(ShoppingListWithItems itemList){
        int i = 0 ;
        for(ItemList item: itemList.itemListShoppingList){
            if(item.isCompleted()){
                i++;
            }
        }
        String result = "Groceries done  " + String.valueOf(i) + "/"
                +String.valueOf(itemList.itemListShoppingList.size());
        return result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView rowIcon, rowUpCount, rowDownCount, rowMainBt;
        public TextView rowMainTitle, rowSubTitle;
        public EditText rowAmount;
        public LinearLayout rowAmountLayout;



        public ViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
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

            rowUpCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countAmount(true);
                }
            });
            rowDownCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countAmount(false);
                }
            });

            rowIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        int id = getIdAtRow(position);
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onClickItemView(position,id);
                        }
                    }
                }
            });

            rowMainBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                isCompleted();
                notifyDataSetChanged();
                }
            });



            if(Constants.TYPE_FRAGMENT_ADAPTER != Constants.ARCHIVING){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mListener!=null){
                            int position = getAdapterPosition();
                            int id = getIdAtRow(position);
                            if(position!=RecyclerView.NO_POSITION){
                                mListener.onClickItemView(position, id);
                            }
                        }
                    }
                });
            }
        }

        private void countAmount(boolean action){
            int amount = Integer.parseInt(rowAmount.getText().toString());
            if(action && 0 <= amount && amount <= 99){
                amount++;
                rowAmount.setText("0");
            }else if(!action && 0 < amount && amount < 99){
                amount--;
                rowAmount.setText("0");
            }else{
                rowAmount.setText("0");
            }
            rowAmount.setText(String.valueOf(amount));
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                itemList = mSubList.get(position);
                itemList.setAmount(amount);
                mSubList.set(position,itemList);
            }
        }
        private void isCompleted(){
            int position = getAdapterPosition();
            itemList = mSubList.get(position);
            itemList.setCompleted(!mSubList.get(position).isCompleted());
            mSubList.set(position,itemList);
        }

    }
}
