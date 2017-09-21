package liuxun.game.service;

import liuxun.game.vo.Person;
import liuxun.game.vo.Unit;

public interface QpService {
	// 初始化棋盘布局
	public Unit[][] intialQp(Unit[][] qp,Person currentU,Person otherU);
}
