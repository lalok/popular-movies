package de.lokaizyk.popularmovies.util;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import de.lokaizyk.popularmovies.ui.adapter.BaseBindingRecyclerAdapter;

/**
 * Created by lars on 03.10.16.
 */
@SuppressWarnings("unchecked")
public class RecyclerViewBindings {

    @BindingAdapter("bind:items")
    public static <T> void setItems(RecyclerView recyclerView, ObservableList<T> itms) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (!(adapter instanceof BaseBindingRecyclerAdapter)) {
            throw new IllegalArgumentException(adapter.getClass().getSimpleName() + " must extend " + BaseBindingRecyclerAdapter.class.getSimpleName());
        }
        ((BaseBindingRecyclerAdapter)adapter).setItems(itms);
    }

    @BindingAdapter("bind:itemClickListener")
    public static <T> void setItemClickListener(RecyclerView recyclerView, BaseBindingRecyclerAdapter.OnItemClickListener<T> itemClickListener) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (!(adapter instanceof BaseBindingRecyclerAdapter)) {
            throw new IllegalArgumentException(adapter.getClass().getSimpleName() + " must extend " + BaseBindingRecyclerAdapter.class.getSimpleName());
        }
        ((BaseBindingRecyclerAdapter)adapter).setOnItemClickListener(itemClickListener);
    }

}
