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
// У яблока – 1 параметр, который находится в parts[1]
breakfast[itemsSoFar] = new Coffee(parts[1]);
coffeecounter++;
}
else if (parts[0].equals("Cheese")) {
	// У сыра дополнительных параметров нет
	breakfast[itemsSoFar] = new Cheese();
	cheesecounter++;
	} else if (parts[0].equals("Apple")) {
	// У яблока – 1 параметр, который находится в parts[1]
	breakfast[itemsSoFar] = new Apple(parts[1]);
	applecounter++;
	}
itemsSoFar++;
}
System.out.println("CЫР - " + cheesecounter);
System.out.println("ЯБЛОКО - " + applecounter);
System.out.println("КОФЕ - " + coffeecounter);
// Перебор всех элементов массива
for (Food item: breakfast)
if (item!=null)
// Если элемент не null – употребить продукт
item.consume();
else
// Если дошли до элемента null – значит достигли конца
// списка продуктов, ведь 20 элементов в массиве было
// выделено с запасом, и они могут быть не
// использованы все
break;
System.out.println("Всего хорошего!");
}
}