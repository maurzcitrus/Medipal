package sg.edu.nus.iss.ft08.medipal.category;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.appointment.dummy.DummyContent;
import sg.edu.nus.iss.ft08.medipal.core.Category;

public class CategoryDetailFragment extends Fragment {

    public static final String ARG_CATEGORY_ID = "category_id";

    private OnListFragmentInteractionListener cListener;
    private CategoryRecyclerViewAdapter categoryAdapter;
    private int cat_ColumnCount =1;

    private DummyContent.DummyItem mItem;

    public CategoryDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_CATEGORY_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_CATEGORY_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_detail, container, false);

        if (rootView instanceof RecyclerView) {

            Context context = rootView.getContext();

            RecyclerView recyclerView = (RecyclerView) rootView;
            if (cat_ColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, cat_ColumnCount));
            }

            categoryAdapter = new CategoryRecyclerViewAdapter(getContext(), cListener);
            recyclerView.setAdapter(categoryAdapter);
        }

        return rootView;
    }

    public void onResume() {
        super.onResume();
        categoryAdapter.refresh(getContext());
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Category item);
    }
}
