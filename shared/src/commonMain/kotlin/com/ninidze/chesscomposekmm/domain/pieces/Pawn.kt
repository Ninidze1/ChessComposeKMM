package com.ninidze.chesscomposekmm.domain.pieces

import com.ninidze.chesscomposekmm.domain.base.ChessPiece
import com.ninidze.chesscomposekmm.domain.model.ChessBoard
import com.ninidze.chesscomposekmm.domain.model.PieceColor
import com.ninidze.chesscomposekmm.domain.model.PieceColor.White
import com.ninidze.chesscomposekmm.domain.model.PieceType
import com.ninidze.chesscomposekmm.domain.model.PieceType.Pawn
import com.ninidze.chesscomposekmm.domain.model.Position
import com.ninidze.chesscomposekmm.domain.movement.PawnMoveStrategy

class Pawn(
    color: PieceColor,
    position: Position,
    override val moveStrategy: PawnMoveStrategy = PawnMoveStrategy()
) : ChessPiece(color, position) {

    override val type: PieceType = Pawn

    override fun getUnfilteredMoves(chessBoard: ChessBoard): List<Position> {
        return moveStrategy.getMoves(this, chessBoard)
    }

    override fun getPotentialCaptures(chessBoard: ChessBoard): List<Position> {
        val direction = if (color == White) -1 else 1
        return listOf(
            Position(position.row + direction, position.column + 1),
            Position(position.row + direction, position.column - 1)
        ).filter { it.isValidPosition() }
    }
}
