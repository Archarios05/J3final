import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

class Figure implements Serializable {
    private static final long serialVersionUID = 1L;
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

// 塗り潰し四角形クラス
class FilledRectangleFigure extends Figure {
  public FilledRectangleFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
  }
  public void draw(Graphics g) {
    g.setColor(color);
    g.fillRect(x, y, width, height);
  }
}

// 円クラス
class CircleFigure extends Figure {
  public CircleFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
  }
  public void draw(Graphics g) {
    g.setColor(color);
    g.drawOval(x, y, width, height);
  }
}

// 塗り潰し円クラス
class FilledCircleFigure extends Figure {
  public FilledCircleFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
  }
  public void draw(Graphics g) {
    g.setColor(color);
    g.fillOval(x, y, width, height);
  }
}

// 直線クラス
class LineFigure extends Figure {
  protected int endX, endY;
  
  public LineFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
    endX = x + w;
    endY = y + h;
  }
  
  @Override
  public void reshape(int x1, int y1, int x2, int y2) {
    this.x = x1;
    this.y = y1;
    this.endX = x2;
    this.endY = y2;
  }
  
  public void draw(Graphics g) {
    g.setColor(color);
    g.drawLine(x, y, endX, endY);
  }
}