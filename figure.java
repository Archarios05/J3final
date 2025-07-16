import javax.swing.*;
import java.awt.*;

class Figure {
    protected int x, y, size;
    protected int width, height;
    protected Color color;

    Figure() {
        x = (int) (Math.random() * 450);
        y = (int) (Math.random() * 450);
        size = (int) (Math.random() * 30 + 20);
    }

    void draw(Graphics g) {
    }

    public Figure(int x, int y, int w, int h, Color c) {
  this.x = x; this.y = y;  // this.x, this.y はインスタンス変数を指す．
  width = w; height = h;   // ローカル変数で同名の変数がある場合は，this
  color = c;               // を付けると，インスタンス変数を指す．
}

public void setSize(int w, int h) {
  width = w; height = h;
}
public void setLocation(int x, int y) {
  this.x = x; this.y = y;
}

public void reshape(int x1, int y1, int x2, int y2) {
  int newx = Math.min(x1, x2);
  int newy = Math.min(y1, y2);
  int neww = Math.abs(x1 - x2);
  int newh = Math.abs(y1 - y2);
  setLocation(newx, newy);
  setSize(neww, newh);
}
}

class RectangleFigure extends Figure {
  public RectangleFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
    // 引数付きのコンストラクタは継承されないので，コンストラクタを定義．
    // superで親のコンストラクタを呼び出すだけ．
  }
  public void draw(Graphics g) {
    g.setColor(color);
    g.drawRect(x, y, width, height);
  }
}