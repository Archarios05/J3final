import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

///////////////////////////////////////////////
// View (V)
 
// ViewPanel は ModelObserver を実装してモデルの変更を監視
class ViewPanel extends JPanel implements ModelObserver, ActionListener {
  protected DrawModel model;
  protected JComboBox<String> figureSelector;
  protected JComboBox<String> colorSelector;
  protected JButton colorPickerButton;
  
  public ViewPanel(DrawModel m, DrawController c) {
    this.setLayout(new BorderLayout());
    
    // 上部パネルを作成（図形選択と色選択を配置）
    JPanel topPanel = new JPanel(new FlowLayout());
    
    // 図形選択用のコンボボックスを作成
    String[] figureTypes = {"Rectangle", "FilledRectangle", "Circle", "FilledCircle", "Line"};
    figureSelector = new JComboBox<>(figureTypes);
    figureSelector.addActionListener(this);
    
    // 色選択用のコンボボックスを作成
    String[] colorTypes = {"Red", "Green", "Blue", "Yellow", "Magenta", "Cyan", "Black", "Orange", "Pink"};
    colorSelector = new JComboBox<>(colorTypes);
    colorSelector.addActionListener(this);
    
    // カラーピッカーボタンを作成
    colorPickerButton = new JButton("色選択");
    colorPickerButton.addActionListener(this);
    
    // ラベルとコンポーネントを上部パネルに追加
    topPanel.add(new JLabel("図形:"));
    topPanel.add(figureSelector);
    topPanel.add(new JLabel("色:"));
    topPanel.add(colorSelector);
    topPanel.add(colorPickerButton);
    
    // 描画領域となるパネルを作成
    JPanel drawingPanel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDrawing(g);
      }
    };
    
    drawingPanel.setBackground(Color.WHITE);
    drawingPanel.addMouseListener(c);
    drawingPanel.addMouseMotionListener(c);
    drawingPanel.addKeyListener(c);
    drawingPanel.setFocusable(true);
    drawingPanel.requestFocusInWindow(); // フォーカスを明示的に要求
    
    // マウスクリック時にフォーカスを取得する
    drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        drawingPanel.requestFocusInWindow();
      }
    });
    
    // レイアウト設定
    this.add(topPanel, BorderLayout.NORTH);
    this.add(drawingPanel, BorderLayout.CENTER);
    
    model = m;
    model.addObserver(this);
  }
  
  // 描画処理を別メソッドに分離
  private void paintDrawing(Graphics g) {
    
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
    
    // 現在の図形タイプと色を画面上部に表示
    g.setColor(model.getCurrentColor());
    g.fillRect(10, 10, 50, 20);
    g.setColor(Color.BLACK);
    g.drawRect(10, 10, 50, 20);
    
    // 色の詳細情報を表示
    Color currentColor = model.getCurrentColor();
    String colorInfo = String.format("RGB(%d,%d,%d)", 
                                    currentColor.getRed(), 
                                    currentColor.getGreen(), 
                                    currentColor.getBlue());
    g.drawString("現在の色: " + colorInfo, 70, 25);
    g.drawString("図形: " + model.getCurrentFigureType(), 10, 45);
    g.drawString("保存: S(PNG) Ctrl+S(Data) | 読込: 0 | クリア: Delete", 10, 65);
  }
  
  // JComboBoxとボタンの選択変更・クリックイベント処理
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == figureSelector) {
      String selectedFigure = (String) figureSelector.getSelectedItem();
      model.setCurrentFigureType(selectedFigure);
    } else if (e.getSource() == colorSelector) {
      String selectedColor = (String) colorSelector.getSelectedItem();
      Color color = getColorFromName(selectedColor);
      model.setCurrentColor(color);
    } else if (e.getSource() == colorPickerButton) {
      // JColorChooserダイアログを表示
      Color currentColor = model.getCurrentColor();
      Color newColor = JColorChooser.showDialog(this, "色を選択してください", currentColor);
      if (newColor != null) {
        model.setCurrentColor(newColor);
      }
    }
  }
  
  // 色名から色オブジェクトを取得するヘルパーメソッド
  private Color getColorFromName(String colorName) {
    switch(colorName) {
      case "Red": return Color.RED;
      case "Green": return Color.GREEN;
      case "Blue": return Color.BLUE;
      case "Yellow": return Color.YELLOW;
      case "Magenta": return Color.MAGENTA;
      case "Cyan": return Color.CYAN;
      case "Black": return Color.BLACK;
      case "Orange": return Color.ORANGE;
      case "Pink": return Color.PINK;
      default: return Color.RED;
    }
  }
  
  // ModelObserver インターフェースの実装
  public void update(DrawModel model) {
    repaint();
  }
}