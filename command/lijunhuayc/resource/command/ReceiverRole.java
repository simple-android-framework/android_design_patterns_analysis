package com.command;

/**
 * @Desc: 命令的具体执行类[接受者角色]
 * ps：命令接收者可以是任意的类，只要实现了命令要求实现的相应功能即可。
 * @author ljh
 * @date 2015-3-16 上午11:06:14
 */
public class ReceiverRole {
	private PeopleBean people;
	private PeopleBean peopleCache = new PeopleBean(); //具体命令操作的缓存栈，用于回滚。这里为了方便就用一个PeopleBean来代替[实际的使用情况可能是需要回滚多个命令，这里只回滚一次]
	
	public ReceiverRole() {
		this.people = new PeopleBean(-1, "NULL");//初始化年龄为-1，姓名为NULL
	}
	
	public ReceiverRole(PeopleBean people) {
		this.people = people;
	}
	
	/**
	 * @Description: 具体操作方法[修改年龄和姓名]
	 * @author (ljh) @date 2015-3-16 上午11:07:32  
	 * @return void
	 */
	//修改年龄
	public void opActionUpdateAge(int age) {
		System.out.println("执行命令前："+people.toString());
		this.people.update(age);
		System.out.println("执行命令后："+people.toString()+"\n");
	}
	//修改姓名
	public void opActionUpdateName(String name) {
		System.out.println("执行命令前："+people.toString());
		this.people.update(name);
		System.out.println("执行命令后："+people.toString()+"\n");
	}
	
	/**
	 * @Description: 回滚操作，用于撤销opAction执行的改变
	 * @author (ljh) @date 2015-3-16 上午11:34:41  
	 * @return void
	 */
	public void rollBackAge() {
		people.setAge(peopleCache.getAge());
		System.out.println("命令回滚后："+people.toString()+"\n");
	}
	public void rollBackName() {
		people.setName(peopleCache.getName());
		System.out.println("命令回滚后："+people.toString()+"\n");
	}
	
}
