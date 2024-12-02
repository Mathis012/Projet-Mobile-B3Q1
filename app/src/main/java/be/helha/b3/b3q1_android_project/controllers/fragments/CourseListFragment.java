package be.helha.b3.b3q1_android_project.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import be.helha.b3.b3q1_android_project.adapters.CourseAdapter;
import be.helha.b3.b3q1_android_project.controllers.EvaluationActivity;
import be.helha.b3.b3q1_android_project.models.Course;
import be.helha.b3.b3q1_android_project.repositories.CourseRepository;

/**
 * Fragment for displaying a list of courses.
 */
public class CourseListFragment extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private CourseRepository repository;

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
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        classId = getArguments().getString("CLASS_ID");
        repository = new CourseRepository(requireContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshCourses();

        TextView headerTitle = view.findViewById(R.id.headerTitle);
        String className = getArguments().getString("CLASS_NAME");
        headerTitle.setText(className);

        ImageButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            repository.addCourse("New Course", classId);
            refreshCourses();
            Toast.makeText(getContext(), "Course added!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    /**
     * Refreshes the list of courses.
     */
    private void refreshCourses() {
        List<Course> courses = repository.getCoursesForClass(classId);
        if (adapter == null) {
            adapter = new CourseAdapter(courses, new CourseAdapter.OnCourseClickListener() {
                @Override
                public void onEditClicked(Course course, String newName) {
                    repository.updateCourseName(course.getId(), newName);
                    Toast.makeText(getContext(), "Course name updated!", Toast.LENGTH_SHORT).show();
                    refreshCourses();
                }

                @Override
                public void onCourseClicked(Course course) {
                    Intent intent = new Intent(getContext(), EvaluationActivity.class);
                    intent.putExtra("CLASS_ID", classId);
                    intent.putExtra("COURSE_ID", course.getId().toString());
                    intent.putExtra("COURSE_NAME", course.getName());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setItems(courses);
        }
    }
}
