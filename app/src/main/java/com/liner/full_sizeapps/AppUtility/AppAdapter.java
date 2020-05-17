package com.liner.full_sizeapps.AppUtility;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner.full_sizeapps.R;

import java.util.List;

@SuppressWarnings("CanBeFinal")
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<AppHolder> appHolderList;
    private Context context;
    private ISelectionListener selectionListener;

    public AppAdapter(List<AppHolder> apps, Context context, ISelectionListener selectionListener) {
        this.appHolderList = apps;
        this.context = context;
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final AppHolder item = appHolderList.get(position);

        holder.appIcon.setImageDrawable(AppHelper.getApplicationIcon(context, item.getAppPackageName()));
        holder.appName.setTypeface(holder.appName.getTypeface(), (item.isSelected())?Typeface.BOLD: Typeface.NORMAL);
        holder.appName.setText(String.valueOf(item.getAppName()));
        holder.selectedCheckBox.setChecked(item.isSelected());
        holder.appPackageName.setText(String.valueOf(item.getAppPackageName()));
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionListener != null) {
                    item.setSelected(!item.isSelected());
                    holder.selectedCheckBox.setChecked(item.isSelected());
                    holder.appName.setTypeface(holder.appName.getTypeface(), (item.isSelected())?Typeface.BOLD: Typeface.NORMAL);
                    selectionListener.onItemsSelected(appHolderList);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appHolderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appPackageName;
        LinearLayout itemLayout;
        CheckBox selectedCheckBox;


        ViewHolder(final View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            appIcon = itemView.findViewById(R.id.appIcon);
            appName = itemView.findViewById(R.id.appName);
            appPackageName = itemView.findViewById(R.id.appPackageName);
            selectedCheckBox = itemView.findViewById(R.id.selected);
        }
    }

    public interface ISelectionListener {
        void onItemsSelected(List<AppHolder> appHolderList);
    }


    public void setSelected(boolean value){
        for (AppHolder item:appHolderList){
            item.setSelected(value);
        }
        if (selectionListener != null) {
            selectionListener.onItemsSelected(appHolderList);
        }
        notifyDataSetChanged();
    }
}