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
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients =coefficients;
        setSize(WIDTH,HEIGHT);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        setLocation((toolkit.getScreenSize().width-WIDTH)/2,(toolkit.getScreenSize().height-HEIGHT)/2);
        //сборка меню
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu=new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu=new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu infMenu=new JMenu("Справка");
        JMenuItem aboutProgramm=infMenu.add("О программе");
        menuBar.add(infMenu);


        aboutProgramm.addActionListener(new ActionListener() {//реакция на нажатие на "О программе"
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new Frame(),"Молошенко Артём\nГруппа №9");
            }
        });

        Action saveToTextAction=new AbstractAction("Сохранить в текстовый файл") {//реакция на нажатие на "Сохранить в текстовый файл"
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

        Action saveToGraphicsAction=new AbstractAction("Сохранить данные для постройки графика") {//реакция на нажатие на "Сохранить данные для постройки графика"
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

        Action searchValueAction=new AbstractAction("Найти значение многочлена") {//реакция на нажатие "Найти значение многочлена"
            @Override
            public void actionPerformed(ActionEvent e) {
                String value=JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска","Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        };

        searchValueMenuItem=tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);

        JLabel labelForFrom=new JLabel("Х изменятеся на интервале от: ");
        textFieldFrom=new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForTo=new JLabel("до: ");
        textFieldTo=new JTextField("0.0",10);
        textFieldTo.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForStep=new JLabel("с шагом: ");
        textFieldStep=new JTextField("0.1",10);
        textFieldStep.setMaximumSize(textFieldFrom.getPreferredSize());
        //сборка участка интефейса с параметрами значения х
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

        JButton buttonCalc=new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {//реакция на нажатие "Вычислить"
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
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonClear=new JButton("Очистить поля");
        buttonClear.addActionListener(new ActionListener() {//реакция на нажатие "Очистить поля"
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
        //добавление части интерфейса с таблтицей
        hBoxResult=Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
        hBoxResult.setPreferredSize(new Dimension(
                new Double(hBoxResult.getMaximumSize().getWidth()).intValue(),
                new Double(hBoxResult.getMaximumSize().getHeight()).intValue()*2
        ));
        //добавление части интерфейса с  возможными действиями
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
    //реализация сохранения таблицы в текстовый файл
    protected void saveToTextFile(File selectedFile){
        try{
            PrintStream out=new PrintStream(selectedFile);
            out.println("Результат табулирования многочлена по схеме Горнера");
            out.println("Многочлен: ");
            for(int i = 0; i< coefficients.length; i++) {
                out.print(coefficients[i] + "*x^" + (coefficients.length - i - 1));
                if (i != coefficients.length - 1) {
                    out.print("+");
                }
            }
            out.println("");
            out.println("Интервал от "+ data.getFrom() +
                    " до "+ data.getTo() + " с шагом "+ data.getStep());
            out.println("==================================================");
            for(int i=0;i<data.getRowCount();i++){
                out.println("Значеие в точке "+data.getValueAt(i,0)+" равно "+
                        data.getValueAt(i,1));
            }
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    //реализация сохранения таблицы в файл для постройки графика
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