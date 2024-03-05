package com.example.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sudoku.model.SudokuModel

class PlaySudokuViewModel : ViewModel() {
    val sudokuModel = SudokuModel()
}