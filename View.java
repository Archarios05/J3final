import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

///////////////////////////////////////////////
// View (V)
 
// ViewPanel は ModelObserver を実装してモデルの変更を監視
class ViewPanel extends JPanel implements ModelObserver {
  protected DrawModel model;
  
  public ViewPanel(DrawModel m, DrawController c) {
    this.setBackground(Color.WHITE);  // Color.white は deprecated なので Color.WHITE に変更
    this.addMouseListener(c);
    this.addMouseMotionListener(c);
    this.addKeyListener(c);  // キーボード入力を追加
    this.setFocusable(true); // フォーカスを受け取れるように設定
    model = m;
    model.addObserver(this);
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // 図形を描画
    ArrayList<Figure> fig = model.getFigures();
    for(int i = 0; i < fig.size(); i++) {
      Figure f = fig.get(i);
      f.draw(g);
    }
    
    // 現在の色を画面上部に表示
    g.setColor(model.getCurrentColor());
    g.fillRect(10, 10, 50, 20);
    g.setColor(Color.BLACK);
    g.drawRect(10, 10, 50, 20);
    g.drawString("Current Color", 70, 25);
    g.drawString("R:Red G:Green B:Blue Y:Yellow M:Magenta C:Cyan", 10, 45);
  }
  
  // ModelObserver インターフェースの実装
  public void update(DrawModel model) {
    repaint();
  }
}