package lab1;

public class Apple extends Food {
	// ����� ���������� ���� ������ ������
	private String size;
	public Apple(String size) {
	// ������� ����������� ������, ������� ��� ��� ������
	super("������");
	// ���������������� ������ ������
	this.size = size;
	}
	// �������������� ������ ������������ ������
	public void consume() {
	System.out.println(this + " �������");
	}
	//������ ��� ������� � ���� ������ ������
	public String getSize() {
	return size;
	}
	// ������ ��� ��������� ���� ������ ������
	public void setSize(String size) {
	this.size = size;
	}
	// ���������������� ������ ������ equals(), ������� ��� ���������
	// ��������� �� ������ ���� name (��� 1), �� � ��������� �������������
	// ����� (��� 2) � ���������� �������� (��� 3)
	public boolean equals(Object arg0) {
	if (!super.equals(arg0)) return false; // ��� 1
	if (!(arg0 instanceof Apple)) return false; // ��� 2
	return size.equals(((Apple)arg0).size); // ��� 3
	}
	// ���������������� ������ ������ toString(), ������������ �� ������
	// �������� ��������, �� � ��� ������
	public String toString() {
	return super.toString() + " ������� '" + size.toUpperCase() + "'";
	}
	}