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

public class AddStudentAdapter extends RecyclerView.Adapter<AddStudentAdapter.ViewHolder> {
    private final List<String> studentNames;

    public AddStudentAdapter(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    public void addStudent() {
        studentNames.add("");
        notifyItemInserted(studentNames.size() - 1);
    }

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText studentNameEdit;
        private final List<String> studentNames;

        public ViewHolder(View itemView, List<String> studentNames) {
            super(itemView);
            this.studentNameEdit = itemView.findViewById(R.id.studentName);
            this.studentNames = studentNames;
        }

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
