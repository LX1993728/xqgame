package liuxun.game.junit;

import java.util.Random;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import liuxun.game.service.QpServiceImpl;
import liuxun.game.vo.Person;
import liuxun.game.vo.Unit;

public class TestDemo {

	private Unit[][] result = null;
	Person currentU = null;
	Person otherU = null;

	@Before
	public void Initial() {
		Person person1 = new Person(1, "张三");
		Person person2 = new Person(1, "李四");
		int flag = new Random().nextInt(2); // 选取红黑方（模拟手动第一次翻子）, 实际上是根据cookie检查
											// 哪个客户端先翻的子
		if (flag == 0) {
			currentU = person1;
			otherU = person2;
		} else {
			otherU = person1;
			currentU = person2;
		}
		Unit[][] sourceQp = new Unit[8][4];
		result = new QpServiceImpl().intialQp(sourceQp, currentU, otherU);
		// 初始化(新开局后)棋盘如下===========
		printQP();
	}

	private void printQP() {
		if (result != null) {
			System.out.println(result);
			// 打印棋盘
			for (int j = 0; j <= 3; j++) {
				for (int i = 0; i <= 7; i++) {
					if (result[i][j] != null) {
						String uF = (result[i][j].getCurrentU() == currentU) ? "红" : "黑";
						String stat = result[i][j].getStatus() == 1 ? "正" : "反";
						System.out.print(result[i][j].getName() + "(" + uF + " " + stat + ")" +"["+i+","+j+"]"+ "\t\t");
					} else {
						System.out.print("[空]" + "\t\t");
					}
				}
				System.out.println();
			}
		} else {
			System.out.println("初始化异常");
		}
	}

	@Test
	public void test() { // 测试
		// 翻子（1，1）
		boolean fz = result[1][1].fz();
		System.out.println(fz ? (result[1][1].getCurrentU() == currentU) ? "红" : "黑" + "方翻子成功"
				: (result[1][1].getCurrentU() == currentU) ? "红" : "黑" + "方翻子失败");
		boolean fz2 = result[1][1].fz();
		System.out.println(fz2 ? ((result[1][1].getCurrentU() == currentU) ? "红" : "黑" + "方翻子成功")
				: ((result[1][1].getCurrentU() == currentU) ? "红" : "黑" + "方翻子失败"));
		printQP();
		Scanner scanner = new Scanner(System.in);
		boolean fzOther = true;
		do {
			System.out.println("请输入另一方所要翻的子：");
			int x2 = scanner.nextInt();
			int y2 = scanner.nextInt();
			fzOther = result[x2][y2].fz();

		} while (!fzOther);
		printQP();

		boolean flag = true;
		do {

			// 当前方要做的事情
			System.out.println("============操作类型序号说明===========");
			System.out.println("============[-1]退出程序===========");
			System.out.println("============[0]进行翻子操作===========");
			System.out.println("============[1]进行移动操作===========");
			System.out.println("============[2]进行兑子操作===========");
			System.out.println("============[3]进行吃子操作===========");
			if (currentU.getFlag() == 0) {
				System.out.println("============请红方输入操作类型:============");
			} else if (otherU.getFlag() == 0) {
				System.out.println("============请黑方输入操作类型:============");

			}
			int type = scanner.nextInt();
			switch (type) {
			case 0: {
				System.out.println("请输入所要翻子的位置[x y]");
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				boolean f = result[x][y].fz();
				System.out.println(f ? ((result[x][y].getCurrentU() == currentU) ? "红方翻子成功" : "黑方翻子成功")
						: ((result[x][y].getCurrentU() == currentU) ? "红方翻子失败" : "黑方翻子失败"));
			}
				break;
			case 1: {
				System.out.println("请输入当前操作棋子的位置:");
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				System.out.println("请输入要移动到的位置:");
				int toX = scanner.nextInt();
				int toY = scanner.nextInt();
				Unit unitFrom = result[x][y];
				boolean f = result[x][y].move(toX, toY);
				System.out.println(f ? ((unitFrom.getCurrentU() == currentU) ? "红方移动成功" : "黑方移动成功")
						: ((unitFrom.getCurrentU() == currentU) ? "红方移动失败" : "黑方移动失败"));

			}
				break;
			case 2: {
				System.out.println("请输入当前操作棋子的位置:");
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				System.out.println("请输入要要兑换棋子的位置:");
				int toX = scanner.nextInt();
				int toY = scanner.nextInt();
				Unit unitFrom = result[x][y];
				boolean f = result[x][y].dz(toX, toY);
				System.out.println(f ? ((unitFrom.getCurrentU() == currentU) ? "红方兑子成功" : "黑方兑子成功")
						: ((unitFrom.getCurrentU() == currentU) ? "红方兑子失败" : "黑方兑子失败"));

			}

				break;
			case 3:
			{
				System.out.println("请输入当前操作棋子的位置:");
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				System.out.println("请输入要吃的棋子的位置:");
				int toX = scanner.nextInt();
				int toY = scanner.nextInt();
				Unit unitFrom = result[x][y];
				boolean f = result[x][y].cz(toX, toY);
				System.out.println(f ? ((unitFrom.getCurrentU() == currentU) ? "红方吃子成功" : "黑方吃子成功")
						: ((unitFrom.getCurrentU() == currentU) ? "红方吃子失败" : "黑方吃子失败"));

			}
				break;
			case -1:
				flag = false;
				break;
			default:
				System.out.println("请输入合法值!!!");
				break;
			}
			printQP();
		} while (flag = true);
	}
}
