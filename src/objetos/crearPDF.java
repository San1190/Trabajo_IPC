package objetos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

public class crearPDF {

    public static void main(String[] args) {
       
        PrinterJob job = PrinterJob.getPrinterJob();

        
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.drawString("¡Hola Mundo! Este es un PDF generado desde Java.", 100, 100);
                return PAGE_EXISTS;
            }
        });

        
        PageFormat pageFormat = job.defaultPage();
        Paper paper = new Paper();
        paper.setSize(595.2, 841.8); 
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        pageFormat.setPaper(paper);
        job.setPrintable(job.getPrintable(), pageFormat);

        
        PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
        if (services.length > 0) {
            try {
                job.setPrintService(services[0]);
                
                
                DocPrintJob printJob = services[0].createPrintJob();
                Doc doc = new SimpleDoc(job.getPrintable(), DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
                printJob.print(doc, null);
                System.out.println("PDF generado correctamente.");
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encontró un servicio de impresión compatible.");
        }
    }
}
