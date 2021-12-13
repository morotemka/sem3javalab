package lab3;

import java.util.Scanner;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        Scanner in =new Scanner(System.in);
        args= new String[]{"-1","0.5","+2"};//������������ ����������
        if(args.length==0){
            System.out.println("���������� ������������ ���������, ��� ��������" +
                    " �� ������ �� ������ ������������!");
            System.exit(-1);
        }
        double[] coefficients=new double[args.length];
        int i=0;
        try{
            for(String arg: args){
                coefficients[i++]=Double.parseDouble(arg);
            }
        }catch (NumberFormatException ex){
            System.err.println("������ �������������� ������ '"+ args[i] +
                    "' � ����� ���� Double");
            System.exit(-2);
        }
        MainFrame frame=new MainFrame(coefficients);//�������� ����������
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
