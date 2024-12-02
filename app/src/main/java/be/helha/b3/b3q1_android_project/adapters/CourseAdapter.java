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
import be.helha.b3.b3q1_android_project.models.Course;

/**
 * Adapter class for displaying a list of courses in a RecyclerView.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courses;
    private final OnCourseClickListener listener;

    /**
     * Interface for handling click events on the course items.
     */
    public interface OnCourseClickListener {
        void onEditClicked(Course course, String newName);
        void onCourseClicked(Course course);
    }

    /**
     * Constructor for the CourseAdapter.
     * @param courses The list of courses to display.
     * @param listener The listener for handling click events.
     */
    public CourseAdapter(List<Course> courses, OnCourseClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }

    /**
     * Sets the list of courses to display.
     * @param courses The list of courses to display.
     */
    public void setItems(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    /**
     * Creates a new ViewHolder instance.
     * @param parent The parent view.
     * @param viewType The view type.
     * @return A new ViewHolder instance.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder.
     * @param holder The ViewHolder instance.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseName.setText(course.getName());

        holder.editButton.setOnClickListener(v -> {
            holder.courseName.setVisibility(View.GONE);
            holder.editName.setVisibility(View.VISIBLE);
            holder.editName.setText(course.getName());
            holder.confirmButton.setVisibility(View.VISIBLE);
        });

        holder.confirmButton.setOnClickListener(v -> {
            String newName = holder.editName.getText().toString();
            listener.onEditClicked(course, newName);
            holder.courseName.setText(newName);
            holder.courseName.setVisibility(View.VISIBLE);
            holder.editName.setVisibility(View.GONE);
            holder.confirmButton.setVisibility(View.GONE);
        });

        holder.itemView.setOnClickListener(v -> listener.onCourseClicked(course));
    }

    /**
     * Gets the number of items in the list.
     * @return The number of items in the list.
     */
    @Override
    public int getItemCount() {
        return courses != null ? courses.size() : 0;
    }

    /**
     * ViewHolder class for the CourseAdapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        EditText editName;
        ImageButton editButton, confirmButton;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The view for the course item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            editName = itemView.findViewById(R.id.courseNameEdit);
            editButton = itemView.findViewById(R.id.modifButton);
            confirmButton = itemView.findViewById(R.id.checkButton);
        }
    }
}
