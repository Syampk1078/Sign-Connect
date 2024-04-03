package com.example.imagepro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private final String[] buttonNames;
    private final int[] buttonColors;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ButtonAdapter(String[] buttonNames, int[] buttonColors) {
        this.buttonNames = buttonNames;
        this.buttonColors = buttonColors;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);
        return new ButtonViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        holder.button.setText(buttonNames[position]);
        holder.button.setBackgroundColor(buttonColors[position]);
    }

    @Override
    public int getItemCount() {
        return buttonNames.length;
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
