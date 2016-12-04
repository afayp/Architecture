package com.pfh.app_mvvm.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pfh.app_mvvm.R;
import com.pfh.app_mvvm.databinding.ItemRepoBinding;
import com.pfh.app_mvvm.model.Repository;
import com.pfh.app_mvvm.viewmodel.ItemRepoViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> repositories;

    public RepositoryAdapter() {
        this.repositories = Collections.emptyList();
    }

    public RepositoryAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }


    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRepoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_repo,
                parent,false);
        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bindRepository(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public static class RepositoryViewHolder extends RecyclerView.ViewHolder{
        ItemRepoBinding binding;

        public RepositoryViewHolder(ItemRepoBinding binding) {
            super(binding.cardView);// binding.getRoot()
            this.binding = binding;
        }

        void bindRepository(Repository repository){
            if (binding.getViewModel() == null){
                binding.setViewModel(new ItemRepoViewModel(itemView.getContext(),repository));
            }else {
                binding.getViewModel().setRepository(repository);
            }

        }

        public void setBinding(ItemRepoBinding binding){
            this.binding = binding;
        }

        public ItemRepoBinding getBinding(){
            return binding;
        }
    }
}
