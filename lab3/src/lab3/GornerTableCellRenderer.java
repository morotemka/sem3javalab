package lab3;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {
    private String needle=null;
    private DecimalFormat formatter=(DecimalFormat)NumberFormat.getInstance();
    private JPanel panel=new JPanel();
    private JLabel label=new JLabel();
    public GornerTableCellRenderer(){//настройка отображения элементов таблицы
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble=formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    @Override//смена цвета при поиске элемента в таблице
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String formatterDouble=formatter.format(value);
        label.setText(formatterDouble);
        if(column==1 && needle!=null && needle.equals(formatterDouble)){
            panel.setBackground(Color.red);
        }else{
            panel.setBackground(Color.white);
        }
        return panel;
    }
    public void setNeedle(String needle){
        this.needle=needle;
    }
}
