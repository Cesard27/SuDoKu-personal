package com.example.sudoku.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding
import com.example.sudoku.viewmodel.PlaySudokuViewModel

class MainActivity : ComponentActivity(), BoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.BoardView.registerListener(this)

        viewModel = ViewModelProvider(this)[PlaySudokuViewModel::class.java]
        viewModel.sudokuModel.selectedCellLiveData.observe(this, Observer {
            updateSelectedCellUI(it)
        })
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.BoardView.updateSelectedCellUI(cell.first, cell.second)

        //boardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, column: Int){
        viewModel.sudokuModel.updateSelectedCell(row, column)
    }
}