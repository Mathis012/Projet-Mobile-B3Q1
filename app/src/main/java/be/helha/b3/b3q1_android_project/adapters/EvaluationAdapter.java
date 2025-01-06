package be.helha.b3.b3q1_android_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.controllers.StudentGradesActivity;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Student;

/**
 * Adapter class for displaying a list of students and their evaluation scores in a RecyclerView.
 */
public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder> {

    private List<Student> students;
    private String courseId;
    private String classId;
    private Context context;
    private AppDatabaseHelper dbHelper;

    /**
     * Constructor for the EvaluationAdapter.
     * @param context The context in which the adapter is used.
     * @param students The list of students to display.
     * @param courseId The ID of the course.
     * @param classId The ID of the class.
     */
    public EvaluationAdapter(Context context, List<Student> students, String courseId, String classId) {
        this.context = context;
        this.students = students;
        this.courseId = courseId;
        this.classId = classId;
        this.dbHelper = new AppDatabaseHelper(context);
    }

    /**
     * Creates a new ViewHolder instance.
     * @param parent The parent view.
     * @param viewType The view type.
     * @return A new ViewHolder instance.
     */
    @NonNull
    @Override
    public EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation, parent, false);
        return new EvaluationViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder.
     * @param holder The ViewHolder instance.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull EvaluationViewHolder holder, int position) {
        Student student = students.get(position);
        holder.studentTextView.setText(student.getFirstName());
        double averageOn20 = calculateAverageOn20(student.getId().toString(), courseId);
        holder.averageTextView.setText(String.format("%.2f / 20", averageOn20));

        holder.arrowButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudentGradesActivity.class);
            intent.putExtra("STUDENT_ID", student.getId().toString());
            intent.putExtra("COURSE_ID", courseId);
            intent.putExtra("CLASS_ID", classId);
            context.startActivity(intent);
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return students.size();
    }

    /**
     * ViewHolder class for the EvaluationAdapter.
     */
    public static class EvaluationViewHolder extends RecyclerView.ViewHolder {
        TextView studentTextView;
        TextView averageTextView;
        ImageButton arrowButton;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The view for the evaluation item.
         */
        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            studentTextView = itemView.findViewById(R.id.evaluationName);
            averageTextView = itemView.findViewById(R.id.averageTextView);
            arrowButton = itemView.findViewById(R.id.arrowButton);
        }
    }

    /**
     * Calculates the average score of a student for a specific course.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return The average score on a scale of 20.
     */
    private double calculateAverageOn20(String studentId, String courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT e." + AppDbSchema.EvaluationTable.Cols.MAX_POINT + ", g." + AppDbSchema.GradeTable.Cols.SCORE +
                        " FROM " + AppDbSchema.EvaluationTable.NAME + " e" +
                        " LEFT JOIN " + AppDbSchema.GradeTable.NAME + " g" +
                        " ON e." + AppDbSchema.EvaluationTable.Cols.UUID + " = g." + AppDbSchema.GradeTable.Cols.EVALUATION_ID +
                        " WHERE e." + AppDbSchema.EvaluationTable.Cols.COURSE_ID + " = ? AND g." + AppDbSchema.GradeTable.Cols.STUDENT_ID + " = ?",
                new String[]{courseId, studentId}
        );

        double totalScore = 0;
        double totalMaxPoints = 0;

        try {
            while (cursor.moveToNext()) {
                int maxPoint = cursor.getInt(cursor.getColumnIndexOrThrow(AppDbSchema.EvaluationTable.Cols.MAX_POINT));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(AppDbSchema.GradeTable.Cols.SCORE));

                totalScore += score;
                totalMaxPoints += maxPoint;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (totalMaxPoints == 0) return 0;

        return (totalScore / totalMaxPoints) * 20;
    }
}