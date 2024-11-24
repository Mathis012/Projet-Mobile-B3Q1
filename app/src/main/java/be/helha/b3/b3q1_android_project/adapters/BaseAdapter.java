package be.helha.b3.b3q1_android_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
    * To create RecyclerView adapters for different types of elements.
    * @param <T> The type of elements in the list.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    private List<T> items;

    /**
     * Constructor.
     * @param items The list of items to display.
     */
    public BaseAdapter(List<T> items) {
        this.items = items;
    }

    /**
     * Set the list of items to display.
     * @param newItems The new list of items.
     */
    public void setItems(List<T> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    /**
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return new BaseViewHolder(itemView);
    }

    /**
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        T item = items.get(position);
        bindData(holder.itemView, item);
    }

    /**
     * @return The total number of items in the data set held by the adapter.
     */
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    /**
     * @return The layout resource ID for the item view.
     */
    protected abstract int getItemLayout();

    /**
     * Bind the data of the item to the view.
     * @param itemView The view of the item.
     * @param item The item to display.
     */
    protected abstract void bindData(View itemView, T item);

    /**
     * A ViewHolder that holds a View of the given view type.
     */
    static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
