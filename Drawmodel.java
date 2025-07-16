import java.util.ArrayList;
import java.awt.Color;
import java.util.List;

class DrawModel {
  protected ArrayList<Figure> fig;
  protected Figure drawingFigure;
  protected Color currentColor;
  protected List<ModelObserver> observers;
 
  public DrawModel() {
    fig = new ArrayList<Figure>();
    drawingFigure = null;
    currentColor = Color.RED;  // Color.red は deprecated なので Color.RED に変更
    observers = new ArrayList<ModelObserver>();
  }

  // Observer パターンの代替実装
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }
  
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }
  
  protected void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  public ArrayList<Figure> getFigures() {
    return fig;
  }
  
  public Figure getFigure(int idx) {
    return fig.get(idx);
  }

  public void createFigure(int x,int y) {
    Figure f = new RectangleFigure(x, y, 0, 0, currentColor);
    fig.add(f);
    drawingFigure = f;
    notifyObservers();
  }

  // 色を変更するメソッド
  public void setCurrentColor(Color color) {
    currentColor = color;
    notifyObservers();
  }

  public Color getCurrentColor() {
    return currentColor;
  }
   
  public void reshapeFigure(int x1, int y1, int x2, int y2) {
    if (drawingFigure != null) {
      drawingFigure.reshape(x1, y1, x2, y2);
      notifyObservers();
    }
  }
}

// Observer インターフェースの代替
interface ModelObserver {
  void update(DrawModel model);
}
