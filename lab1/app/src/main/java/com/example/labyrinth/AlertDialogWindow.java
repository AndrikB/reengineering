package com.example.labyrinth;


import android.app.AlertDialog;
import android.content.DialogInterface;


public class AlertDialogWindow {
    public static void showDialogWindow(final Screen context) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("YOU WIN!")
                .setMessage("Do you want to start new game?")
                .setPositiveButton("Yes", (dialog, which) -> context.startNewGame())
                .setNegativeButton("No", (dialog, which) -> context.returnToMainMenu())
                .show();
    }
}