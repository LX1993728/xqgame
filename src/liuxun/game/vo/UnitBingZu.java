package liuxun.game.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义兵卒类型的棋子
 * 
 * @author liuxun
 *
 */
public class UnitBingZu extends Unit {
	public UnitBingZu() {
		this.setP(-1); // 设置优先级
	}

	public UnitBingZu(String name, Person curU, Person otherU) {
		this();
		this.setName(name);
		this.setCurrentU(curU);
		this.setOtherU(otherU);
	}
	public UnitBingZu(String name, Person curU, Person otherU, int x, int y) {
		this(name, curU, otherU);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public boolean cz(int toX, int toY) {
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
					Integer y3 = y; // 纵向可能位置3
					List<Integer> xCandadite = new ArrayList<Integer>(); // 横向候选位置数组
					List<Integer> yCandadite = new ArrayList<Integer>(); // 纵向候选位置数组
					if (x1 >= 1 && x1 <= 7) {
						xCandadite.add(x1);
					}
					if (x2 >= 0 && x2 <= 6) {
						xCandadite.add(x2);
					}
					xCandadite.add(x3);
					if (y1 >= 1 && y1 <= 3) {
						yCandadite.add(y1);
					}
					if (y2 >= 0 && y2 <= 2) {
						yCandadite.add(y2);
					}
					yCandadite.add(y3);
					// 兵卒要吃棋子的位置在候选位置中且该位置不为空且优先级等于将帅的优先级且已翻牌且为对方的牌
					if (xCandadite.contains(toX) && yCandadite.contains(toY) && this.getQp()[toX][toY] != null
							&& this.getQp()[toX][toY].getP() == 6 && this.getQp()[toX][toY].getStatus()== 1 
							&& this.getQp()[toX][toY].getCurrentU()!=this.getCurrentU()) {
						// 移除所要吃的棋子
						this.getQp()[toX][toY] = this;
						this.getQp()[this.getX()][this.getY()] = null;
						this.setX(toX);
						this.setY(toY);
						this.changeUserStatus();
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
