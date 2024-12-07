package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.StudentAdapter;
import be.helha.b3.b3q1_android_project.controllers.AddStudentActivity;
import be.helha.b3.b3q1_android_project.models.Student;
import be.helha.b3.b3q1_android_project.repositories.StudentRepository;

public class StudentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private StudentRepository repository;

    private String classId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        classId = getArguments().getString("CLASS_ID");
        repository = new StudentRepository(requireContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshStudents();

        TextView headerTitle = view.findViewById(R.id.headerTitle);
        String className = getArguments().getString("CLASS_NAME");
        headerTitle.setText(className);

        ImageButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddStudentActivity.class);
            intent.putExtra("CLASS_ID", classId);
            startActivity(intent);
        });

        return view;
    }

    private void refreshStudents() {
        List<Student> students = repository.getStudentsForClass(classId);
        if (adapter == null) {
            adapter = new StudentAdapter(students);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setItems(students);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshStudents();
    }
}
