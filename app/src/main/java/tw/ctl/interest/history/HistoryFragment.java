package tw.ctl.interest.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import tw.ctl.interest.Entity;
import tw.ctl.interest.R;

/**
 * Created by jacklai on 2017/3/1.
 */

public class HistoryFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.description)   TextView description;

    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        setRecyclerView();
        fetchData();

        return view;
    }

    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void fetchData() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Entity> entities = realm.where(Entity.class).findAllSorted("date", Sort.DESCENDING);

        recyclerView.setVisibility(entities.size() == 0 ? View.GONE : View.VISIBLE);
        description.setVisibility(entities.size() == 0 ? View.VISIBLE : View.GONE);

        entities.addChangeListener(new RealmChangeListener<RealmResults<Entity>>() {
            @Override
            public void onChange(RealmResults<Entity> elements) {
                recyclerView.setVisibility(elements.size() == 0 ? View.GONE : View.VISIBLE);
                description.setVisibility(elements.size() == 0 ? View.VISIBLE : View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setEntities(entities);
    }

}
