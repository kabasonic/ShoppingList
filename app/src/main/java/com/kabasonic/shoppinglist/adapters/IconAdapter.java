package com.kabasonic.shoppinglist.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kabasonic.shoppinglist.R;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

    public static final int[] ArrayIcons =
            { R.drawable.aubergine, R.drawable.beer, R.drawable.birthday_cake
            , R.drawable.biscuit, R.drawable.bread, R.drawable.breakfast, R.drawable.brochettes
            , R.drawable.burger, R.drawable.carrot, R.drawable.cheese, R.drawable.chicken
            , R.drawable.chicken_1, R.drawable.chocolate, R.drawable.chocolate_1, R.drawable.chocolate_2, R.drawable.cocktail
            , R.drawable.coffee, R.drawable.coke, R.drawable.covering, R.drawable.croissant, R.drawable.cup, R.drawable.cupcake
            , R.drawable.donut, R.drawable.egg, R.drawable.fish, R.drawable.fries, R.drawable.honey, R.drawable.hot_dog
            , R.drawable.icecream, R.drawable.jam, R.drawable.jelly, R.drawable.juice, R.drawable.ketchup, R.drawable.lemon
            , R.drawable.lettuce, R.drawable.loaf, R.drawable.milk, R.drawable.noodles, R.drawable.pepper, R.drawable.pickles
            , R.drawable.pie, R.drawable.pizza, R.drawable.rice, R.drawable.sausage, R.drawable.shopping_bag
            , R.drawable.spaguetti, R.drawable.steak, R.drawable.tea, R.drawable.water_glass, R.drawable.watermelon, R.drawable.wine};

    private Context mContext;
    private String[] mNameTitle;
    public IconAdapter(Context mContext) {
        this.mContext = mContext;
        if(this.mNameTitle == null){
            mNameTitle = mContext.getResources().getStringArray(R.array.product_array);
        }
    }

    public interface OnItemIconClickListener {
        void onClickItemIconView(int idIcon);
    }

    OnItemIconClickListener mListener;

    public void setOnItemIconClickListener(OnItemIconClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_icon, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(ContextCompat.getDrawable(mContext,ArrayIcons[position])).into(holder.rowIcon);
        holder.titleIcon.setText(mNameTitle[position]);
    }

    @Override
    public int getItemCount() {
        if(ArrayIcons == null){
            return 0;
        }
        return ArrayIcons.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView rowIcon;
        public TextView titleIcon;

        public ViewHolder(@NonNull View itemView, OnItemIconClickListener mListener) {
            super(itemView);
            rowIcon = itemView.findViewById(R.id.im_choose_icon);
            titleIcon = itemView.findViewById(R.id.name_choose_icons);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onClickItemIconView(ArrayIcons[position]);
                        }
                    }
                }
            });
        }
    }
}
