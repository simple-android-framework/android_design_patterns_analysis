Android设计模式源码解析之责任链模式 
====================================
> 本文为 [Android 设计模式源码解析](https://github.com/simple-android-framework-exchange/android_design_patterns_analysis) 中责任链模式分析  
> Android系统版本： 4.4.4        
> 分析者：[Aige](https://github.com/AigeStudio)，分析状态：完成，校对者：[SM哥](https://github.com/bboyfeiyu)，校对状态：未开始   
 
## 1. 模式介绍  
 
###  模式的定义
一个请求沿着一条“链”传递，直到该“链”上的某个处理者处理它为止。


### 模式的使用场景
 一个请求可以被多个处理者处理或处理者未明确指定时。
 

## 2. UML类图
制作中

### 角色介绍
你猜




## 3. 模式的简单实现
###  简单实现的介绍
责任链模式非常简单异常好理解，相信我它比单例模式还简单易懂，其应用也几乎无所不在，甚至可以这么说……从你敲代码的第一天起你就不知不觉用过了它最原始的裸体结构：分支语句：
```java
public class SimpleResponsibility {
	public static void main(String[] args) {
		int request = (int) (Math.random() * 3);
		switch (request) {
		case 0:
			System.out.println("SMBother handle it: " + request);
			break;
		case 1:
			System.out.println("Aige handle it: " + request);
			break;
		case 2:
			System.out.println("7Bother handle it: " + request);
			break;
		default:
			break;
		}
	}
}
```
谁敢说没用过上面这种结构体的站出来我保证不打屎他，没用过swith至少if-eles用过吧，if-eles都没用过你怎么知道github的……上面的这段代码其实就是一种最最简单的责任链模式，其根据request的值进行不同的处理。当然这只是个不恰当的例子来让大家尽快对责任链模式有个简单的理解，因为可能很多童鞋第一次听说这个模式，而人对未知事物总是恐惧的，为了消除大家的这种恐惧，我将大家最常见的code搬出来相信熟悉的代码对大家来说有一种亲切的感觉，当然我们实际应用中的责任链模式绝逼不是这么Mr.Simple，但是也不会复杂不到哪去。责任链模式，顾名思义，必定与责任Responsibility相关，其实质呢就像上面定义中说的那样一个请求（比如上面代码中的request值）沿着一条“链”（比如上面代码中我们的switch分支语句）传递，当某个处于“链”上的处理者（case定义的条件）处理它时完成处理。其实现实生活中关于责任者模式的例子数不胜数，最常见的就是工作中上下级之间的责任请求关系了。比如：
>程序猿狗屎运被派出去异国出差一周，这时候就要去申请一定的差旅费了，你心里小算一笔加上各种车马费估计大概要个两三万，于是先向小组长汇报申请，可是大于一千块小组长没权利批复，于是只好去找项目主管，项目主管一看妈蛋这么狠要这么多我只能批小于五千块的，于是你只能再跑去找部门经理，部门经理看了下一阵淫笑后说没法批我只能批小于一万的，于是你只能狗血地去跪求老总，老总一看哟！小伙子心忒黑啊！老总话虽如此但还是把钱批给你了毕竟是给公司办事，到此申请处理完毕，你也可以屁颠屁颠地滚了。

如果把上面的场景应用到责任链模式，那么我们的request请求就是申请经费，组长主管经理老总们就是一个个具体的责任人他们可以对请求做出处理但是他们只能在自己的责任范围内处理该处理的请求，而程序猿只是个底层狗请求者向责任人们发起请求…………苦逼的猿。

### 实现源码
上面的场景我们可以使用使用如下的代码来模拟实现：

首先定义一个程序员类：
```Java
/**
 * 程序猿类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class ProgramApe {
	private int expenses;// 声明整型成员变量表示出差费用
	private String apply = "爹要点钱出差";// 声明字符串型成员变量表示差旅申请

	/*
	 * 含参构造方法
	 */
	public ProgramApe(int expenses) {
		this.expenses = expenses;
	}

	/*
	 * 获取程序员具体的差旅费用
	 */
	public int getExpenses() {
		return expenses;
	}

	/*
	 * 获取差旅费申请
	 */
	public String getApply() {
		return apply;
	}
}
```

然后依次是各个大爷类：

```Java
/**
 * 小组长类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class GroupLeader {

	/**
	 * 处理请求
	 * 
	 * @param ape
	 *            具体的猿
	 */
	public void handleRequest(ProgramApe ape) {
		System.out.println(ape.getApply());
		System.out.println("GroupLeader: Of course Yes!");
	}
}
```

```Java
/**
 * 项目主管类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class Director {
	/**
	 * 处理请求
	 * 
	 * @param ape
	 *            具体的猿
	 */
	public void handleRequest(ProgramApe ape) {
		System.out.println(ape.getApply());
		System.out.println("Director: Of course Yes!");
	}
}
```

```Java
/**
 * 部门经理类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class Manager {
	/**
	 * 处理请求
	 * 
	 * @param ape
	 *            具体的猿
	 */
	public void handleRequest(ProgramApe ape) {
		System.out.println(ape.getApply());
		System.out.println("Manager: Of course Yes!");
	}
}
```

```Java
/**
 * 老总类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class Boss {
	/**
	 * 处理请求
	 * 
	 * @param ape
	 *            具体的猿
	 */
	public void handleRequest(ProgramApe ape) {
		System.out.println(ape.getApply());
		System.out.println("Boss: Of course Yes!");
	}
}
```

好了，万事俱备只欠场景，现在我们模拟一下整个场景过程：

```Java
/**
 * 场景模拟类
 * 
 * @author Aige{@link https://github.com/AigeStudio}
 *
 */
public class Client {
	public static void main(String[] args) {
		/*
		 * 先来一个程序猿 这里给他一个三万以内的随机值表示需要申请的差旅费
		 */
		ProgramApe ape = new ProgramApe((int) (Math.random() * 30000));

		/*
		 * 再来四个老大
		 */
		GroupLeader leader = new GroupLeader();
		Director director = new Director();
		Manager manager = new Manager();
		Boss boss = new Boss();

		/*
		 * 处理申请
		 */
		if (ape.getExpenses() <= 1000) {
			leader.handleRequest(ape);
		} else if (ape.getExpenses() <= 5000) {
			director.handleRequest(ape);
		} else if (ape.getExpenses() <= 10000) {
			manager.handleRequest(ape);
		} else {
			boss.handleRequest(ape);
		}
	}
}
```

运行一下，我的结果输出如下（注：由于随机值的原因你的结果也许与我不一样）：

>爹要点钱出差
>Manager: Of course Yes!


### 总结
`对上述的简单示例进行总结说明`

  


## Android源码中的模式实现
`分析源码中的模式实现，列出相关源码，以及使用该模式原因等`  


 

## 4. 杂谈
该模式的优缺点以及自己的一些感悟，非所有项目必须。  



`写完相关内容之后到开发群告知管理员，管理员安排相关人员进行审核，审核通过之后即可。`  

