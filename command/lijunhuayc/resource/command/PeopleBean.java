package com.command;

/**
 * @Desc: 辅助类，作为接收者Receiver的成员，包含两个属性，用来观察命令的执行情况
 * @author ljh
 * @date 2015-3-16 上午11:29:11
 */
public class PeopleBean {
	private int age = -1;	//年龄
	private String name = "NULL";	//姓名
	
	public PeopleBean() {
	}
	public PeopleBean(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public void update(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	/**
	 * @return 返回一个PeopleBean的克隆对象
	 */
	protected PeopleBean clone(){
		return new PeopleBean(age, name);
	}
	
	/**
	 * @Description: 还原操作
	 * @author (ljh) @date 2015-3-16 上午11:45:23 
	 * @param cache 
	 * @return void
	 */
	protected void undo(PeopleBean cache) {
		this.age = cache.age;
		this.name = cache.name;
	}
	
	@Override
	public String toString() {
		return " 【姓名：" + name + "\t年龄：" + age + "】\n";
	}
	
}
