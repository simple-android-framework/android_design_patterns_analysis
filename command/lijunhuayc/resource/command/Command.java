package com.command;

/**
 * @Desc: 命令接口[命令角色]
 * ps：你也可以定义成abstract class类型	*_*
 * @author ljh
 * @date 2015-3-16 上午11:01:01
 */
public interface Command {
	public void execute();
	public void undo();
	public void redo();
	
}
