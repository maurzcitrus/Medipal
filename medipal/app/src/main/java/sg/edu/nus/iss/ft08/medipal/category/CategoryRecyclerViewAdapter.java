package sg.edu.nus.iss.ft08.medipal.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Category;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>
{
    private final CategoryDetailFragment.OnListFragmentInteractionListener cListener;
    private List<Category> categoryList;

    public CategoryRecyclerViewAdapter(Context context, CategoryDetailFragment.OnListFragmentInteractionListener listener) {
        categoryList = findAllCategory(context);
        cListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Category current = categoryList.get(position);
        holder.mItem = current;
        holder.mCategoryName.setText(current.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != cListener) {

                    cListener.onListFragmentInteraction(holder.mItem);
                }

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_list_content, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCategoryName;
        public final TextView mCategoryCode;
        public final TextView mCategoryDescription;
        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCategoryName = (TextView) view.findViewById(R.id.category_name);
            mCategoryCode = (TextView) view.findViewById(R.id.category_code);
            mCategoryDescription = (TextView) view.findViewById(R.id.category_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCategoryName.getText() + "'";
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void refresh(Context context){
        categoryList = findAllCategory(context);
        notifyDataSetChanged();
    }

    private List<Category> findAllCategory(Context context) {
        List<Category> records = null;

        try {
            FindAllQuery query = new FindAllQuery(context);
            query.execute();
            records = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (records == null) {
            records = new ArrayList<Category>();
        }

        return records;
    }
}
