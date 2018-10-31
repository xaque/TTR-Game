package cs340.game.shared.models;

import java.io.Serializable;

import cs340.game.shared.Color;

public class TrainCard implements Serializable {

    private Color color;

    public TrainCard(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
