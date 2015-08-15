package com.semarang.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
CustomAdapter to be able to highlight winning cells
to use Photos/Picture in future development
 */
public class GridCustomAdapter extends ArrayAdapter<String> {

    private int[] arrayWin;

    public GridCustomAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }


    public void setArrayWin(int[] arrayWin) {
        this.arrayWin = arrayWin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        String stringXO = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell,parent,false);
        TextView textXO = (TextView) convertView.findViewById(R.id.text_xo);
        textXO.setText(stringXO);

        if (arrayWin!=null) {
            for (int i : arrayWin) {
               if(position==i){
                   textXO.setBackgroundColor(Color.WHITE);
               }
            }
        }

        return convertView;

    }
}
