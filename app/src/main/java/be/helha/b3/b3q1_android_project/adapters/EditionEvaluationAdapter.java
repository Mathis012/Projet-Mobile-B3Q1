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

public class EditionEvaluationAdapter extends RecyclerView.Adapter<EditionEvaluationAdapter.ViewHolder> {

    private List<Evaluation> evaluations;
    private LayoutInflater inflater;

    public EditionEvaluationAdapter(Context context, List<Evaluation> evaluations) {
        this.evaluations = evaluations;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_evaluation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evaluation evaluation = evaluations.get(position);
        holder.evaluationScore.setText(String.valueOf(evaluation.getScore()));
        holder.evaluationName.setText(evaluation.getName());
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView evaluationScore;
        TextView evaluationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            evaluationScore = itemView.findViewById(R.id.evaluationScore);
            evaluationName = itemView.findViewById(R.id.evaluationName);
        }
    }
}