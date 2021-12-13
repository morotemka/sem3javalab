package lab1;

public class Coffee extends Food {
	private String aroma;
	public Coffee(String aroma) {
		super("Coffee");
        if (aroma.equals("восточный")||aroma.equals("насыщенный")||aroma.equals("горький"))
		this.aroma = aroma;
		else System.out.println("Неверный параметр");
		}
		// Переопределить способ употребления яблока
		public void consume() {
		System.out.println(this + " выпит");
		}
		// Селектор для доступа к полю данных РАЗМЕР
		public String getAroma() {
		return aroma;
		}
		// Модификатор для изменения поля данных РАЗМЕР
		public void setAroma(String aroma) {
		this.aroma = aroma;
		}
		// Переопределѐнная версия метода equals(), которая при сравнении
		// учитывает не только поле name (Шаг 1), но и проверяет совместимость
		// типов (Шаг 2) и совпадение размеров (Шаг 3)
		public boolean equals(Object arg0) {
		if (super.equals(arg0)) { // Шаг 1
		if (!(arg0 instanceof Coffee)) return false; // Шаг 2
		return aroma.equals(((Coffee)arg0).aroma); // Шаг 3
		} else
		return false;
		}
		// Переопределѐнная версия метода toString(), возвращающая не только
		// название продукта, но и его размер
		public String toString() {
		return super.toString() + " аромата '" + aroma.toUpperCase() + "'";
		}
		}