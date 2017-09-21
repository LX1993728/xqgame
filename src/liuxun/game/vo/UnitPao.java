package liuxun.game.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 定义"炮"类型棋子
 * 
 * @author liuxun
 *
 */
public class UnitPao extends Unit {

	public UnitPao() {
		this.setP(1); // 设置优先级
	}

	public UnitPao(String name, Person curU, Person otherU) {
		this();
		this.setName(name);
		this.setCurrentU(curU);
		this.setOtherU(otherU);
	}
	public UnitPao(String name, Person curU, Person otherU, int x, int y) {
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
					// 定义位置加减范围
					Integer xRan[] = { 1, 2, 3, 4, 5, 6, 7 };
					Integer yRan[] = { 1, 2, 3 };
					List<Integer> xRanList = Arrays.asList(xRan);
					List<Integer> yRanList = Arrays.asList(yRan);

					List<Integer> xList = new ArrayList<Integer>(); // 横向可能位置数组(位置+
																	// -)
					List<Integer> yList = new ArrayList<Integer>(); // 纵向可能位置数组(位置+
																	// -)

					// 查找所要吃的棋子横向可能所在的位置
					for (int i = 0; i <= xRanList.size() - 1; i++) { // xJZ ：x
																		// 变化的增减量
						Integer x1 = x + xRanList.get(i);
						Integer x2 = x - xRanList.get(i);
						if (x1 <= 7 && x1 >= 1) {
							xList.add(x1);
						}
						if (x2 >= 0 && x2 <= (7 - xRanList.get(i))) {
							xList.add(x2);
						}
					}
					xList.add(x);
					// 查找所要吃的棋子纵向可能所在的位置
					for (int i = 0; i <= yRanList.size() - 1; i++) { // xJZ ：x
																		// 变化的增减量
						Integer y1 = y + yRanList.get(i);
						Integer y2 = y - yRanList.get(i);
						if (y1 <= 3 && y1 >= 1) {
							yList.add(y1);
						}
						if (y2 >= 0 && y2 <= (3 - yRanList.get(i))) {
							yList.add(y2);
						}
					}
					yList.add(y);
					// 查找所要吃的棋子可能在的所有位置后，需要检查所要吃的棋子位置是否在所有的可能的位置之中
					if (xList.contains(toX) && yList.contains(toY)) {
						int flag = 0; // 横向和纵向只要有一个方向满足之隔一个棋子就OK
						// 横向检查当前棋子和被吃棋子中间是否只有一个棋子
						if (y.intValue() == toY) {
							int count1 = 0;
							Integer min = Math.min(x, toX);
							Integer max = Math.max(x, toX);
							if (max - min > 1) {
								for (int i = min.intValue() + 1; i < max.intValue(); i++) {
									if (this.getQp()[i][toY] != null) {
										count1++;
									}
								}
							}
							if (count1 == 1) {
								flag = 1;
							}
						}
						// 纵向检查当前棋子和被吃棋子中间是否只有一个棋子
						if (flag == 0 && x.intValue() == toX) { // 如果横向不是只间隔一个棋子，则检查纵向
							int count2 = 0;
							Integer min2 = Math.min(y, toY);
							Integer max2 = Math.max(y, toY);
							if (max2 - min2 > 1) {
								for (int i = min2.intValue() + 1; i < max2.intValue(); i++) {
									if (this.getQp()[toX][i] != null) {
										count2++;
									}
								}
							}
							if (count2 == 1) {
								flag = 1;
							}
						}
						if (flag == 1) { // 如果所要吃的棋子满足位置要求
							// 需要检查被吃的棋子是否是对方的
							if (this.getQp()[toX][toY].getCurrentU() != this.getCurrentU()) {
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
			}
		}
		return false;
	}

}
