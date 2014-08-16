import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*; // For Ellipse2D, etc.
import java.awt.event.*; 

/**
 * This Applet displays a whiteboard onto which users can 
 * draw shapes and type text. It makes use of an open source
 * FontChooser Dialog available from 
 * http://examples.oreilly.com/jswing2/code/ch12/FontChooser.java
 *
 * @author BDavis
 *
 */
public class JWhiteBoard extends JApplet implements KeyListener, MouseMotionListener, 
										            MouseListener{
  /**
   * Selects what to do when the user drags
   */
  public enum Brush { Pen, Rectangle, Erasor }
  Brush selectedBrush = Brush.Pen; 
  Font selectedFont= new Font("Arial", Font.PLAIN, 12); 
  RenderingHints selectedRenderHints =
	  new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	  
  
  Color selectedColor = Color.BLACK; 
  
  /** Canvas to draw onto */
  Canvas canvas = new Canvas();
  
  JButton colorSelectButton; 
  JSpinner spinStroke;
  	  
  /**
   * Initialize Applet and add all the controls to the form
   */
  public void init() {
	this.addKeyListener(this); 
	setLayout(new BorderLayout()); 
    WindowUtilities.setNativeLookAndFeel();
    
    Container content = getContentPane();    
    content.setBackground(Color.white);
    content.setLayout(new FlowLayout()); 

    selectedRenderHints.put(RenderingHints.KEY_RENDERING, 
    		        RenderingHints.VALUE_RENDER_QUALITY);
    
    canvas.addKeyListener(this);
    canvas.addMouseMotionListener(this); 
    canvas.addMouseListener(this); 
    
    canvas.setPreferredSize(new Dimension(600,300));
    
    content.add(canvas, BorderLayout.CENTER);
    
    JPanel panelSouth = new JPanel(); 
    content.add(panelSouth, BorderLayout.SOUTH); 
    
    //Combobox
    panelSouth.add(new JLabel("Brush:")); 
    String[] brushOptions = { "Pen", "Rectangle", "Erasor"};
    JComboBox brushComboBox = new JComboBox(brushOptions);
    brushComboBox.setSelectedIndex(0);
    brushComboBox.addActionListener(new ActionListener() { 
    	public void actionPerformed(ActionEvent e) 
    	{ 
    		JComboBox j = (JComboBox)e.getSource();     		
    		if (j.getSelectedItem().toString().compareTo("Pen")==0)
    			selectedBrush = Brush.Pen; 
    		else if (j.getSelectedItem().toString().compareTo("Rectangle")==0)
    			selectedBrush = Brush.Rectangle; 
    		else if (j.getSelectedItem().toString().compareTo("Erasor")==0)
    			selectedBrush = Brush.Erasor; 
    			
    			
    	}
    });
    panelSouth.add(brushComboBox); 
    
    //Brush Size
    panelSouth.add(new JLabel("  Brush Size:")); 
    spinStroke = new JSpinner(new SpinnerNumberModel(15, 0, 64, 1));
    spinStroke.addChangeListener(new ChangeListener()  { 
    	public void stateChanged(ChangeEvent e) 
    	{
    		setStrokeWidth(((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue());
    	} 
    } ); 
    panelSouth.add(spinStroke); 
    
    //Checkbox
    JCheckBox jCheck = new JCheckBox("Anti-Aliasing"); 
    jCheck.setSelected(true); 
    jCheck.addActionListener(new ActionListener() { 
    	public void actionPerformed(ActionEvent e) 
    	{ 
    		JCheckBox j = (JCheckBox)e.getSource(); 
    		if (j.isSelected()) 
    		{
    			 RenderingHints renderHints =
    				  new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    			renderHints.put(RenderingHints.KEY_RENDERING, 
        		        RenderingHints.VALUE_RENDER_QUALITY);
    		}
    		else
    		{
    			RenderingHints renderHints =
  				  new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    			
    		}
    	}
    });
    panelSouth.add(jCheck); 
    
    //Font Button
    JButton fontSelectButton = new JButton("Select Font");    
    fontSelectButton.addActionListener( new ActionListener() { 
    	public void actionPerformed(ActionEvent e) 
    	{ 
    	    FontChooser f = new FontChooser(null); 
    	    f.setLocationByPlatform(true); 
    	    f.setVisible(true); 
    	    Font newFont = f.getNewFont(); 
    	    if (newFont != null) 
    	    	selectedFont = newFont; 
    		
    			
    	}
    });
    panelSouth.add(fontSelectButton);

    //Color Select Button
    colorSelectButton =  new JButton("Set Color"); 
    colorSelectButton.addActionListener( new ActionListener() { 
    	public void actionPerformed(ActionEvent e) 
    	{ 
    		Color c = JColorChooser.showDialog(null, "Dialog Title", selectedColor); 
    		if (c != null) 
    			selectedColor = c;
    		colorSelectButton.setForeground(selectedColor); 
    		
    			
    	}
    });
    panelSouth.add(colorSelectButton);
    
    //Clear Button
    JButton b = new JButton("Clear"); 
    b.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e)
    	{clearWhiteboard();}    	
    }); 
    panelSouth.add(b);
  }

  private Point lastMouseLocation = new Point(0, 0); 
  public void setStrokeWidth(int k) { 
	  
	  Graphics2D g2d = (Graphics2D)canvas.getGraphics();
	  g2d.setStroke(new BasicStroke(k));
  }
  
  /**
   * Clear the whole whiteboard
   */
  public void clearWhiteboard() 
  {
	  Graphics2D g2d = (Graphics2D)canvas.getGraphics();

	  g2d.clearRect(0, 0, 600, 300);
  }
  
  /**
   * Handle the mouse drag event and handle 
   * depending on the current brush selection
   */
  public void mouseDragged(MouseEvent e) 
  {
	  Graphics2D g2d = (Graphics2D)canvas.getGraphics();
	  g2d.addRenderingHints(selectedRenderHints);
	  g2d.setStroke(new BasicStroke(
			  ((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue(),
			  BasicStroke.CAP_ROUND,
			  BasicStroke.JOIN_ROUND));
	  g2d.setColor(this.selectedColor);
	  if (selectedBrush == Brush.Pen)
	  {
	    g2d.draw(new java.awt.geom.Line2D.Float(lastMouseLocation.x, lastMouseLocation.y, 
	 		  e.getX(), e.getY()));
	  }
	  else if (selectedBrush == Brush.Rectangle) 
	  {
		  g2d.translate(e.getX(), e.getY()); 
		  g2d.fill(new Rectangle(0,0,
			  ((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue(),
			  ((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue() ));
	  }
	  else if (selectedBrush == Brush.Erasor) 
	  {
		  g2d.clearRect(e.getX(), e.getY(), 
				  ((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue(), 
				  ((SpinnerNumberModel)spinStroke.getModel()).getNumber().intValue());
	  }
	  lastMouseLocation = e.getPoint();
	
	  
  }

  char lastKey = ' '; 
  /**
   * Draw a character at the current mouse position. Advance the 
   * position size of character. Attempts to delete previous characters 
   * if the user presses delete. 
   */
  public void keyTyped(KeyEvent e)
  {
	  Graphics2D g2d = (Graphics2D)canvas.getGraphics();
	  g2d.addRenderingHints(selectedRenderHints);
	  g2d.setColor(this.selectedColor);
	  g2d.setFont(selectedFont); 
	  if (e.getKeyChar() == '\b') 
	  {		  
		  lastMouseLocation.x -= this.getFontMetrics(selectedFont).charWidth(lastKey);
		  g2d.clearRect(lastMouseLocation.x, 
				  lastMouseLocation.y-this.getFontMetrics(selectedFont).getHeight(), 
				  this.getFontMetrics(selectedFont).charWidth(lastKey), 
				  this.getFontMetrics(selectedFont).getHeight());		  
	  }
	  else { 
		  g2d.drawString(e.getKeyChar()+"", lastMouseLocation.x , lastMouseLocation.y);
		  lastMouseLocation.x += this.getFontMetrics(selectedFont).charWidth(e.getKeyChar());
		  lastKey = e.getKeyChar();
	  }
	  
	  
  }
  
  /**
   * Set last mouse position, so that the brush and type tools will 
   * work as expected
   */
  public void mousePressed(MouseEvent e)
  {
	  this.lastMouseLocation = e.getPoint(); 
  }
  

  public void mouseMoved(MouseEvent e)
  { 
	  
  }
  
  public void keyPressed(KeyEvent e) 
  {
	   
  }
  
  public void keyReleased(KeyEvent e) 
  {
  }
  
  public void mouseClicked(MouseEvent e)
  {
	  
  }
  
  public void mouseEntered(MouseEvent e)
  {
  }
  
  public void mouseExited(MouseEvent e) 
  {
  }
   
  public void mouseReleased(MouseEvent e) 
  {
  
  }

}