package liuxun.game.vo;
/**
 * 每个棋子要执行的动作
 * @author liuxun
 *
 */
public interface DZ {
  public boolean fz(); //翻子
  public boolean move(int toX,int toY); //移动
  public boolean dz(int toX,int toY); //兑子
  public boolean cz(int toX,int toY); //吃子
}
