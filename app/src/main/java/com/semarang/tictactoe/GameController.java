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
    private void scanArrayXO() {
        boolean[] winCell;
        int column = (int) Math.sqrt(mArrayXO.length);
        String extractString = "";
        int winPosition[] = null;

        //TODO diagonal logic algorithm optimization
        //TODO change below logic to switch

        //scanning the rows
        if (scanningTheRows()) return;
        //scanning the columns
        if (scanningTheColumns()) return;
        // diagonal from left to right
        if (scanningDiagLeft_1st()) return;
        if (scanningDiagLeft_2nd()) return;
        // diagonal from right to left
        if (scanningDiagRight_1st()) return;
        if (scanningDiagRight_2nd()) return;


    }

    private boolean scanningDiagRight_2nd() {
        int[] winPosition;
        for (int i = numColumn - 1; i < (mArrayXO.length - (4 * numColumn)); i += 10) {
            winPosition = scanWinDiagRight2(i);
            if (winPosition != null) {
                Log.d("semarang winCol = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;

                return true;
            }
        }
        return false;
    }

    private boolean scanningDiagRight_1st() {
        int[] winPosition;
        for (int i = 4; i < (numColumn); i++) {

                winPosition = scanWinDiagRight(i);
                if (winPosition != null) {
                    Log.d("semarang winCol = ", Arrays.toString(winPosition));
                    winArray = winPosition;
                    isWin = true;
                    return true;
                }
            }
        return false;
    }

    private boolean scanningDiagLeft_2nd() {
        int[] winPosition;
        for (int i = 0; i < (mArrayXO.length - (4 * numColumn)); i += 10) {

                winPosition = scanWinDiagLeft2(i);
                if (winPosition != null) {
                    Log.d("semarang winCol = ", Arrays.toString(winPosition));
                    winArray = winPosition;
                    isWin = true;
                    return true;
                }
            }
        return false;
    }

    private boolean scanningDiagLeft_1st() {
        int[] winPosition;
        for (int i = 0; i < (numColumn - 4); i++) {

            winPosition = scanWinDiagLeft(i);
            if (winPosition != null) {
                Log.d("semarang winCol = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;
                return true;
            }
        }
        return false;
    }

    private boolean scanningTheColumns() {
        int[] winPosition;
        for (int i = 0; i < numColumn; i++) {  //number of rows = number of column
            winPosition = scanWinColumn(i);
            if (winPosition != null) {
                Log.d("semarang winCol = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;
                return true;
            }
        }
        return false;
    }

    private boolean scanningTheRows() {
        int[] winPosition;
        for (int i = 0; i < numColumn; i++) {  //number of rows = number of column
            int start = i * numColumn;      //start at each horizontal row number
            winPosition = scanWinRow(start, start + (numColumn - 1));  //column-1 = length of the row
            if (winPosition != null) {
                Log.d("semarang winRow = ", Arrays.toString(winPosition));
                winArray = winPosition;
                isWin = true;
                return true;
            }
        }
        return false;
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
        int arrayPos[] = new int[numColumn];

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
        int arrayPos[] = new int[numColumn];  //for translating pattern position to mArrayXO position

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

    //scan for column in diagonal direction
    private int[] scanWinDiagLeft(int start){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[numColumn];  //for translating pattern position to mArrayXO position

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);

        //add up the String in each cell in a column
        // i = array no , j = used to populate arrayPos
        for(int i = start, j = 0; i<=(mArrayXO.length-((10*start)+1)); i+=11, j++){
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

    //scan for column in diagonal direction
    private int[] scanWinDiagLeft2(int start){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[numColumn];  //for translating pattern position to mArrayXO position

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);

        //add up the String in each cell in a column
        // i = cell no , j = used to populate arrayPos
        for(int i = start, j = 0; i<=(mArrayXO.length-((start/10)+1)); i+=11, j++){  //TODO optimize this code
            extractString += mArrayXO[i];
            arrayPos[j]=i;
        }
        int startPattern2 = extractString.indexOf(pattern);
        if (startPattern2 ==-1) { //if pattern not found will return -1
            return null;

        } else {
            //translate start and finish position to array position
            for (int i = 0; i < 5; i++){
                arrayWin[i] = arrayPos[startPattern2+i];
            }

            return arrayWin;
        }

    }




    //scan for column in diagonal direction
    private int[] scanWinDiagRight(int start){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[numColumn];  //for translating pattern position to mArrayXO position

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);

        //add up the String in each cell in a column
        // i = array no , j = used to populate arrayPos
        for(int i = start, j = 0; i<=((10*start)); i+=9, j++){  //TODO optimize this code
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

    //scan for column in diagonal direction
    private int[] scanWinDiagRight2(int start){

        String extractString = "";
        int startWinRow;
        int finishWinRow;
        int arrayWin[] = new int[5];
        int arrayPos[] = new int[numColumn];  //for translating pattern position to mArrayXO position

        //setup pattern for reference
        String pattern = "XXXXX";
        if (getXO().equals("O")){
            pattern = "OOOOO";

        }
        //Log.d("semarang O or X=", pattern);



            //add up the String in each cell in a column
            // i = array no , j = used to populate arrayPos
            for(int i = start, j = 0; i<=(mArrayXO.length-4); i+=9, j++){ //TODO optimize this code
                extractString += mArrayXO[i];
                arrayPos[j]=i;
            }
            int startPattern2 = extractString.indexOf(pattern);
            if (startPattern2 ==-1) { //if pattern not found will return -1
                return null;

            } else {
                //translate start and finish position to array position
                for (int i = 0; i < 5; i++){
                    arrayWin[i] = arrayPos[startPattern2+i];
                }

                return arrayWin;
            }




    }

}