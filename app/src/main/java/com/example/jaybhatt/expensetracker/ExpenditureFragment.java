package com.example.jaybhatt.expensetracker;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialcab.MaterialCab;
import com.example.jaybhatt.expensetracker.Model.Expenditure;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExpenditureFragment extends Fragment implements View.OnLongClickListener {

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

/*    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.cab_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_delete:
                    Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_LONG).show();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };*/

    MaterialCab.Callback callback = new MaterialCab.Callback() {
        @Override
        public boolean onCabCreated(MaterialCab cab, Menu menu) {
            // The CAB was started, return true to allow creation to continue.
            return true;
        }

        @Override
        public boolean onCabItemClicked(MenuItem item) {
            // An item in the toolbar or overflow menu was tapped.
            return true;
        }

        @Override
        public boolean onCabFinished(MaterialCab cab) {
            // The CAB was finished, return true to allow destruction to continue.

            return true;
        }
    };


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

    @Override
    public boolean onLongClick(View v) {
        /*if(mActionMode != null)
            return false;

        mActionMode = getActivity().startActionMode(callback);
        v.setSelected(true);
        return true;*/

        if(cab.isActive())
        {
            cab.finish();
            return false;
        }


        cab.start(callback);
        v.setSelected(true);
        v.setBackgroundColor(Color.GREEN);
        return true;
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

    /*private class SelectionAdapter extends ArrayAdapter<String> {

        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public SelectionAdapter(Context context, int resource,
                                int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

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
