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
	
	/**
	 * @Description: 
	 * @author (ljh) @date 2015-3-16 下午1:40:54 
	 * @param args 0执行正常命令，1执行回滚命令
	 * @return void
	 */
	public void invoke(int args) {
		//可以根据具体情况选择执行某些命令
		if(args == 0){
			command1.execute();
			command2.execute();
		}else if(args == 1){
			command1.undo();
			command2.undo();
		}
	}
	
}
