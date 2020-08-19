package final_exam;

public class Chess {
	private boolean alive=true;
	public String Color;
	public Chess() {} //定义构造方法
	public void eat() {alive=false;} //定义棋子被吃
	public boolean isAlive() {return alive;} //定义函数判断棋子是否存活
}
