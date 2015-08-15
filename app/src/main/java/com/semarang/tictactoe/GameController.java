package com.semarang.tictactoe;

import android.util.Log;

import java.util.Arrays;


public class GameController {

    private int mMaxTurn = 0;
    private int mTurnNum = 0;
    private String[] mArrayXO;
    private int lastPosition;   //TODO scanWin only on lastPosition
                                //TODO full scan only for AI
    private int numColumn;
    private int[] winArray;
    private boolean isWin;


    public GameController(){

    }

    public boolean isWin() {
        return isWin;
    }

    public int[] getWinArray() {
        return winArray;
    }

    public String getXO(){


        // if even number ="X", odd number= "O"
        if ((mTurnNum&1)==0){ //even
            return "X";
        } else {
            return "O";
        }
    }

    public void setTurnNum(int turnNum){
        this.mTurnNum = turnNum;
    }

    public int getTurnNum(){
        return this.mTurnNum;
    }

    public void setmArrayXO(String[] mArrayXO) {
        this.mArrayXO = mArrayXO;
        this.numColumn = (int) Math.sqrt(mArrayXO.length);
    }

    public void updatemArrayXO(int position){
        mTurnNum++;
        if(mArrayXO[position].equals(" ")) {
            mArrayXO[position] = getXO();

            this.lastPosition = position;
            scanArrayXO();
        } else {
            //TODO Handle user input error
            //throw exception
        }
    }

    public String[] getmArrayXO(){
        return mArrayXO;
    }

    /*
    Scan array in 3 directions
     */
    private void scanArrayXO(){
        boolean[] winCell;
        int column = (int) Math.sqrt(mArrayXO.length);
        String extractString = "";
        int winPosition[];

        //scanning the rows
        for (int i=0; i<numColumn; i++){  //number of rows = number of column
            int start = i*numColumn;      //start at each horizontal row number
            winPosition = scanWinRow(start,start+(numColumn-1));  //column-1 = length of the row
            if (winPosition!= null) {
                Log.d("semarang winRow = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;
                break;
            }
        }

        //scanning the columns
        for (int i=0; i<numColumn; i++){  //number of rows = number of column

            winPosition = scanWinColumn(i);
            if (winPosition!= null) {
                Log.d("semarang winCol = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;
                break;
            }
        }




    }

    /*
    Scanning Row based on start and finish position.
    return int[] of winning position if pattern "XXXXX" or "OOOOO" is found.
    else return null
     */
    private int[] scanWinRow(int start, int finish){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[10];

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);

        //add up the String in each cell in a row
        // i = array no , j = used to populate arrayPos
            for(int i = start, j = 0; i<=finish; i++,j++){
                extractString += mArrayXO[i];
                arrayPos[j]= i;
            }
        //Log.d("semarang string", extractString.toString());

        //indexOf will return int position if substring pattern is found
        int startPattern = extractString.indexOf(pattern);
        if (startPattern==-1){ //if pattern not found will return -1
            return null;
        } else {


            //translate start and finish position to array position
            for (int i = 0; i < 5; i++){
                arrayWin[i] = arrayPos[startPattern+i];
            }

            return arrayWin;
        }

    }

    //scan for column in vertical direction
    private int[] scanWinColumn(int start){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[10];  //for translating pattern position to mArrayXO position

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);

        //add up the String in each cell in a column
        // i = array no , j = used to populate arrayPos
        for(int i = start, j = 0; i<(mArrayXO.length); i+=10, j++){  //vertical column increment by 10
            extractString += mArrayXO[i];
            arrayPos[j]=i;
        }
        //Log.d("semarang stringcol=", extractString.toString());

        //indexOf will return int position if substring pattern is found
        int startPattern = extractString.indexOf(pattern);
        if (startPattern==-1){ //if pattern not found will return -1
            return null;
        } else {

            //translate start and finish position to array position
            for (int i = 0; i < 5; i++){
                arrayWin[i] = arrayPos[startPattern+i];
            }

            return arrayWin;
        }

    }







}