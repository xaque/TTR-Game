package cs340.game.client.ViewInterface;

public interface IView {
    void setUp(Object data);
    void update(Object data);
    void onError(String message);
}
