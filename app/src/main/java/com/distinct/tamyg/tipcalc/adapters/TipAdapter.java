package com.distinct.tamyg.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.distinct.tamyg.tipcalc.R;
import com.distinct.tamyg.tipcalc.models.TipRecord;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tamyg on 4/06/16.
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context context;
    private List<TipRecord> dataset;
    private OnItemClickListenner onItemClickListenner;


    public TipAdapter(Context context, OnItemClickListenner onItemClickListenner) {
        this.context = context;
        this.dataset = new ArrayList<TipRecord>();
        this.onItemClickListenner = onItemClickListenner;
    }

    public TipAdapter(Context context, List<TipRecord> dataset, OnItemClickListenner onItemClickListenner) {
        this.context = context;
        this.dataset = dataset;
        this.onItemClickListenner = onItemClickListenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord element = dataset.get(position);
        String strTip = String.format(context.getString(R.string.global_message_tip),
                                element.getTip());
        String strDate = element.getDateFormatted();

        holder.txtContent.setText(strTip);
        holder.txtDate.setText(strDate);
        holder.setOnItemClickListenner(element, onItemClickListenner);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public  void add(TipRecord record){
        dataset.add(0, record);
        notifyDataSetChanged();
    }

    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }

    public  static  class ViewHolder extends  RecyclerView.ViewHolder{
        @Bind(R.id.txtContent)
        TextView txtContent;
        @Bind(R.id.txtDate)
        TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListenner(final TipRecord element, final OnItemClickListenner onItemClickListenner) {
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickListenner.onItemClick(element);
                }
            });
        }
    }
}
