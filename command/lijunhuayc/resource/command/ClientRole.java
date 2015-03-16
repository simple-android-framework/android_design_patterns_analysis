package com.command;

/**
 * @Desc: 命令对象和接受者对象的组装类[客户角色]
 * ps：我这把类名定义成ClientRole更方便读者理解这只是命令模式中的一个客户角色，而不是我们常规意义上说的客户端
 * @author ljh
 * @date 2015-3-16 上午11:08:03
 */
public class ClientRole {
	
	/**
	 * @Description:
	 * @author (ljh) @date 2015-3-16 上午11:13:06  
	 * @return void
	 */
	public void assembleAction() {
		ReceiverRole receiverRole1 = new ReceiverRole();//创建一个命令接收者
		Command command1 = new ConcreteCommandImpl1(receiverRole1);//创建一个命令的具体实现对象，并指定命令接收者
		Command command2 = new ConcreteCommandImpl2(receiverRole1);
		
		//PS：command1 修改people 年龄
		//PS：command2 修改people 姓名
		//PS：command23修改people 年龄和姓名

		InvokerRole invokerRole = new InvokerRole();//创建一个命令调用者
		invokerRole.setCommand1(command1);//为调用者指定命令对象1
		invokerRole.setCommand2(command2);//为调用者指定命令对象2
		invokerRole.invoke(0);//发起调用命令请求
		invokerRole.invoke(1);//发起调用命令请求
		
	}
	
}
