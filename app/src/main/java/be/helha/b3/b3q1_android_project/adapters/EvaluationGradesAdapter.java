package be.helha.b3.b3q1_android_project.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.db.AppDatabaseHelper;
import be.helha.b3.b3q1_android_project.db.AppDbSchema;
import be.helha.b3.b3q1_android_project.models.Evaluation;

/**
 * Adapter class for displaying and managing evaluation grades in a RecyclerView.
 */
public class EvaluationGradesAdapter extends RecyclerView.Adapter<EvaluationGradesAdapter.ViewHolder> {

    private Context context;
    private List<Evaluation> evaluations;
    private String studentId;
    private AppDatabaseHelper dbHelper;

    /**
     * Constructor for the EvaluationGradesAdapter.
     * @param context The context in which the adapter is used.
     * @param evaluations The list of evaluations to display.
     * @param studentId The ID of the student.
     */
    public EvaluationGradesAdapter(Context context, List<Evaluation> evaluations, String studentId) {
        this.context = context;
        this.evaluations = evaluations;
        this.studentId = studentId;
        this.dbHelper = new AppDatabaseHelper(context);
    }

    /**
     * Creates a new ViewHolder instance.
     * @param parent The parent view.
     * @param viewType The view type.
     * @return A new ViewHolder instance.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grades_evaluation, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder.
     * @param holder The ViewHolder instance.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Evaluation evaluation = evaluations.get(position);
        holder.evalName.setText(evaluation.getName());
        int gradeScore = getGradeForEvaluation(studentId, evaluation.getId().toString());
        holder.scoreDisplay.setText(gradeScore == 0 ? String.format("0 / %d", evaluation.getMaxPoint()) : String.format("%d / %d", gradeScore, evaluation.getMaxPoint()));

        holder.saveButton.setOnClickListener(v -> {
            String enteredScore = holder.scoreInput.getText().toString();
            if (!enteredScore.isEmpty()) {
                int score = Integer.parseInt(enteredScore);
                if (score > evaluation.getMaxPoint()) {
                    holder.scoreInput.setError("Score cannot exceed " + evaluation.getMaxPoint());
                } else {
                    holder.scoreDisplay.setText(String.format("%d / %d", score, evaluation.getMaxPoint()));
                    saveGrade(evaluation.getId().toString(), enteredScore, studentId);

                    Log.d("EvaluationGradesAdapter", "Score saved: " + enteredScore);
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    /**
     * Saves the grade for a specific evaluation and student.
     * @param evaluationId The ID of the evaluation.
     * @param score The score to save.
     * @param studentId The ID of the student.
     */
    private void saveGrade(String evaluationId, String score, String studentId) {
        UUID gradeId = UUID.randomUUID();
        dbHelper.getWritableDatabase().execSQL("INSERT OR REPLACE INTO " + AppDbSchema.GradeTable.NAME + " (" +
                        AppDbSchema.GradeTable.Cols.UUID + ", " +
                        AppDbSchema.GradeTable.Cols.STUDENT_ID + ", " +
                        AppDbSchema.GradeTable.Cols.EVALUATION_ID + ", " +
                        AppDbSchema.GradeTable.Cols.SCORE + ") VALUES (?, ?, ?, ?)",
                new Object[]{gradeId.toString(), studentId, evaluationId, Integer.parseInt(score)});
        Log.d("EvaluationGradesAdapter", "Grade saved for evaluation: " + evaluationId);
    }

    /**
     * Retrieves the grade for a specific evaluation and student.
     * @param studentId The ID of the student.
     * @param evaluationId The ID of the evaluation.
     * @return The grade score.
     */
    private int getGradeForEvaluation(String studentId, String evaluationId) {
        Cursor cursor = dbHelper.getReadableDatabase().query(
                AppDbSchema.GradeTable.NAME,
                new String[]{AppDbSchema.GradeTable.Cols.SCORE},
                AppDbSchema.GradeTable.Cols.STUDENT_ID + " = ? AND " + AppDbSchema.GradeTable.Cols.EVALUATION_ID + " = ?",
                new String[]{studentId, evaluationId},
                null, null, null
        );

        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(AppDbSchema.GradeTable.Cols.SCORE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    /**
     * ViewHolder class for the EvaluationGradesAdapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView evalName;
        TextView scoreDisplay;
        EditText scoreInput;
        Button saveButton;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The view for the evaluation item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            evalName = itemView.findViewById(R.id.evalName);
            scoreDisplay = itemView.findViewById(R.id.scoreDisplay);
            scoreInput = itemView.findViewById(R.id.scoreInput);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}