import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;

class DrawController implements MouseListener, MouseMotionListener, KeyListener {
  protected DrawModel model;
  protected int dragStartX,dragStartY;
  public DrawController(DrawModel a) {
    model = a;
  }
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {
    dragStartX = e.getX(); dragStartY = e.getY();
    model.createFigure(dragStartX, dragStartY);
  }
  public void mouseDragged(MouseEvent e) {
    model.reshapeFigure(dragStartX, dragStartY, e.getX(), e.getY());
  }
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {}
  
  // キーボードで色を変更（ドロップダウンと併用可能）
  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_R: // Rキーで赤
        model.setCurrentColor(Color.RED);
        break;
      case KeyEvent.VK_G: // Gキーで緑
        model.setCurrentColor(Color.GREEN);
        break;
      case KeyEvent.VK_B: // Bキーで青
        model.setCurrentColor(Color.BLUE);
        break;
      case KeyEvent.VK_Y: // Yキーで黄色
        model.setCurrentColor(Color.YELLOW);
        break;
      case KeyEvent.VK_M: // Mキーでマゼンタ
        model.setCurrentColor(Color.MAGENTA);
        break;
      case KeyEvent.VK_C: // Cキーでシアン
        model.setCurrentColor(Color.CYAN);
        break;
      case KeyEvent.VK_S: // Sキーで保存
        if (e.isControlDown()) {
          model.saveFigures("drawings.dat");
        } else {
          model.saveAsImage("drawing.png", 500, 500);
        }
        break;
      case KeyEvent.VK_0: // 0キーで読み込み
        model.loadFigures("drawings.dat");
        break;
      case KeyEvent.VK_DELETE: // Deleteキーで全削除
        model.clearAllFigures();
        break;
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
}