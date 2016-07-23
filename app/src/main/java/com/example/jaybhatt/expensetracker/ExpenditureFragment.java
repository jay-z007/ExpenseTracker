package com.example.jaybhatt.expensetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialcab.MaterialCab;
import com.example.jaybhatt.expensetracker.Model.Budget;
import com.example.jaybhatt.expensetracker.Model.Expenditure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExpenditureFragment extends Fragment implements View.OnLongClickListener, MyCallback, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String CAB = "context_action_bar";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private MyExpenditureRecyclerViewAdapter adapter;
    private List<Expenditure> expenses;
    private MaterialCab cab;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ExpenditureFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExpenditureFragment newInstance(MaterialCab cab) {
        ExpenditureFragment fragment = new ExpenditureFragment();
        Bundle args = new Bundle();
        args.putSerializable(CAB, cab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cab = (MaterialCab) getArguments().get(CAB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure_list, container, false);
        expenses = Expenditure.listAll(Expenditure.class);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*} else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }*/
            adapter = new MyExpenditureRecyclerViewAdapter(expenses, mListener, this);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    MaterialCab.Callback callback = new MaterialCab.Callback() {
        @Override
        public boolean onCabCreated(MaterialCab cab, Menu menu) {
            // The CAB was started, return true to allow creation to continue.
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Field field = menu.getClass().
                            getDeclaredField("mOptionalIconsVisible");
                    field.setAccessible(true);
                    field.setBoolean(menu, true);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
            return true; // allow creation
        }

        @Override
        public boolean onCabItemClicked(MenuItem item) {
            // An item in the toolbar or overflow menu was tapped.
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteSelectedItems();
                    cab.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCabFinished(MaterialCab cab) {
            // The CAB was finished, return true to allow destruction to continue.
            adapter.clearSelected();
            return true; // allow destruction
        }
    };

    private void deleteSelectedItems() {
        Expenditure expenditure;
        ArrayList<Integer> mSelected = adapter.getSelectedItems();
        for (Integer i : mSelected) {
            expenditure = Budget.findById(Expenditure.class, expenses.get(i).getId());
            expenditure.delete();
        }
        expenses.removeAll(expenses);
        expenses.addAll(Expenditure.listAll(Expenditure.class));
        adapter.notifyItemRangeChanged(0, expenses.size());
    }

    @Override
    public boolean onLongClick(View v) {
        String[] tag = ((String) v.getTag()).split(":");
        int index = Integer.parseInt(tag[1]);
        onItemClicked(index, true);

        return true;
    }

    @Override
    public void onClick(View v) {
        String[] tag = ((String) v.getTag()).split(":");
        int index = Integer.parseInt(tag[1]);
        onItemClicked(index, false);
    }

    @Override
    public void onItemClicked(int index, boolean longClick) {
        if (longClick || (cab != null && cab.isActive())) {
            onIconClicked(index);
        }
    }

    @Override
    public void onIconClicked(int index) {
        adapter.toggleSelected(index);
        if (adapter.getSelectedCount() == 0) {
            cab.finish();
        } else if (!cab.isActive())
            cab.start(callback);
        //       cab.setTitle(getString(R.string.x_selected, adapter.getSelectedCount()));
    }

    @Override
    public void onResume() {
        super.onResume();
        expenses.removeAll(expenses);
        expenses.addAll(Expenditure.listAll(Expenditure.class));
        adapter.notifyItemRangeChanged(0, expenses.size());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Expenditure item);
    }

/*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);//let the adapter handle setting up the row views
            v.setBackgroundColor(getResources().getColor(android.R.color.background_light)); //default color

            if (mSelection.get(position) != null) {
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));// this is a selected position so make it red
            }
            return v;
        }
    }*/
}
