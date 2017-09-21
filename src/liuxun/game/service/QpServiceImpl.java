package liuxun.game.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import liuxun.game.vo.Person;
import liuxun.game.vo.Unit;
import liuxun.game.vo.UnitBingZu;
import liuxun.game.vo.UnitJiangShuai;
import liuxun.game.vo.UnitOther;
import liuxun.game.vo.UnitPao;

/**
 * 初始化棋盘
 * 
 * @author liuxun
 *
 */
public class QpServiceImpl implements QpService {

	public Unit[][] intialQp(Unit[][] qp, Person currentU, Person otherU) {
		List<Unit> qzList = new ArrayList<Unit>();
		if (qp != null && currentU != null && otherU != null) {
			for(int i=0;i<2;i++){
				Unit shi1 = new UnitOther("仕", currentU, otherU, 5);
				Unit shi2 = new UnitOther("士", otherU, currentU, 5);
				Unit xiang1 = new UnitOther("相", currentU, otherU, 4);
				Unit xiang2 = new UnitOther("象", otherU, currentU, 4);
				Unit ma1 = new UnitOther("马", currentU, otherU, 3);
				Unit ma2 = new UnitOther("马", otherU, currentU, 3);
				Unit che1 = new UnitOther("车", currentU, otherU, 2);
				Unit che2 = new UnitOther("车", otherU, currentU, 2);
				Unit pao1 = new UnitPao("炮", currentU, otherU);
				Unit pao2 = new UnitPao("炮", otherU, currentU);
				qzList.add(shi1);
				qzList.add(shi2);
				qzList.add(xiang1);
				qzList.add(xiang2);
				qzList.add(ma1);
				qzList.add(ma2);
				qzList.add(che1);
				qzList.add(che2);
				qzList.add(pao1);
				qzList.add(pao2);
			}
			for(int i=1;i<=5;i++){
				Unit bing = new UnitBingZu("兵", currentU, otherU);
				Unit zu = new UnitBingZu("卒", otherU, currentU);
				qzList.add(bing);
				qzList.add(zu);
			}
			Unit jiang = new UnitJiangShuai("将", currentU, otherU);
			Unit shuai = new UnitJiangShuai("帅", otherU, currentU);
			qzList.add(jiang);
			qzList.add(shuai);
			for (Unit unit : qzList) {
				Integer[] randomXY = getRandomXY(qp);
				unit.setX(randomXY[0]);
				unit.setY(randomXY[1]);
				//System.out.println("为棋盘某个坐标赋值"+unit.getX()+" "+unit.getY());
				qp[unit.getX()][unit.getY()] = unit;
			}
		}
		int count =0;
		for(int i=0;i<8;i++){
			for(int j=0;j<4;j++){
				if(qp[i][j] !=null){
					qp[i][j].setQp(qp);
					count++;
				}
			}
		}
		System.out.println("已经初始化个数 "+count);
		return count==32?qp:null;
	}
	
	// 获取一个随机坐标(该坐标必须为空)
	private Integer[] getRandomXY(Unit[][] qp){
		Integer x  = null;
		Integer y = null;
		do{
			Random random = new Random();
			 x = random.nextInt(8);
			 y = random.nextInt(4);
			if(qp[x][y]==null){
				Integer xy[] = new Integer[]{x,y};
				//System.out.println("返回的随机坐标："+x+"\t"+y);
				return  xy;
			}
			// 如果棋盘没有分配满棋子 继续随机获取空的位置 否则继续查找
			int flag = 0; //假设棋盘没有排满
			for(int i=0;i<=7;i++){
				for(int j=0;j<=3;j++){
					if(qp[i][j] ==null){
						flag = 1;
					}
				}
			}
			if (flag == 0) { // 棋子已排满
				return null;
			}
		}while(qp[x][y]!=null);
		return null;
	}
	
	
}
