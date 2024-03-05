package com.example.sudoku.model

import androidx.lifecycle.MutableLiveData

class SudokuModel {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()

    private var selectedRow = -1
    private var selectedCol = -1

    init {
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
    }

    fun updateSelectedCell(row: Int, col: Int){
        selectedCol = col
        selectedRow = row
        selectedCellLiveData.postValue(Pair(row, col))

    }

}