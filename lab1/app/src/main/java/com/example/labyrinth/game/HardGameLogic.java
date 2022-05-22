package com.example.labyrinth.game;

import android.util.Size;

public final class HardGameLogic extends GameLogic {
    public HardGameLogic(Size size, long seed) {
        super(size, seed);
    }

    @Override
    protected boolean shouldContinueMove() {
        return false;
    }
}
