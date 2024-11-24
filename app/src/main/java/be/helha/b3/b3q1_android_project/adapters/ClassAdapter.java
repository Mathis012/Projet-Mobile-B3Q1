package be.helha.b3.b3q1_android_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.models.ClassModel;

/**
 * Adapter for the RecyclerView that displays the list of classes.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private List<ClassModel> classes;
    private final OnClassClickListener listener;

    /**
     * Interface for handling click events on the class items.
     */
    public interface OnClassClickListener {
        void onEditClicked(ClassModel aClass, String newName);
        void onDeleteClicked(ClassModel aClass);
        void onCourseClicked(ClassModel aClass);
        void onStudentClicked(ClassModel aClass);
    }

    /**
     * Creates a new instance of the adapter.
     * @param classes The list of classes to display.
     * @param listener The listener for click events.
     */
    public ClassAdapter(List<ClassModel> classes, OnClassClickListener listener) {
        this.classes = classes;
        this.listener = listener;
    }

    /**
     * Sets the list of classes to display.
     * @param classes The list of classes to display.
     */
    public void setItems(List<ClassModel> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Updates the contents of the ViewHolder to reflect the element at the given position.
     * @param holder The ViewHolder to be updated.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassModel aClass = classes.get(position);
        holder.className.setText(aClass.getName());

        holder.deleteButton.setVisibility(View.GONE);
        holder.confirmButton.setVisibility(View.GONE);

        holder.editButton.setOnClickListener(v -> {
            holder.className.setVisibility(View.GONE);
            holder.editName.setVisibility(View.VISIBLE);
            holder.editName.setText(aClass.getName());

            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.confirmButton.setVisibility(View.VISIBLE);
            holder.courseButton.setVisibility(View.GONE);
            holder.studentButton.setVisibility(View.GONE);
        });

        holder.confirmButton.setOnClickListener(v -> {
            String newName = holder.editName.getText().toString();
            listener.onEditClicked(aClass, newName);

            holder.className.setText(newName);
            holder.className.setVisibility(View.VISIBLE);
            holder.editName.setVisibility(View.GONE);

            holder.deleteButton.setVisibility(View.GONE);
            holder.confirmButton.setVisibility(View.GONE);
            holder.courseButton.setVisibility(View.VISIBLE);
            holder.studentButton.setVisibility(View.VISIBLE);
        });

        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClicked(aClass));
        holder.courseButton.setOnClickListener(v -> listener.onCourseClicked(aClass));
        holder.studentButton.setOnClickListener(v -> listener.onStudentClicked(aClass));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return classes.size();
    }

    /**
     * ViewHolder is an internal class that contains the views for each item in the list.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        EditText editName;
        ImageButton editButton, confirmButton, deleteButton, courseButton, studentButton;

        public ViewHolder(View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            editName = itemView.findViewById(R.id.classNameEdit);
            editButton = itemView.findViewById(R.id.modifButton);
            confirmButton = itemView.findViewById(R.id.checkButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            courseButton = itemView.findViewById(R.id.courseButton);
            studentButton = itemView.findViewById(R.id.studentButton);
        }
    }
}
