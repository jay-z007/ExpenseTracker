package com.example.jaybhatt.expensetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaybhatt.expensetracker.ExpenditureFragment.OnListFragmentInteractionListener;
import com.example.jaybhatt.expensetracker.Model.Expenditure;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyExpenditureRecyclerViewAdapter extends RecyclerView.Adapter<MyExpenditureRecyclerViewAdapter.ViewHolder> {

    private final List<Expenditure> mValues;
    private final OnListFragmentInteractionListener mListener;
    private ArrayList<Integer> mSelected;
    private ExpenditureFragment mExpenditureFragment;

    public MyExpenditureRecyclerViewAdapter(List<Expenditure> items, OnListFragmentInteractionListener listener, ExpenditureFragment expenditureFragment) {
        mValues = items;
        mListener = listener;
        mExpenditureFragment = expenditureFragment;
        mSelected = new ArrayList<>();
    }

    public void toggleSelected(int index) {
        final boolean newState = !mSelected.contains(index);
        if (newState)
            mSelected.add(index);
        else
            mSelected.remove((Integer) index);
        notifyItemChanged(index);
    }

    public int getSelectedCount() {
        return mSelected.size();
    }

    public void clearSelected() {
        mSelected.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_expenditure, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mView.setActivated(mSelected.contains(position));
        holder.mView.setTag("item:" + position);
        holder.mItem = mValues.get(position);
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        holder.mAmountView.setText(String.format("%f", mValues.get(position).getAmount()));
        holder.mDateView.setText(mValues.get(position).getDate());
        holder.mCategoryView.setText(mValues.get(position).getCategory().name());
        holder.mTypeView.setText(mValues.get(position).getExpenseType().name());
        holder.mView.setOnLongClickListener(mExpenditureFragment);
        holder.mView.setOnClickListener(mExpenditureFragment);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        return mSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    {     public final View mView;
        public final TextView mDescriptionView;
        public final TextView mAmountView;
        public final TextView mDateView;
        public final TextView mCategoryView;
        public final TextView mTypeView;
        public Expenditure mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescriptionView = (TextView) view.findViewById(R.id.text_view_expense_description);
            mAmountView = (TextView) view.findViewById(R.id.text_view_expense_amount);
            mDateView = (TextView) view.findViewById(R.id.text_view_expense_date);
            mCategoryView = (TextView) view.findViewById(R.id.text_view_expense_category);
            mTypeView = (TextView) view.findViewById(R.id.text_view_expense_type);
            view.setOnLongClickListener(mExpenditureFragment);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mDescriptionView=" + mDescriptionView +
                    ", mAmountView=" + mAmountView +
                    ", mDateView=" + mDateView +
                    ", mCategoryView=" + mCategoryView +
                    ", mTypeView=" + mTypeView +
                    ", mItem=" + mItem +
                    '}';
        }

    }
}
