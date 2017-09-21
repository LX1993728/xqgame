package liuxun.game.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义棋子的抽象类
 * @author liuxun
 *
 */
public abstract class Unit implements DZ {
	private Unit[][] qp= null; // 定义当前棋盘布局对于一方而言有 1个将/帅  2*{车、马、炮、相/象、士/仕}  5*{兵/卒}
	private int p; // 当前棋子的优先级 将帅:6  仕士:5  象相:4 马:3 车:2  炮:1  兵卒:-1
	private int status = 0; //当前棋子状态- 0：未翻牌 1：已翻牌 
    private int x,y; //当前棋子的位置
    private Person currentU; // 当前用户
    private Person otherU; // 对手
    private String name; // 当前棋子名称
    
	public boolean fz() { // 各类棋子翻子的方法是相同的
		// 如果当前用户可以执行动作 并且 当前棋子未翻牌
		if (this.status == 0 && this.currentU.getFlag() == 0) {
			//改变当前棋子状态
			this.setStatus(1);
			this.changeUserStatus();
			return true;
		}
		return false;
	}
   
	public boolean move(int toX, int toY) {  //各类棋子移动位置的方法是相同的
		return this.commonMoveAndDZ(toX, toY, 0);
	}
	
	public boolean dz(int toX, int toY) { // //各类棋子兑子的方法是相同的
		return this.commonMoveAndDZ(toX, toY, 1);
	}
	
	public abstract boolean cz(int toX,int toY);
	//===========抽取公共部分==================
	
	// 改变双方下棋顺序
	protected void changeUserStatus(){
		this.currentU.setFlag(1);
		this.otherU.setFlag(0);
	}
	
	// 抽取移动和兑子的公共部分
	private boolean commonMoveAndDZ(int toX, int toY,int flag){ // flag- 0:move  1：dz
		if (this.getQp() != null) { // 棋盘存在
			// 如果当前用户可以执行动作并且当前棋子已经翻牌
			if (this.getStatus() == 1 && this.getCurrentU().getFlag() == 0) {
				Integer x = this.getX();
				Integer y = this.getY();

				// 保证当前棋子位置和移动的位置在棋盘范围 且不可是同一位置
				if (x >= 0 && x <= 7 && toX >= 0 && toX <= 7 && y >= 0 && y <= 3 && toY >= 0 && toY <= 7
						&& !(x == toX && y == toY)) {
					Integer x1 = x + 1; // 横向可能位置1
					Integer x2 = x - 1; // 横向可能位置2
					Integer x3 = x ; // 横向可能位置3
					Integer y1 = y + 1; // 纵向可能位置1
					Integer y2 = y - 1; // 纵向可能位置2
					Integer y3 = y ; // 纵向可能位置3
					List<Integer> xCandadite = new ArrayList<Integer>(); // 横向候选位置数组
					List<Integer> yCandadite = new ArrayList<Integer>(); // 纵向候选位置数组
					if (x1 >= 1 && x1 <= 7) {
						xCandadite.add(x1);
					}
					if (x2 >= 0 && x2 <= 6) {
						xCandadite.add(x2);
					}
					xCandadite.add(x);
					if (y1 >= 1 && y1 <= 3) {
						yCandadite.add(y1);
					}
					if (y2 >= 0 && y2 <= 2) {
						yCandadite.add(y2);
					}
					yCandadite.add(y);
					if (flag == 0) {
						// 所要移动的位置在候选位置中且该位置没有棋子 为空(被移除)
						if (xCandadite.contains(toX) && yCandadite.contains(toY)&&this.getQp()[toX][toY] == null) {
							// 将当前棋子放入到所要移动的位置
							this.getQp()[toX][toY] = this;
							this.getQp()[this.getX()][this.getY()] = null;
							this.setX(toX);
							this.setY(toY);
							this.changeUserStatus();
							return true;
						}
						System.out.println("Error:所要移动的位置不在候选位置中或该位置有棋子 不为空(没有被移除)");
						return false;
					} else if(flag == 1){
						// 所要兑子的位置在候选位置中且该位置不为空且优先级相同
						if (xCandadite.contains(toX) && yCandadite.contains(toY) && this.getQp()[toX][toY] != null
								&& this.getQp()[toX][toY].getP() == this.getP()) {
							// 同时移除两个棋子
							this.getQp()[this.getX()][this.getY()] = null;
							this.getQp()[toX][toY] = null;
							this.changeUserStatus();
							return true;
						}
						System.out.println("Error:所要兑子的位置不在候选位置中或该位置为空或优先级不同");
						return false;
					}
					System.out.println("Error:输入方法区分标志有误");
					return false;
				}
				System.out.println("Error:当前棋子位置和移动的位置不在棋盘范围 或者是同一位置");
				return false;
			}
			System.out.println("Exception:当前用户不可以执行动作或者当前棋子未翻牌");
			return false;
		}
		System.out.println("Exception:棋盘不存在");
		return false;
		
	}
	
	//============为变量设置setter和getter方法==================
	
	public Unit[][] getQp() {
		return qp;
	}

	public void setQp(Unit[][] qp) {
		this.qp = qp;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Person getCurrentU() {
		return currentU;
	}

	public void setCurrentU(Person currentU) {
		this.currentU = currentU;
	}

	public Person getOtherU() {
		return otherU;
	}

	public void setOtherU(Person otherU) {
		this.otherU = otherU;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
  
}
