package final_exam;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


//继承MouseListener接口，必须实现所有的抽象方法
public class FI extends JFrame implements MouseListener{
	int width = Toolkit.getDefaultToolkit().getScreenSize().width; 
	int height = Toolkit.getDefaultToolkit().getScreenSize().height; //设置宽度和高度，根据屏幕分辨率
	private Chess[][] allChess = new Chess[9][9]; //用列表来保存棋子
	private Chess currentChess; //选中的棋子
	private int step = 1; //棋子的步数，单数为黑棋走，双数为白棋走，黑棋先走
	private boolean first = true; //判断是否可以执行第一步（选择棋子）
	private boolean second = false; //判断是否可以执行第二步（落下棋子）
	private String message1; //message1告知玩家该谁走
	private String message2 = ""; //message2告知玩家游戏状态（包括错误）
	
	//构造函数
	public FI(){
		this.initChess();
		this.setTitle("帝国古典象棋"); //设置标题
		this.setSize(700,700); //设置窗口大小
		this.setLocation((width - 500) / 2 , (height - 500) / 2 ); //设置窗口位置，让窗口居中显示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置关闭按钮
		this.setResizable(false); //设置窗口不可改变，固定窗口大小
		this.setVisible(true); //设置可视化	
		this.repaint(); //java里repaint()是重绘component的方法，它会直接调用下面的方法对界面进行重行绘画			
		this.addMouseListener(this); //实现鼠标监听的方法
	}
	
