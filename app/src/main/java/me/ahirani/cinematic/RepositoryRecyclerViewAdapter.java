package me.ahirani.cinematic;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<RepositoryRecyclerViewAdapter.RepositoryViewHolder> {

    private List<Repository> repositories;

    public RepositoryRecyclerViewAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView repositoryName;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            repositoryName = (TextView) itemView.findViewById(R.id.repo_name);
        }
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_element, parent, false);
        return new RepositoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder repositoryViewHolder, int position) {
        repositoryViewHolder.repositoryName.setText(repositories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
