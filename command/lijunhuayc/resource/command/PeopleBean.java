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
	public void update(int age) {
		this.age = age;
	}
	public void update(String name) {
		this.name = name;
	}
	/**
	 * @return 返回一个PeopleBean的克隆对象
	 */
	protected PeopleBean clone(){
		return new PeopleBean(age, name);
	}
	@Override
	public String toString() {
		return " 【年龄：" + age + "\t姓名：" + name + "】";
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
