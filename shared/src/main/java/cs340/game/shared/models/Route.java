package cs340.game.shared.models;

import java.util.Objects;

import cs340.game.shared.City;
import cs340.game.shared.Color;

public class Route {

    private City city1;
    private City city2;
    private Color color;
    private int length;
    private String playerOnRoute;

    public boolean connectsToCity(City city){
        boolean hasCity1 = (city1 == city);
        boolean hasCity2 = (city2 == city);

        return (hasCity1 || hasCity2);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPlayerOnRoute() {
        return playerOnRoute;
    }

    public void setPlayerOnRoute(String playerOnRoute) {
        this.playerOnRoute = playerOnRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }

        Route route = (Route) o;
        boolean city1EqualsCity1 = (this.city1 == route.getCity1());
        boolean city2EqualsCity2 = (this.city2 == route.getCity2());
        boolean city1EqualsCity2 = (this.city1 == route.getCity2());
        boolean city2EqualsCity1 = (this.city2 == route.getCity1());

        if (this.color == route.getColor()){
            if (city1EqualsCity1 && city2EqualsCity2) {
                return true;
            }
            if (city1EqualsCity2 && city2EqualsCity1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(city1, city2);
    }
}
