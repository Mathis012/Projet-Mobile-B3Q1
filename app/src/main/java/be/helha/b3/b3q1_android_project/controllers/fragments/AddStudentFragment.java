package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.AddStudentAdapter;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;

/**
 * Fragment for adding students to a class.
 */
public class AddStudentFragment extends Fragment {
    private RecyclerView recyclerView;
    private AddStudentAdapter adapter;
    private String classId;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        classId = getArguments().getString("CLASS_ID");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AddStudentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        ImageButton addButton = view.findViewById(R.id.addStudentButton);
        addButton.setOnClickListener(v -> {
            adapter.addStudent();
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveStudents());

        return view;
    }

    /**
     * Saves the list of students to the database.
     */
    private void saveStudents() {
        List<String> studentNames = adapter.getStudentNames();
        if (classId == null) {
            Toast.makeText(getContext(), "Error: Class ID is null.", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabaseHelper dbHelper = new AppDatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (String name : studentNames) {
            if (!name.trim().isEmpty()) {
                ContentValues values = new ContentValues();
                values.put(AppDbSchema.StudentTable.Cols.UUID, java.util.UUID.randomUUID().toString());
                values.put(AppDbSchema.StudentTable.Cols.NAME, name);
                values.put(AppDbSchema.StudentTable.Cols.CLASS_ID, classId);
                long id = db.insert(AppDbSchema.StudentTable.NAME, null, values);
                Log.d("AddStudentFragment", "Saved student with ID: " + id + ", Name: " + name);
            }
        }
        db.close();

        Toast.makeText(getContext(), "Students saved!", Toast.LENGTH_SHORT).show();
        requireActivity().finish();
    }
}
