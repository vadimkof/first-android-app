package com.example.vadim.myapplication;

import android.graphics.Color;

public enum Colors {

    BLACK("#000000"),

    BLUE("#2196F3"),

    YELLOW("#FFFF00"),

    RED("#F44336"),

    BROWN("#795548"),

    GREEN("#1B5E20"),

    PINK("#F50057");

    final String color;

    Colors(String color) {
        this.color = color;
    }

    public int getCodeColor() {
        return Color.parseColor(color);
    }

}
