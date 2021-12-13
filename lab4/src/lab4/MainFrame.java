package lab4;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class MainFrame extends JFrame {
    private static final int WIDTH=800;
    private static final int HEIGHT=600;
    private JFileChooser fileChooser=null;
    private JCheckBoxMenuItem showAxisMenuItem;
    private JCheckBoxMenuItem showMarkersMenuItem;
    private GraphicsDisplay display=new GraphicsDisplay();
    private boolean fileLoaded=false;

    public MainFrame() {
        super("���������� �������� ������� �� ������ ������� �������������� ������");
        setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width-WIDTH)/2,
                (kit.getScreenSize().height-HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar=new JMenuBar();//���� ����������
        setJMenuBar(menuBar);
        JMenu fileMenu=new JMenu("����");
        menuBar.add(fileMenu);

        Action openGraphicsAction=new AbstractAction("������� ���� � ��������") {// ��������� ������� "������� ���� � ��������"
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser==null){
                    fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION)
                    openGraphics(fileChooser.getSelectedFile());
            }
        };

        fileMenu.add(openGraphicsAction);
        JMenu graphicsMenu=new JMenu("������");
        menuBar.add(graphicsMenu);

        Action showAxisAction=new AbstractAction("���������� ��� ���������") {// ��������� ��������� � ������  ������ "���������� ��� ���������"
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setShowAxis(showAxisMenuItem.isSelected());
            }
        };
        showAxisMenuItem=new JCheckBoxMenuItem(showAxisAction);
        graphicsMenu.add(showAxisMenuItem);
        showAxisMenuItem.setSelected(true);

        Action showMarkersAction = new AbstractAction("���������� ������� �����") {// ��������� ���������� � ������ ������ "���������� ������� �����"
            @Override
            public void actionPerformed(ActionEvent event) {
                display.setShowMarkers(showMarkersMenuItem.isSelected());
            }
        };
        showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(showMarkersMenuItem);
        showMarkersMenuItem.setSelected(false);
        graphicsMenu.addMenuListener(new GraphicsMenuListener());
        getContentPane().add(display,BorderLayout.CENTER);
    }

    protected void openGraphics(File selectedFile){//����� ���������� ������, ���� ������ ���������� ����
        try{
            DataInputStream in=new DataInputStream(new FileInputStream(selectedFile));
            double[][] graphicsData=new double[in.available()/(Double.SIZE/8)/2][];
            int i=0;
            while(in.available()>0){
                double x=in.readDouble();
                double y=in.readDouble();
                graphicsData[i++]=new double[] {x,y};
            }
            if(graphicsData!=null && graphicsData.length>0){
                fileLoaded=true;
                display.showGraphics(graphicsData);
            }
            in.close();
        }catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(MainFrame.this,"��������� ���� �� ������",
                    "������ �������� ������",JOptionPane.WARNING_MESSAGE);
            return;
        }catch (IOException ex){
            JOptionPane.showMessageDialog(MainFrame.this,"������ ������ ��������� ����� �� �����",
                    "������ �������� ������",JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    private class GraphicsMenuListener implements MenuListener{
        @Override//�������� ����������� ������� � ����������� �� �������� �������(���� ������-��������, ��� �������-����������)
        public void menuSelected(MenuEvent e){
            showAxisMenuItem.setEnabled(fileLoaded);
            showMarkersMenuItem.setEnabled(fileLoaded);
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
}
