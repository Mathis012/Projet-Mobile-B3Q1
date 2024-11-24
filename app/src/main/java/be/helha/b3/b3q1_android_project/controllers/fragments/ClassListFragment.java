package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.adapters.ClassAdapter;
import be.helha.b3.b3q1_android_project.controllers.CourseActivity;
import be.helha.b3.b3q1_android_project.controllers.StudentActivity;
import be.helha.b3.b3q1_android_project.repositories.ClassesRepository;
import be.helha.b3.b3q1_android_project.models.ClassModel;

/**
 * Fragment that displays the list of classes.
 */
public class ClassListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private ClassesRepository repository;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        repository = new ClassesRepository(requireContext());
        refreshClasses();

        ImageButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            repository.addClass("New Class");
            refreshClasses();
            Toast.makeText(getContext(), "Class added!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    /**
     * Refreshes the list of classes.
     */
    private void refreshClasses() {
        List<ClassModel> classes = repository.getAll();
        if (adapter == null) {
            adapter = new ClassAdapter(classes, new ClassAdapter.OnClassClickListener() {
                @Override
                public void onEditClicked(ClassModel aClass, String newName) {
                    repository.updateClassName(aClass.getId(), newName);
                    Toast.makeText(getContext(), "Class updated!", Toast.LENGTH_SHORT).show();
                    refreshClasses();
                }

                @Override
                public void onDeleteClicked(ClassModel aClass) {
                    repository.deleteClass(aClass.getId());
                    refreshClasses();
                }

                @Override
                public void onCourseClicked(ClassModel aClass) {
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    intent.putExtra("CLASS_ID", aClass.getId().toString());
                    intent.putExtra("CLASS_NAME", aClass.getName());
                    startActivity(intent);
                }

                @Override
                public void onStudentClicked(ClassModel aClass) {
                    Intent intent = new Intent(getActivity(), StudentActivity.class);
                    intent.putExtra("CLASS_ID", aClass.getId().toString());
                    intent.putExtra("CLASS_NAME", aClass.getName());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setItems(classes);
        }
    }
}
