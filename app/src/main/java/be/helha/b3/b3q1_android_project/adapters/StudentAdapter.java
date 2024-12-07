package be.helha.b3.b3q1_android_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.models.Student;

/**
 * Adapter class for displaying a list of students in a RecyclerView.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> students;

    /**
     * Constructor for StudentAdapter.
     * @param students List of students to display.
     */
    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    /**
     * Sets the list of students to display.
     * @param students List of students.
     */
    public void setItems(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.studentName.setText(student.getFirstName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    /**
     * ViewHolder class for student items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;

        /**
         * Constructor for ViewHolder.
         * @param itemView The view of the student item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
        }
    }
}
