import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.net.*;

/** This applet is based on the in class version
 *  It only lets collects input and then 
 *  redirects the page to using a query 
 *  string. The jsp page collects the 
 *  query string and then shows the search 
 *  frames. 
 */

public class SearchApplet extends JApplet
                          implements ActionListener {
  private JTextField queryField;
  private JButton submitButton;

  JComboBox a, b, c; 
  public void init() {
	Container content = getContentPane();    
	content.setBackground(Color.white);
	content.setLayout(new FlowLayout()); 
    
	setFont(new Font("Serif", Font.BOLD, 18));
    content.add(new Label("Search String:"));
    queryField = new JTextField(40);
    queryField.addActionListener(this);
    content.add(queryField);
    submitButton = new JButton("Send to Search Engines");
    submitButton.addActionListener(this);
    
    String [] engines = {"yahoo", "ask", 
    		"gigablast", "dmoz", "live", 
    		"exalead", "netscape"};
    a = new JComboBox(engines); 
    b = new JComboBox(engines); 
    c = new JComboBox(engines); 
    content.add(a);
    content.add(b);
    content.add(c);
    
    content.add(submitButton);
  }

  /** Submit data when button is pressed <B>or</B>
   *  user presses Return in the TextField.
   */
  final String base = "http://localhost:8888/search.jsp"; 
  public void actionPerformed(ActionEvent event) {
	  try {
		  URL u = new URL(base + 
				  "?search=" + URLEncoder.encode(queryField.getText()) + 
				  "&engine1="+a.getSelectedItem() + 
				  "&engine2="+b.getSelectedItem() + 
				  "&engine3="+c.getSelectedItem()); 
		  getAppletContext().showDocument(u);
	  } catch (MalformedURLException mue) {
	  }

  }
}