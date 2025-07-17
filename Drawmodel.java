import java.util.ArrayList;
import java.awt.Color;
import java.util.List;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

class DrawModel {
  protected ArrayList<Figure> fig;
  protected Figure drawingFigure;
  protected Color currentColor;
  protected List<ModelObserver> observers;
  protected String currentFigureType; // 現在選択されている図形タイプ
 
  public DrawModel() {
    fig = new ArrayList<Figure>();
    drawingFigure = null;
    currentColor = Color.RED;  // Color.red は deprecated なので Color.RED に変更
    observers = new ArrayList<ModelObserver>();
    currentFigureType = "Rectangle"; // デフォルトは四角形
  }

  // コピペから非推奨機能を削除、observableはjava9から非推奨
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
    Figure f = null;
    
    // 現在選択されている図形タイプに応じて図形を生成
    switch(currentFigureType) {
      case "Rectangle":
        f = new RectangleFigure(x, y, 0, 0, currentColor);
        break;
      case "FilledRectangle":
        f = new FilledRectangleFigure(x, y, 0, 0, currentColor);
        break;
      case "Circle":
        f = new CircleFigure(x, y, 0, 0, currentColor);
        break;
      case "FilledCircle":
        f = new FilledCircleFigure(x, y, 0, 0, currentColor);
        break;
      case "Line":
        f = new LineFigure(x, y, 0, 0, currentColor);
        break;
      default:
        f = new RectangleFigure(x, y, 0, 0, currentColor);
    }
    
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

  // シリアライズによる保存機能
  //バイナリファイルで画像を保存している
  //ArrayList<Figure>を保存している

  public void saveFigures(String filename) {
    try {
      FileOutputStream fileOut = new FileOutputStream(filename);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(fig);
      out.close();
      fileOut.close();
      System.out.println("図形データを " + filename + " に保存しました");
    } catch (IOException e) {
      System.out.println("保存エラー: " + e.getMessage());
    }
  }

  // シリアライズによる読み込み機能
  // バイナリファイルからArrayList<Figure>を読み込む
  // 読み込んだ図形はfigに格納される
  @SuppressWarnings("unchecked")
  public void loadFigures(String filename) {
    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      fig = (ArrayList<Figure>) in.readObject();
      in.close();
      fileIn.close();
      drawingFigure = null;
      notifyObservers();
      System.out.println("図形データを " + filename + " から読み込みました");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("読み込みエラー: " + e.getMessage());
    }
  }

  // 画像ファイルとして出力
  //BufferedImageを使用して図形を描画し、PNG形式で保存
  public void saveAsImage(String filename, int width, int height) {
    try {
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = image.createGraphics();
      
      // 背景を白で塗りつぶし
      g2d.setColor(Color.WHITE);
      g2d.fillRect(0, 0, width, height);
      
      // 全ての図形を描画
      for (Figure figure : fig) {
        figure.draw(g2d);
      }
      
      g2d.dispose();
      
      // PNG形式で保存
      File outputFile = new File(filename);
      ImageIO.write(image, "png", outputFile);
      System.out.println("画像を " + filename + " に保存しました");
    } catch (IOException e) {
      System.out.println("画像保存エラー: " + e.getMessage());
    }
  }

  // 図形タイプを設定するメソッド
  public void setCurrentFigureType(String figureType) {
    currentFigureType = figureType;
    notifyObservers();
  }

  public String getCurrentFigureType() {
    return currentFigureType;
  }

  // 全図形をクリア
  public void clearAllFigures() {
    fig.clear();
    drawingFigure = null;
    notifyObservers();
  }
}
