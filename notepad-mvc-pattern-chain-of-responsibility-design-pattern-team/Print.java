import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

public class Print implements Printable {
  private String data;

  private int[] pageBreaks;  

  private String[] textLines;

  private Font font;

  public Print(String data, Font font) {
    this.font = font;
    this.data = data;
  }

  public static String[] splitTextIntoLines(String text, int lineLength) {
    List<String> lines = new ArrayList<>();
    int length = text.length();
    int index = 0;

    while (index < length) {
        int end = Math.min(index + lineLength, length);

         if (end < length) {
            while (end > index && !Character.isWhitespace(text.charAt(end))) {
                end--;
            }
        }
        
        String line = text.substring(index, end).trim();
        lines.add(line);
        index = end;
    }

    return lines.toArray(new String[lines.size()]);
}

  private void initTextLines() {
    if (textLines == null) {
      int numLines = 2000;
      int lineLength = 70;
      textLines = splitTextIntoLines(data, lineLength);

    }
  }


  public void printDocument() {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);
    boolean doPrint = job.printDialog();
    if (doPrint) {
      try {
        job.print();
        
      } catch (PrinterException ex) {
      }
    }
  }

  public int print(Graphics g, PageFormat pf, int pageIndex) {
      g.setFont(font);
      FontMetrics metrics = g.getFontMetrics(font);
      int lineHeight = metrics.getHeight();
  



    if (pageBreaks == null) {
      initTextLines();
      int linesPerPage = ((int)pf.getImageableHeight() - 100) / lineHeight;
      int numBreaks = (textLines.length - 1) / linesPerPage;
      pageBreaks = new int[numBreaks];
      for (int i = 0; i < numBreaks; i++) {
        pageBreaks[i] = (i + 1) * linesPerPage;
      }

    }

    if (pageIndex > pageBreaks.length) {
      return NO_SUCH_PAGE;
    }

    
    Graphics2D g2d = (Graphics2D)g;
    g2d.translate(pf.getImageableX(), pf.getImageableY());

    
    int y = 50;
    int x = 50;
    int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
    int end   = (pageIndex == pageBreaks.length)
    ? textLines.length : pageBreaks[pageIndex];

    for (int line = start; line < end; line++) {
      y += lineHeight;
      g.drawString(textLines[line], x, y);
    }

    Font fontColumnar = new Font("Serif", Font.PLAIN, 12);
    g.setFont(fontColumnar);
    g.drawString("Number of page: " + (pageIndex + 1), 100, ((int)pf.getImageableHeight() - 20));
 
    return PAGE_EXISTS;
  }
}
