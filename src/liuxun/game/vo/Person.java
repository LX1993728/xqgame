package liuxun.game.vo;
/**
 * 表示下棋的双方
 * @author liuxun
 *
 */
public class Person {
	private int id;
	private String name;
	private int flag ;// 0：表示当前对象可以执行动作 1：当前对象已走，轮到对方执行
	
	public Person() {

	}
	public Person(int id, String name) {
		this.id = id;
		this.name = name;
		this.flag = 0; 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
