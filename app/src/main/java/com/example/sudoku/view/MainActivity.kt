package com.example.sudoku.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sudoku.R
import com.example.sudoku.viewmodel.PlaySudokuViewModel

class MainActivity : ComponentActivity(), BoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        viewModel = ViewModelProvider(this)[PlaySudokuViewModel::class.java]
        viewModel.sudokuModel.selectedCellLiveData.observe(this, Observer {
            updateSelectedCellUI(it)
        })
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        //boardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, column: Int){
        viewModel.sudokuModel.updateSelectedCell(row, column)
    }
}