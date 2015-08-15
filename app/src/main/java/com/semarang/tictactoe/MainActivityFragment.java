package com.semarang.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements AdapterView.OnItemClickListener {

    protected String[] mArray_XO;
    private int mGridNum = 100; //TODO: make this adjustable in setting
    private GridCustomAdapter mArrayAdapter;
    public static final String ARRAY_XO_ID = "ArrayXOID";
    public static final String TURN_ID = "TurnID";
    public GameController gameController;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameController = new GameController();
        if (savedInstanceState == null) {
            setmArray_XO(mGridNum); //set dummy array
            gameController.setmArrayXO(mArray_XO);
            gameController.setTurnNum(1);
        } else {
            mArray_XO = savedInstanceState.getStringArray(ARRAY_XO_ID);
            gameController.setmArrayXO(mArray_XO);
            gameController.setTurnNum(savedInstanceState.getInt(TURN_ID));

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mArrayAdapter = new GridCustomAdapter(rootView.getContext(), R.layout.cell, mArray_XO);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setNumColumns((int) Math.sqrt(mGridNum)); //set grid column based on mGridNum
        gridView.setAdapter(mArrayAdapter);
        gridView.setOnItemClickListener(this);


        return rootView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this.getActivity(),"Player X Turn", Toast.LENGTH_LONG).show();


        gameController.updatemArrayXO(position);
        mArray_XO = gameController.getmArrayXO();
        if(gameController.isWin()){
            int[] winArray = gameController.getWinArray();
            mArrayAdapter.setArrayWin(winArray);
            //TODO notify user
            //TODO reset the game
        }
        mArrayAdapter.notifyDataSetChanged();

    }

    //Temporary array generator with dummy string
    public void setmArray_XO(int numberOfGrid){
        mArray_XO = new String[numberOfGrid];
        for (int i = 0; i < mArray_XO.length; i++){
            mArray_XO[i]=" ";
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mArray_XO!=null) {
            outState.putStringArray(ARRAY_XO_ID, mArray_XO);
            outState.putInt(TURN_ID,gameController.getTurnNum());
        }

            super.onSaveInstanceState(outState);

    }





}
