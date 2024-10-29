package com.tuankiet.sample.features.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.tuankiet.sample.R;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private final List<ExploreItem> originalList;
    private final List<ExploreItem> filteredList;
    private final Context context;

    public ExploreAdapter(Context context, List<ExploreItem> itemList) {
        this.context = context;
        this.originalList = new ArrayList<>(itemList);
        this.filteredList = new ArrayList<>(itemList);
    }

    public static class ExploreViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, category, author;

        public ExploreViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_title);
            description = view.findViewById(R.id.item_description);
            category = view.findViewById(R.id.item_category);
            author = view.findViewById(R.id.item_author);
        }
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore, parent, false);
        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        ExploreItem item = filteredList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.category.setText(item.getCategory());
        holder.author.setText(item.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WelcomeAgentActivity.class);
            intent.putExtra("agent_title", item.getTitle());
            intent.putExtra("agent_description", item.getDescription());
            intent.putExtra("agent_category", item.getCategory());
            intent.putExtra("agent_author", item.getAuthor());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            text = text.toLowerCase();
            for (ExploreItem item : originalList) {
                if (item.getTitle().toLowerCase().contains(text) ||
                        item.getDescription().toLowerCase().contains(text) ||
                        item.getCategory().toLowerCase().contains(text) ||
                        item.getAuthor().toLowerCase().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
