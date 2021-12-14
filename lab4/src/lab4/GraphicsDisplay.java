package lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GraphicsDisplay extends JPanel {
    private double[][] graphicsData;
    private boolean showAxis=true;
    private boolean showMarkers =true;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double scale;
    private BasicStroke graphicsStroke;
    private BasicStroke axisStroke;
    private BasicStroke markerStroke;
    private Font axisFont;

    public GraphicsDisplay(){//çàäàþòñÿ ïàðàìåòðû îòîáðàæåíèÿ îñåé, ïîäïèñåé îñåé, òî÷åê
        setBackground(Color.white);
        graphicsStroke=new BasicStroke(2.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0f,new float[] {1,1,1,1,1,1,4,1,2,1,2},0.0f);
        axisStroke=new BasicStroke(2.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0f,null,0.0f);
        markerStroke=new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,10.0f,null,0.0f);
        axisFont=new Font("Serif",Font.BOLD,36);
    }

    public void showGraphics(double[][] graphicsData){//âûçûâàåòñÿ, åñëè âûáðàíû äàííûå äëÿ ãðàôèêà
        this.graphicsData=graphicsData;
        repaint();//âûçûâàåò ìåòîä paintComponent äëÿ ïåðåðèñîâêè ãðàôèêà ïîñëå îáíîâëåíèÿ äàííûõ
    }

    public void setShowAxis(boolean showAxis){//óñòàíàâëèâàåò íåîáõîäèìñîòü îòðèñîâêè îñåé êîîðäèíàò
        this.showAxis=showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers){//óñòàíàâëèâàåò íåîáõîäèìîñòü îòðèñîâêè òî÷åê
        this.showMarkers=showMarkers;
        repaint();
    }

    protected Point2D.Double xyToPoint(double x, double y){
        double deltaX=x- minX;
        double deltaY=maxY-y;
        return new Point2D.Double(deltaX*scale,deltaY*scale);
    }

    protected Point2D.Double shiftPoint(Point2D.Double src, double deltaX, double deltaY){
        Point2D.Double dest=new Point2D.Double();
        dest.setLocation(src.getX()+deltaX,src.getY()+deltaY);
        return dest;
    }

    protected void paintGraphics(Graphics2D canvas){
        canvas.setStroke(graphicsStroke);
        canvas.setColor(Color.red);
        GeneralPath graphics =new GeneralPath();
        for(int i=0;i<graphicsData.length;i++){
            Point2D.Double point = xyToPoint(graphicsData[i][0],graphicsData[i][1]);
            if(i>0){
                graphics.lineTo(point.getX(),point.getY());
            }
            else{
                graphics.moveTo(point.getX(),point.getY());
            }
            canvas.draw(graphics);
        }
    }

    protected void paintAxis(Graphics2D canvas){//îòðèñîâêà îñåé êîîðäèíàò
        canvas.setStroke(axisStroke);
        canvas.setColor(Color.black);
        canvas.setPaint(Color.black);
        canvas.setFont(axisFont);
        FontRenderContext context=canvas.getFontRenderContext();
        if(minX <=0.0 && maxX>=0.0){
            GeneralPath OY=new GeneralPath();
            OY.moveTo(xyToPoint(0,maxY).getX(),xyToPoint(0,maxY).getY());
            OY.lineTo(xyToPoint(0,minY).getX(),xyToPoint(0,minY).getY());
            canvas.draw(OY);
            GeneralPath arrow=new GeneralPath();
            Point2D.Double lineEnd=xyToPoint(0,maxY);
            arrow.moveTo(lineEnd.getX(),lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX()+5,arrow.getCurrentPoint().getY()+20);
            arrow.lineTo(arrow.getCurrentPoint().getX()-10,arrow.getCurrentPoint().getY());
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
            Rectangle2D bounds=axisFont.getStringBounds("y",context);
            Point2D.Double labelPos=xyToPoint(0,maxY);
            canvas.drawString("y",(float)labelPos.getX()+10,(float)(labelPos.getY()-bounds.getY()));
        }
        if(minY<=0.0 && maxY>=0.0){
            GeneralPath OX=new GeneralPath();
            OX.moveTo(xyToPoint(minX,0).getX(),xyToPoint(minX,0).getY());
            OX.lineTo(xyToPoint(maxX,0).getX(),xyToPoint(maxX,0).getY());
            canvas.draw(OX);
            GeneralPath arrow=new GeneralPath();
            Point2D.Double lineEnd=xyToPoint(maxX,0);
            arrow.moveTo(lineEnd.getX(),lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX()-20,arrow.getCurrentPoint().getY()-5);
            arrow.lineTo(arrow.getCurrentPoint().getX(),arrow.getCurrentPoint().getY()+10);
            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);
            Rectangle2D bounds= axisFont.getStringBounds("x",context);
            Point2D.Double labelPos=xyToPoint(maxX,0);
            canvas.drawString("x",(float)(labelPos.getX()-bounds.getWidth()-10),
                    (float)(labelPos.getY()-bounds.getY()));
        }
    }

    protected void paintMarkers(Graphics2D canvas){//òîðèñîâêà òî÷åê
        canvas.setStroke(markerStroke);
        canvas.setColor(Color.RED); //point color was changed
        canvas.setPaint(Color.RED); //point color was changed
        Double dx=5.5;
        Double dy=5.5;
        for(double[] point:graphicsData){
            GeneralPath rhombus=new GeneralPath();
            Point2D.Double center=xyToPoint(point[0],point[1]);
            rhombus.moveTo(center.getX(),center.getY()+dx);
            rhombus.lineTo(center.getX()+dx,center.getY());
            rhombus.lineTo(center.getX(),center.getY()-dy);
            rhombus.lineTo(center.getX()-dx,center.getY());
            rhombus.closePath();
            canvas.draw(rhombus);
            if((double)((int)center.getY()/2)%10!=0)//ïðîâåðêà, ÿâëÿåòñÿ ëè öåëàÿ ÷àñòü ôóíêöèè íå÷åòíîé
            canvas.fill(rhombus);
        }
    }

    public void paintComponent(Graphics g){//îòðèñîâêà ãðàôèêà
        super.paintComponent(g);
        if(graphicsData==null || graphicsData.length==0)
            return;
        minX =graphicsData[0][0];
        maxX=graphicsData[graphicsData.length-1][0];
        minY=graphicsData[0][1];
        maxY=minY;
        for(int i=1;i<graphicsData.length;i++){
            if(graphicsData[i][1]<minY){
                minY=graphicsData[i][1];
            }
            if(graphicsData[i][1]>maxY){
                maxY=graphicsData[i][1];
            }
        }
        double scaleX=getSize().getWidth()/(maxX- minX);
        double scaleY=getSize().getHeight()/(maxY-minY);
        scale=Math.min(scaleX,scaleY);
        if(scale==scaleX){
            double yIncrement=(getSize().getHeight()/scale-(maxY-minY))/2;
            maxY+=yIncrement;
            minY-=yIncrement;
        }
        if(scale==scaleY){
            double xIncrement=(getSize().getWidth()/scale-(maxX-minX))/2;
            maxX+=xIncrement;
            minX-=xIncrement;
        }
        Graphics2D canvas=(Graphics2D) g;
        Stroke oldStroke= canvas.getStroke();
        Color oldColor=canvas.getColor();
        Paint oldPaint=canvas.getPaint();
        Font oldFont=canvas.getFont();
        if(showAxis) paintAxis(canvas);
        if(showMarkers) paintMarkers(canvas);
        paintGraphics(canvas);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
        canvas.setFont(oldFont);
    }
}
