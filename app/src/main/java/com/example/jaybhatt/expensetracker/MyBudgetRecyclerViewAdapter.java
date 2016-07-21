package com.example.jaybhatt.expensetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaybhatt.expensetracker.BudgetFragment.OnListFragmentInteractionListener;
import com.example.jaybhatt.expensetracker.Model.Budget;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBudgetRecyclerViewAdapter extends RecyclerView.Adapter<MyBudgetRecyclerViewAdapter.ViewHolder> {

    private final List<Budget> mValues;
    private final OnListFragmentInteractionListener mListener;
    private BudgetFragment mBudgetFragment;

    public MyBudgetRecyclerViewAdapter(List<Budget> items, OnListFragmentInteractionListener listener, BudgetFragment budgetFragment) {
        mValues = items;
        mListener = listener;
        mBudgetFragment = budgetFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_budget, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        holder.mAmountView.setText(String.format("%f", mValues.get(position).getAmount()));
        holder.mDateView.setText(mValues.get(position).getDate().toString());
        holder.mSavingView.setText(String.format("%f", mValues.get(position).getSaving()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final TextView mAmountView;
        public final TextView mDateView;
        public final TextView mSavingView;
        public Budget mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.text_view_budget_title);
            mDescriptionView = (TextView) view.findViewById(R.id.text_view_budget_description);
            mAmountView = (TextView) view.findViewById(R.id.text_view_budget_amount);
            mDateView = (TextView) view.findViewById(R.id.text_view_budget_date);
            mSavingView = (TextView) view.findViewById(R.id.text_view_budget_saving);
            view.setOnLongClickListener(mBudgetFragment);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mTitleView=" + mTitleView +
                    ", mDescriptionView=" + mDescriptionView +
                    ", mAmountView=" + mAmountView +
                    ", mDateView=" + mDateView +
                    ", mSavingView=" + mSavingView +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
