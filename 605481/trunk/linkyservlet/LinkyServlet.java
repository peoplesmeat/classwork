package linky;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** Simple servlet for testing the use of packages
 *  and utilities from the same package.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class LinkyServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String wname = request.getParameter("website"); 
    String title = "Welcome to the Link Verifier!";
    
    out.println("<html>" + 
                "<head><title>Link Verifier</title></head"+
                "<BODY>\n" +
                "<H1>" + title + "</H1>\n");
    
    out.println("<form method=\"get\" action=\"linky.LinkyServlet\">" + 
                 "<input name=\"website\"/>" + 
                 "<input type=\"submit\"/>" + 
                 "</form>"); 
    out.println("<p></p>");   
    if (wname != null) 
      out.println("<h2>Verifing "+wname+"</h2>"); 
    if (wname != null) 
    {
    
      //Here we call the verifyLinks function, and print out the output
      //verifyLinks does some formatting of the output stream. 
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Linky.verifyLinks(wname, false, 
                          new PrintStream(baos)); 
      out.println(baos.toString() + 
                  "</BODY></html>");
    }
  }
}
