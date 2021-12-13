package lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {
    private static final int WIDTH=700;
    private static final int HEIGHT=500;
    private double[] coefficients;
    private JFileChooser fileChooser=null;
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    private GornerTableCellRenderer renderer=new GornerTableCellRenderer();
    private GornerTableModel data;

    public MainFrame(double[] coefficients){
        super("������������� ���������� �� ������� �� ����� �������");
        this.coefficients =coefficients;
        setSize(WIDTH,HEIGHT);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        setLocation((toolkit.getScreenSize().width-WIDTH)/2,(toolkit.getScreenSize().height-HEIGHT)/2);
        //������ ����
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu=new JMenu("����");
        menuBar.add(fileMenu);
        JMenu tableMenu=new JMenu("�������");
        menuBar.add(tableMenu);
        JMenu infMenu=new JMenu("�������");
        JMenuItem aboutProgramm=infMenu.add("� ���������");
        menuBar.add(infMenu);


        aboutProgramm.addActionListener(new ActionListener() {//������� �� ������� �� "� ���������"
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new Frame(),"��������� ����\n������ �9");
            }
        });

        Action saveToTextAction=new AbstractAction("��������� � ��������� ����") {//������� �� ������� �� "��������� � ��������� ����"
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    saveToTextFile(fileChooser.getSelectedFile());
                }
            }
        };

        saveToTextMenuItem=fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);

        Action saveToGraphicsAction=new AbstractAction("��������� ������ ��� ��������� �������") {//������� �� ������� �� "��������� ������ ��� ��������� �������"
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser==null){
                    fileChooser =new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if(fileChooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION){
                    saveToGraphicsFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToGraphicsMenuItem=fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        Action searchValueAction=new AbstractAction("����� �������� ����������") {//������� �� ������� "����� �������� ����������"
            @Override
            public void actionPerformed(ActionEvent e) {
                String value=JOptionPane.showInputDialog(MainFrame.this, "������� �������� ��� ������","����� ��������",
                        JOptionPane.QUESTION_MESSAGE);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        };

        searchValueMenuItem=tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);

        JLabel labelForFrom=new JLabel("� ���������� �� ��������� ��: ");
        textFieldFrom=new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForTo=new JLabel("��: ");
        textFieldTo=new JTextField("0.0",10);
        textFieldTo.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForStep=new JLabel("� �����: ");
        textFieldStep=new JTextField("0.1",10);
        textFieldStep.setMaximumSize(textFieldFrom.getPreferredSize());
        //������ ������� ��������� � ����������� �������� �
        Box hBoxRange=Box.createHorizontalBox();
        hBoxRange.setBorder(BorderFactory.createBevelBorder(1));
        hBoxRange.add(Box.createHorizontalGlue());
        hBoxRange.add(labelForFrom);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldFrom);
        hBoxRange.add(Box.createHorizontalStrut(20));
        hBoxRange.add(labelForTo);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldTo);
        hBoxRange.add(Box.createHorizontalStrut(20));
        hBoxRange.add(labelForStep);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldStep);
        hBoxRange.add(Box.createHorizontalGlue());
        hBoxRange.setPreferredSize(new Dimension(
                new Double(hBoxRange.getMaximumSize().getWidth()).intValue(),
                new Double(hBoxRange.getMaximumSize().getHeight()).intValue()
        ));
        getContentPane().add(hBoxRange, BorderLayout.NORTH);

        JButton buttonCalc=new JButton("���������");
        buttonCalc.addActionListener(new ActionListener() {//������� �� ������� "���������"
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double from = Double.parseDouble(textFieldFrom.getText());
                    double to = Double.parseDouble((textFieldTo.getText()));
                    double step = Double.parseDouble(textFieldStep.getText());
                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));
                    getContentPane().validate();
                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "������ � ������� ������ ����� � ��������� ������", "��������� ������ �����",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonClear=new JButton("�������� ����");
        buttonClear.addActionListener(new ActionListener() {//������� �� ������� "�������� ����"
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("0.0");
                textFieldStep.setText("0.1");
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                saveToGraphicsMenuItem.setEnabled(false);
                saveToTextMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                getContentPane().validate();
            }
        });
        //���������� ����� ���������� � ���������
        hBoxResult=Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
        hBoxResult.setPreferredSize(new Dimension(
                new Double(hBoxResult.getMaximumSize().getWidth()).intValue(),
                new Double(hBoxResult.getMaximumSize().getHeight()).intValue()*2
        ));
        //���������� ����� ���������� �  ���������� ����������
        Box hBoxButtons=Box.createHorizontalBox();
        hBoxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.add(buttonCalc);
        hBoxButtons.add(Box.createHorizontalStrut(30));
        hBoxButtons.add(buttonClear);
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.setPreferredSize(new Dimension(
                new Double(hBoxButtons.getMaximumSize().getWidth()).intValue(),
                new Double(hBoxButtons.getMaximumSize().getHeight()).intValue()
        ));
        getContentPane().add(hBoxButtons, BorderLayout.SOUTH);

        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
    }
    //���������� ���������� ������� � ��������� ����
    protected void saveToTextFile(File selectedFile){
        try{
            PrintStream out=new PrintStream(selectedFile);
            out.println("��������� ������������� ���������� �� ����� �������");
            out.println("���������: ");
            for(int i = 0; i< coefficients.length; i++) {
                out.print(coefficients[i] + "*x^" + (coefficients.length - i - 1));
                if (i != coefficients.length - 1) {
                    out.print("+");
                }
            }
            out.println("");
            out.println("�������� �� "+ data.getFrom() +
                    " �� "+ data.getTo() + " � ����� "+ data.getStep());
            out.println("==================================================");
            for(int i=0;i<data.getRowCount();i++){
                out.println("������� � ����� "+data.getValueAt(i,0)+" ����� "+
                        data.getValueAt(i,1));
            }
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    //���������� ���������� ������� � ���� ��� ��������� �������
    protected void saveToGraphicsFile(File selectedFile) {
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for(int i=0;i<data.getRowCount();i++){
                out.writeDouble((Double)data.getValueAt(i,0));
                out.writeDouble((Double)data.getValueAt(i,1));
            }
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}