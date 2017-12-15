
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Graph extends JPanel {

    private List<Double> memory;
    private Color lineColor = Color.blue;
    private Color gridColor = Color.lightGray;
    private static final Stroke edge = new BasicStroke(2);
    private int pointWidth = 4;
    private int yMarks = 8;
    private int margin = 20;
    private int labelMargin = 20;

    public Graph(List<Double> memory) {
        this.memory = memory;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphic = (Graphics2D) g;
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * margin) - labelMargin) / (memory.size() - 1);
        double yScale = ((double) getHeight() - 2 * margin - labelMargin) / (256.0);


        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < memory.size(); i++) {
            int x1 = (int) (i * xScale + margin + labelMargin);
            int y1 = (int) ((256.0 - memory.get(i)) * yScale + margin);
            graphPoints.add(new Point(x1, y1));
        }


        graphic.setColor(Color.white);
        graphic.fillRect(margin + labelMargin, margin, getWidth() - (2 * margin) - labelMargin, getHeight() - 2 * margin - labelMargin);
        graphic.setColor(Color.black);


        for (int i = 0; i < yMarks + 1; i++)
        {
            int xStart = margin + labelMargin;
            int xEnd = pointWidth + margin + labelMargin;
            int yStart = getHeight() - ((i * (getHeight() - margin * 2 - labelMargin)) / yMarks + margin + labelMargin);
            int yEnd = yStart;
            if (memory.size() > 0)
            {
                graphic.setColor(gridColor);
                graphic.drawLine(margin + labelMargin + 1 + pointWidth, yStart, getWidth() - margin, yEnd);
                graphic.setColor(Color.black);
                String yMarker = ((int) (((256.0) * ((i * 1.0) / yMarks)) * 100)) / 100.0 + "";
                FontMetrics metric = graphic.getFontMetrics();
                int labelWidth = metric.stringWidth(yMarker);
                graphic.drawString(yMarker, xStart - labelWidth - 5, yStart + (metric.getHeight() / 2) - 3);
            }
            graphic.drawLine(xStart, yStart, xEnd, yEnd);
        }


        for (int i = 0; i < memory.size(); i++)
        {
            if (memory.size() > 1)
            {
                int xStart = i * (getWidth() - margin * 2 - labelMargin) / (memory.size() - 1) + margin + labelMargin;
                int xEnd = xStart;
                int yStart = getHeight() - margin - labelMargin;
                int yEnd = yStart - pointWidth;

                if ((i % ((int) ((memory.size() / 20.0)) + 1)) == 0)
                {
                    graphic.setColor(gridColor);
                    graphic.drawLine(xStart, getHeight() - margin - labelMargin - 1 - pointWidth, xEnd, margin);
                    graphic.setColor(Color.black);
                    String xMarker = i + "";
                    FontMetrics metric = graphic.getFontMetrics();
                    int labelWidth = metric.stringWidth(xMarker);
                    graphic.drawString(xMarker, xStart - labelWidth / 2, yStart + metric.getHeight() + 3);
                }
                graphic.drawLine(xStart, yStart, xEnd, yEnd);
            }
        }


        graphic.drawLine(margin + labelMargin, getHeight() - margin - labelMargin, margin + labelMargin, margin);
        graphic.drawLine(margin + labelMargin, getHeight() - margin - labelMargin, getWidth() - margin, getHeight() - margin - labelMargin);

        graphic.setColor(lineColor);
        graphic.setStroke(edge);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x = graphPoints.get(i).x;
            int y = graphPoints.get(i).y;
            int nextX = graphPoints.get(i + 1).x;
            int nextY = graphPoints.get(i + 1).y;
            graphic.drawLine(x, y, nextX, nextY);
        }
    }

    public void setMemory(List<Double> memory) {
        this.memory = memory;
        invalidate();
        this.repaint();
    }



}