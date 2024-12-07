package be.helha.b3.b3q1_android_project.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;

/**
 * Adapter class for adding student names in a RecyclerView.
 */
public class AddStudentAdapter extends RecyclerView.Adapter<AddStudentAdapter.ViewHolder> {
    private final List<String> studentNames;

    /**
     * Constructor for AddStudentAdapter.
     * @param studentNames List of student names.
     */
    public AddStudentAdapter(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    /**
     * Adds a new student to the list.
     */
    public void addStudent() {
        studentNames.add("");
        notifyItemInserted(studentNames.size() - 1);
    }

    /**
     * Returns the list of student names.
     * @return List of student names.
     */
    public List<String> getStudentNames() {
        return studentNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_student_item, parent, false);
        return new ViewHolder(view, studentNames);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(studentNames.get(position));
    }

    @Override
    public int getItemCount() {
        return studentNames.size();
    }

    /**
     * ViewHolder class for student name items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText studentNameEdit;
        private final List<String> studentNames;

        /**
         * Constructor for ViewHolder.
         * @param itemView The view of the student name item.
         * @param studentNames List of student names.
         */
        public ViewHolder(View itemView, List<String> studentNames) {
            super(itemView);
            this.studentNameEdit = itemView.findViewById(R.id.studentName);
            this.studentNames = studentNames;
        }

        /**
         * Binds the student name to the EditText and sets up a TextWatcher.
         * @param studentName The name of the student.
         */
        public void bind(String studentName) {
            studentNameEdit.setText(studentName);
            studentNameEdit.clearFocus();

            if (studentNameEdit.getTag() != null) {
                studentNameEdit.removeTextChangedListener((TextWatcher) studentNameEdit.getTag());
            }

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    studentNames.set(getAdapterPosition(), s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            };

            studentNameEdit.addTextChangedListener(watcher);
            studentNameEdit.setTag(watcher);
        }
    }
}
