package com.ninidze.chesscomposekmm.domain.usecase

import com.ninidze.chesscomposekmm.data.helper.Resource
import com.ninidze.chesscomposekmm.domain.engine.Move
import com.ninidze.chesscomposekmm.domain.engine.search.SearchEngine
import com.ninidze.chesscomposekmm.domain.model.ChessBoard
import com.ninidze.chesscomposekmm.util.Constants.INVALID_MOVE_MESSAGE
import com.ninidze.chesscomposekmm.util.Constants.PIECE_NOT_FOUND
import com.ninidze.chesscomposekmm.util.FenConverter
import com.ninidze.chesscomposekmm.util.extensions.movePiece
import com.ninidze.chesscomposekmm.util.extensions.toPosition

class MoveAIUseCase(
    private val chessBoardConverter: FenConverter,
    private val engine: SearchEngine
) {
    operator fun invoke(chessBoard: ChessBoard): Resource<ChessBoard> {
        val calculatedMove = calculateBotMove(chessBoard).toPosition()
        val piece = chessBoard.getPieceAtPosition(calculatedMove.first)
            ?: return Resource.Failure(PIECE_NOT_FOUND)
        val targetPosition = calculatedMove.second

        return if (piece.isValidMove(chessBoard, targetPosition)) {
            val updatedChessBoard = chessBoard.movePiece(piece, targetPosition)
            Resource.Success(updatedChessBoard)
        } else {
            Resource.Failure(INVALID_MOVE_MESSAGE)
        }
    }

    private fun calculateBotMove(chessBoard: ChessBoard): String {
        engine.board.setupWithFen(
            engine = engine,
            fen = chessBoardConverter.convertToFen(chessBoard)
        )
        return Move.toString(engine.bestMove)
    }

}
