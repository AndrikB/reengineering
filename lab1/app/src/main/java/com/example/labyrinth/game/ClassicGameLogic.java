package com.example.labyrinth.game;

import android.util.Size;

public final class ClassicGameLogic extends GameLogic{
    public ClassicGameLogic(Size size, long seed) {
        super(size, seed);
    }

    @Override
    protected boolean shouldContinueMove() {
        return couldTurnMove() && !checkWin();
    }
}