	//绘制棋盘
	public void paint(Graphics g) {
		//BufferedImage类是Image实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
		BufferedImage buf = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB); //设置图片大小
		Graphics g1 =  buf.createGraphics();
		g1.setColor(Color.WHITE); 
		g1.fillRect(0, 0, 700, 700); //背景颜色填充
		Font f = new Font("微软雅黑",Font.BOLD,30); 
		g1.setFont(f); //给画笔设置字体
		g1.setColor(Color.WHITE); //设置棋盘背景颜色
		g1.fill3DRect(50, 60, 450, 450, true); //设置棋盘大小（500,500）
		for(int i=0;i<=9;i++){
			g1.setColor(Color.BLACK); //设置线的颜色
			g1.drawLine(50, 60+i*50, 450+50, 60+i*50); //画横线，每个格子之间的距离为50
			g1.drawLine(50+i*50, 60, 50+i*50, 450+60); //画竖线
		}
		g1.setFont(new Font("微软雅黑",Font.BOLD,20));
		g1.drawRect(550,100,100,40);
	    g1.drawString("重新开始",560,125); //重新开始按钮
		g1.drawRect(550,250,100,40);
	    g1.drawString("游戏规则",560,275); //游戏规则按钮
	    if(step%2==0) {message1 = "白棋";}
	    else {message1 = "黑棋";}
	    g1.drawString("黑方先行，现在该"+message1, 50, 550);
	    g1.drawString("游戏状态："+message2, 50, 600);
	    
	    
		//绘制棋子
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(allChess[i][j] instanceof Black_Chess) { //instanceof是一个关键字，用于判断是否属于一个类
					//绘制棋子所在的位置
					int tempX = i*50+50; 
					int tempY = j*50+60; 
					g1.setColor(Color.BLACK);
					g1.fillOval(tempX, tempY, 50, 50); //绘制圆形棋子，50表示圆的大小为50*50的矩形的内接圆
					g1.setColor(Color.BLACK);
					g1.drawOval(tempX, tempY, 50, 50);
				}
				else if(allChess[i][j] instanceof White_Chess) {
					int tempX = i*50+50;
					int tempY = j*50+60;
					g1.setColor(Color.WHITE);
					g1.fillOval(tempX, tempY, 50, 50);
					g1.setColor(Color.BLACK);
					g1.drawOval(tempX, tempY, 50, 50);
					if(allChess[i][j] instanceof King) {//绘制国王棋子
						tempX = i*50+50;
						tempY = j*50+60;
						g1.setColor(Color.WHITE);
						g1.fillOval(tempX, tempY, 50, 50);
						g1.setColor(Color.BLACK);
						g1.drawOval(tempX, tempY, 50, 50);
						g1.drawOval(tempX+10, tempY+10, 30, 30);
					}
				}
			}
		}
		g.drawImage(buf, 0, 0,this); //将图片显示出来
	}
	
	//初始化棋盘
	private void initChess() {
		//初始化黑棋
		allChess[0][3] = new Black_Chess();
		allChess[0][4] = new Black_Chess();
		allChess[0][5] = new Black_Chess();
		allChess[1][4] = new Black_Chess();
		allChess[3][8] = new Black_Chess();
		allChess[4][8] = new Black_Chess();
		allChess[5][8] = new Black_Chess();
		allChess[4][7] = new Black_Chess();
		allChess[3][0] = new Black_Chess();
		allChess[4][0] = new Black_Chess();
		allChess[5][0] = new Black_Chess();
		allChess[4][1] = new Black_Chess();
		allChess[8][3] = new Black_Chess();
		allChess[8][4] = new Black_Chess();
		allChess[8][5] = new Black_Chess();
		allChess[7][4] = new Black_Chess();
		//初始化白棋
		allChess[2][4] = new White_Chess();
		allChess[3][4] = new White_Chess();
		allChess[5][4] = new White_Chess();
		allChess[6][4] = new White_Chess();
		allChess[4][2] = new White_Chess();
		allChess[4][3] = new White_Chess();
		allChess[4][5] = new White_Chess();
		allChess[4][6] = new White_Chess();
		//初始化国王
		allChess[4][4] = new King();
	}
	
	public int x1,y1,x2,y2;//(x1,y1)表示选择的棋子位置的坐标(x2,y2)表示移动的空格的位置坐标
	public int[] max = new int[4]; //max列表表示可以移动的最远距离 0-left；1-right；2-up；3-down；
	//mouseClicked函数用于选择棋子并且移动棋子（改变列表的内部构造）
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int i = (x-50)/50;
		int j = (y-60)/50;
		if(i>=0 && i<=8 && j<=8 && j>=0) { //判断鼠标是否在棋盘以内，不然直接退出
			//进行棋子的选择
			if(first) {
				x1 = i;
				y1 = j;
				if(allChess[i][j]!=null) {
					//黑棋和白棋的出棋顺序
					if((allChess[i][j].Color == "black" && step%2!=0) || (allChess[i][j].Color == "white" && step%2==0)) {
						currentChess = allChess[i][j]; //选中要移动的棋子
						//选中的棋子的移动最远距离的列表
						for(int n=1;n<=x1;n++) { //left
							if(allChess[x1-n][y1]!=null) {max[0] = n-1;break;}
							else{max[0] = x1;}
						}
						for(int n=1;n<=8-x1;n++) { //right
							if(allChess[x1+n][y1]!=null) {max[1] = n-1;break;} 
							else{max[1] = 8-x1;}
						}
						for(int n=1;n<=y1;n++) { //up
							if(allChess[x1][y1-n]!=null) {max[2] = n-1;break;} 
							else{max[2] = y1;}
						}
						for(int n=1;n<=8-y1;n++) { //down
							if(allChess[x1][y1+n]!=null) {max[3] = n-1;break;} 
							else{max[3] = 8-y1;}
						}
						allChess[i][j] = null; //选中的棋子必须进行移动
						second = true; //选中完毕，可以执行第二步
						first = false; //不能再进行第一步
						//System.out.println("left："+max[0]+"right"+max[1]+"up"+max[2]+"down"+max[3]);
					}
					else {
						JOptionPane.showMessageDialog(null,"请选择自己的棋子");
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"请选择棋子");
				}
			}
			else {
				x2 = i;
				y2 = j;
				if(second) {
					//控制棋子移动位置,具体算法是棋子只能移动到上下左右四个方向小于最大值的距离
					if(x1==x2 && y1==y2) {
						JOptionPane.showMessageDialog(null,"请移动棋子");
					}
					else {
						boolean go = go(x1,x2,y1,y2,max); //进行go的判定
						if(go) {
							allChess[i][j] = currentChess; //将棋子放入空的列表中
							eatChess(currentChess); //进行吃子操作
							this.repaint(); //进行重绘
							second = false; //不能执行第二步
							first = true; //可以执行第一步
							step+=1; //步数+=1
							message2 = "移动了一个棋子";
							int w = isWin(currentChess); //判断输赢
							if(w == 1) {
								JOptionPane.showMessageDialog(null,"白棋赢");
							}
							else if(w == 2) {
								JOptionPane.showMessageDialog(null,"黑棋赢");
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"不能移动到那里");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"请选择自己的棋子");
				}
			}
		}
		//重新开始
		else if(x<=600 && x>=500 && y>=80 && y<=120) {
			restart();
		}
		//游戏规则
		else if(x<=600 && x>=500 && y>=230 && y<=270) {
			//弹窗方法
			JOptionPane.showMessageDialog(null,"这是一个古典象棋游戏，分为黑棋白棋两大阵营，其中黑棋先走。\r\n"
					+ "玩法：棋子走法相当于象棋中的“车”，只要没障碍一次可以横着竖着走多格。如果我方的两个棋子将对方的棋子夹在中间，对方的棋子就被吃了。\r\n" + 
					"胜负条件：白子最中间的棋叫国王。黑棋吃掉国王则黑子胜，国王逃跑到棋盘外则白棋胜。(将军屁股底下那个位置为障碍不能通过)");
		}
		else {
			JOptionPane.showMessageDialog(null,"请在棋盘内点击");
		}
	}
	
	//isWin()判断是否赢的胜利的函数,1代表白棋赢,2代表黑棋赢,0代表继续
	public int isWin(Chess c) {
		int win = 0;
		boolean black = true;
		for(int i=0;i<=8;i++) {
			for(int j=0;j<=8;j++) {
				if(allChess[i][j] instanceof King) {
					int[] ls = getP(c);
					System.out.println(ls[0]);
					if(c instanceof King) {
						if(ls[0]==0||ls[1]==0||ls[0]==8||ls[1]==8) {
							win = 1;
						}
					}
					black = false;
				}
			}
		}
		if(black) {
			win = 2;
		}
		return win;
	}
	
	//返回一个棋子的位置
	public int[] getP(Chess c) {
		int[] ls = new int[2];
		for(int i=0;i<=8;i++) {
			for(int j=0;j<=8;j++) {
				if(allChess[i][j] == c) {
					ls[0]=i;
					ls[1]=j;
				}
			}
		}
		return ls;
	}
	
	//go函数用于移动棋子并且限制棋子移动距离
	public boolean go(int x1,int x2,int y1,int y2,int[] max) {
		boolean go = false;
		if(y1 == y2) {
			if(x1-x2>=0 && x1-x2<=max[0]) {
				go = true;
			}
			else if(x1-x2<0 && x2-x1<=max[1]) {
				go = true;
			}
		}
		else if(x1 == x2) {
			if(y1-y2>=0 && y1-y2<=max[2]) {
				go = true;
			}
			else if(y1-y2<0 && y2-y1<=max[3]) {
				go = true;
			}
		}
		return go;
	}
	
	//eatChess()用于吃子,具体算法为currentChess周围是否存在被吃的棋子
	public void eatChess(Chess c) {
		int[] ls = getP(c); //存储棋子位置，ls[0]=i;ls[1]=j
		int i = ls[0];
		int j = ls[1];
		String cc = c.Color;
		//判断棋子上是否存在棋子
		if(i-1>=0) {
			if(allChess[i-1][j]!=null) {
				if(i-1==0 && cc.equals(allChess[i-1][j].Color)==false) {
					allChess[i-1][j] = null;
				}
				else if(i-1>0 && allChess[i-2][j]!=null) {
					if(cc.equals(allChess[i-1][j].Color)==false && cc.equals(allChess[i-2][j].Color)) {
						allChess[i-1][j] = null;
					}
				}
			} 
		}
		if(i+1<=8) {
			if(allChess[i+1][j]!=null) {
				if(i+1==8 && cc.equals(allChess[i+1][j].Color)==false) {
					allChess[i+1][j] = null;
				}
				else if(i+1<8 && allChess[i+2][j]!=null) {
					if(cc.equals(allChess[i+1][j].Color)==false && cc.equals(allChess[i+2][j].Color)) {
						allChess[i+1][j] = null;
					}
				}
			}
		}
		if(j-1>=0) {
			if(allChess[i][j-1]!=null){
				if(j-1==0 && cc.equals(allChess[i][j-1].Color)==false) {
					allChess[i][j-1] = null;
				}
				else if(j-1>0 && allChess[i][j-2]!=null) {
					if(cc.equals(allChess[i][j-1].Color)==false && cc.equals(allChess[i][j-2].Color)) {
						allChess[i][j-1] = null;
					}
				}
			} 
		}
		if(j+1<=8) {
			if(allChess[i][j+1]!=null){
				if(j-1==0 && cc.equals(allChess[i][j+1].Color)==false) {
					allChess[i][j+1] = null;
				}
				else if(j+1<8 && allChess[i][j+2]!=null) {
					if(cc.equals(allChess[i][j+1].Color)==false && cc.equals(allChess[i][j+2].Color)) {
						allChess[i][j+1] = null;
					}
				}
			} 
		}
	}
	
	//restart()方法用于重新开始游戏
	public void restart() {
		for(int i=0;i<=8;i++) {
			for(int j=0;j<=8;j++) {
				allChess[i][j] = null;
			}
		}
		initChess();
		repaint();
		message2 = "";
		step = 1;
	}
	
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		FI f1 = new FI();
	}

	//必须实现的抽象方法，可以不予理会
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
