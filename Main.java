import javax.swing.JFrame;
import java.awt.Color;

class DrawFrame extends JFrame {
  DrawModel model;
  ViewPanel view;
  DrawController cont;
  
  public DrawFrame() {
    model = new DrawModel();
    cont = new DrawController(model);
    view = new ViewPanel(model, cont);
    this.setBackground(Color.BLACK);  // Color.black は deprecated なので Color.BLACK に変更
    this.setTitle("Draw Editor");
    this.setSize(500, 500);
    this.add(view);
    this.addKeyListener(cont); // フレーム全体にもキーリスナーを追加
    this.setFocusable(true);   // フレームもフォーカス可能にする
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.requestFocus(); // フォーカスを要求
  }
  
  public static void main(String[] args) {
    new DrawFrame();
  }
}