package com.my.mythings;

import android.animation.Animator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author 文琳
 * @time 2020/6/16 17:27
 * @desc
 */
class ThingBinder extends ItemViewBinder<Thing, ThingBinder.ThingHolder> {

    public clickLis clickLis;
    private InAnimation mAnimation = new InAnimation();

    public ThingBinder(ThingBinder.clickLis clickLis) {
        this.clickLis = clickLis;
    }

    @NonNull
    @Override
    protected ThingHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ThingHolder(inflater.inflate(R.layout.thing, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ThingHolder holder, @NonNull Thing thing) {
        holder.tvPosition.setText(String.valueOf(thing.getPosition() + 1));
        holder.tvName.setText(thing.getName());
        holder.tvPrice.setText(thing.getPrice());
        holder.tvPosition.setOnClickListener(v -> {
            if (clickLis != null)
                clickLis.click(thing);
        });
        for (Animator anim
                :mAnimation.getAnimators(holder.itemView)){
            anim.setDuration(300).start();
            anim.setInterpolator(new LinearInterpolator());
        }
    }

    static class ThingHolder extends RecyclerView.ViewHolder {
        TextView tvPosition, tvName, tvPrice;

        public ThingHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

    public interface clickLis {
        void click(Thing thing);
    }
}
