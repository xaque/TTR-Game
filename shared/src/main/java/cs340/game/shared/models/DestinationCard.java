package cs340.game.shared.models;

import java.io.Serializable;

import cs340.game.shared.City;

public class DestinationCard implements Serializable {

    private City city1;
    private City city2;
    private int pointValue;

    public DestinationCard(City _city1, City _city2, int _pointValue){
        city1 = _city1;
        city2 = _city2;
        pointValue = _pointValue;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public int getPointValue() {
        return this.pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }
}
