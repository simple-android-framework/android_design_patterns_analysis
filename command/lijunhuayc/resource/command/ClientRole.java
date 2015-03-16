package com.command;

/**
 * @Desc: 命令对象和接受者对象的组装类[客户角色]
 * ps：我这把类名定义成ClientRole更方便读者理解这只是命令模式中的一个客户角色，而不是我们常规意义上说的客户端
 * @author ljh
 * @date 2015-3-16 上午11:08:03
 */
public class ClientRole {
	
	/**
	 * @Description: 组装操作
	 * @author (ljh) @date 2015-3-16 上午11:13:06  
	 * @return void
	 */
	public void assembleAction() {
		ReceiverRole receiverRole1 = new ReceiverRole();//创建一个命令接收者
		Command command1 = new ConcreteCommandImpl1(receiverRole1);//创建一个命令的具体实现对象，并指定命令接收者
		
		ReceiverRole receiverRole2 = new ReceiverRole(new PeopleBean(9999, "神仙"));
		Command command2 = new ConcreteCommandImpl2(receiverRole2);
		
		//PS：command1 修改people为(18, "西北狼")
		//PS：command2 修改people为(35, "lijunhuayc")
		
		InvokerRole invokerRole = new InvokerRole();//创建一个命令调用者
		invokerRole.setCommand1(command1);//为调用者指定命令对象1
		invokerRole.setCommand2(command2);//为调用者指定命令对象2
		invokerRole.invoke(null);//发起调用命令请求
		
		ReceiverRole receiverRole3 = new ReceiverRole(new PeopleBean(9999, "神仙"));
		Command command3 = new ConcreteCommandImpl3(receiverRole2);
		invokerRole.invoke(command3);//发起调用命令请求
		
	}
	
}
