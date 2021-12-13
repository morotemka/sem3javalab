package lab1;
import lab1.Food;
import lab1.Coffee;
import java.util.Scanner;

public class Lab1v6 { 	
@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
Scanner vvod = new Scanner(System.in);
Food[] breakfast = new Food[20];
int itemsSoFar = 0;
int applecounter=0;
int cheesecounter=0;
int coffeecounter=0;
for (String arg: args) {
String[] parts = arg.split("/");
if (parts[0].equals("Coffee"))
{
	//System.out.println(parts[1]);
// � ������ � 1 ��������, ������� ��������� � parts[1]
breakfast[itemsSoFar] = new Coffee(parts[1]);
coffeecounter++;
}
else if (parts[0].equals("Cheese")) {
	// � ���� �������������� ���������� ���
	breakfast[itemsSoFar] = new Cheese();
	cheesecounter++;
	} else if (parts[0].equals("Apple")) {
	// � ������ � 1 ��������, ������� ��������� � parts[1]
	breakfast[itemsSoFar] = new Apple(parts[1]);
	applecounter++;
	}
itemsSoFar++;
}
System.out.println("C�� - " + cheesecounter);
System.out.println("������ - " + applecounter);
System.out.println("���� - " + coffeecounter);
// ������� ���� ��������� �������
for (Food item: breakfast)
if (item!=null)
// ���� ������� �� null � ���������� �������
item.consume();
else
// ���� ����� �� �������� null � ������ �������� �����
// ������ ���������, ���� 20 ��������� � ������� ����
// �������� � �������, � ��� ����� ���� ��
// ������������ ���
break;
System.out.println("����� ��������!");
}
}