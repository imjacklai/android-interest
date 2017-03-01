package tw.ctl.interest.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import tw.ctl.interest.Entity;
import tw.ctl.interest.R;

/**
 * Created by jacklai on 2017/3/1.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private RealmResults<Entity> entities;

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entity, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        Entity entity = entities.get(position);
        holder.simpleResult.setText(entity.simpleResult);
        holder.compoundResult.setText(entity.compoundResult);
        holder.investResult.setText(entity.investResult);
        holder.principal.setText(entity.principal);
        holder.interest.setText(entity.interest);
        holder.period.setText(entity.period);
        holder.invest.setText(entity.invest);
    }

    @Override
    public int getItemCount() {
        return entities == null ? 0 : entities.size();
    }

    public void setEntities(RealmResults<Entity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.simple_interest_result)   TextView simpleResult;
        @BindView(R.id.compound_interest_result) TextView compoundResult;
        @BindView(R.id.invest_interest_result)   TextView investResult;
        @BindView(R.id.principal) TextView principal;
        @BindView(R.id.interest)  TextView interest;
        @BindView(R.id.period)    TextView period;
        @BindView(R.id.invest)    TextView invest;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
