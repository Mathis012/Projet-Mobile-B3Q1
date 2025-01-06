package be.helha.b3.b3q1_android_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.helha.b3.b3q1_android_project.R;
import be.helha.b3.b3q1_android_project.models.Evaluation;

/**
 * Adapter class for displaying a list of evaluations in a RecyclerView.
 */
public class EditionEvaluationAdapter extends RecyclerView.Adapter<EditionEvaluationAdapter.ViewHolder> {

    private List<Evaluation> evaluations;
    private LayoutInflater inflater;

    /**
     * Constructor for the EditionEvaluationAdapter.
     * @param context The context in which the adapter is used.
     * @param evaluations The list of evaluations to display.
     */
    public EditionEvaluationAdapter(Context context, List<Evaluation> evaluations) {
        this.evaluations = evaluations;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Creates a new ViewHolder instance.
     * @param parent The parent view.
     * @param viewType The view type.
     * @return A new ViewHolder instance.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_evaluation, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder.
     * @param holder The ViewHolder instance.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evaluation evaluation = evaluations.get(position);
        holder.evaluationScore.setText(String.valueOf(evaluation.getScore()));
        holder.evaluationName.setText(evaluation.getName());

        int level = getLevel(evaluation);
        holder.itemView.setPadding(level * 50, 0, 0, 0);
    }

    /**
     * Gets the number of items in the list.
     * @return The number of items in the list.
     */
    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    /**
     * Calculates the hierarchical level of an evaluation.
     * @param evaluation The evaluation to calculate the level for.
     * @return The hierarchical level of the evaluation.
     */
    private int getLevel(Evaluation evaluation) {
        int level = 0;
        String parentId = evaluation.getParentEvaluationId();
        while (parentId != null) {
            level++;
            Evaluation parent = findParentById(parentId);
            if (parent == null) break;
            parentId = parent.getParentEvaluationId();
        }
        return level;
    }

    /**
     * Finds the parent evaluation by its ID.
     * @param parentId The ID of the parent evaluation.
     * @return The parent evaluation, or null if not found.
     */
    private Evaluation findParentById(String parentId) {
        for (Evaluation eval : evaluations) {
            if (eval.getId().toString().equals(parentId)) {
                return eval;
            }
        }
        return null;
    }

    /**
     * ViewHolder class for the EditionEvaluationAdapter.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView evaluationScore;
        TextView evaluationName;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The view for the evaluation item.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            evaluationScore = itemView.findViewById(R.id.evaluationScore);
            evaluationName = itemView.findViewById(R.id.evaluationName);
        }
    }
}