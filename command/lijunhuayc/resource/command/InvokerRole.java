package com.command;

/**
 * @Desc: 命令调用[调用者角色]
 * ps:使用命令对象的入口，扶着调用命令对象执行请求
 * @author ljh
 * @date 2015-3-16 上午11:16:15
 */
public class InvokerRole {
	private Command command1;
	private Command command2;
	//持有多个命令对象[实际的情况也可能是一个命令对象的集合来保存命令对象]
	
	public void setCommand1(Command command1) {
		this.command1 = command1;
	}
	public void setCommand2(Command command2) {
		this.command2 = command2;
	}
	
	public void invoke(Command command) {
		//根据具体情况选择执行某些命令
		if(null != command){
			command.execute();
			return;
		}
		command1.execute();
		command2.execute();
	}
	
	
	
}
