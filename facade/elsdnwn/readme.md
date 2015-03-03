Android设计模式源码解析之外观模式(Facade)
====================================
> 本文为 [Android 设计模式源码解析](https://github.com/simple-android-framework/android_design_patterns_analysis) 中 外观模式 分析  
> Android系统版本： 2.3         
> 分析者：[elsdnwn](https://github.com/elsdnwn)，分析状态：已完成，校对者：[Mr.Simple](https://github.com/bboyfeiyu)，校对状态：未开始   

`问题有两个: 1、简单实现的示例需要更具体一些，比如模拟使用facade模式来实现某个确切功能；2、没有对Android源码中使用的Facade模式进行分析。`    可以参考[Mr.Simple的单例模式分析](../../singleton/mr.simple)  


## 1. 模式介绍  
 
###  模式的定义
  外观模式(Facade)，又称门面模式，为子系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口，让客户端直接调用，实现了客户端和子系统中模块的解耦，让客户端更容易的使用此系统。

### 模式的使用场景
	1、在设计初期阶段，将不同的两个层分离；
	2、在开发阶段，子系统往往因为不断的重构演化而变得越来越复杂，大多数的模式使用时也都会产生很多很小的类，这本是好事，但也给外部调用它们的用户程序带来了使用上的困难，增加外观Facade可以提供一个简单的接口，减少它们之间的依赖。
	3、在维护一个遗留的大型系统时，可能这个系统已经非常难以维护和扩展了，但因为它包含非常重要的功能，新的需求开发必须依赖于它。

## 2. UML类图
 ![url](images/facade-elsdnwn-uml.png)

### 角色介绍
* CustomerA : 顾客A去超市购买所需物品。
* CustomerB : 顾客B去超市购买所需物品。
* Supermarket : 超市，专门销售厂商的物品，供顾客购买。
* Towel : 毛巾类，TowelFactory:生成毛巾的厂商。
* Vegetables : 蔬菜类，VegetablesFactory:种植蔬菜的厂商。
* Computer : 电脑类，ComputerFactory:生产电脑的厂商。


## 3. 模式的简单实现
###  简单实现的介绍
外观模式比较简单，在开发过程中也经常用到，所以直接贴代码。

### 实现源码

```
package com.elsdnwn.Facade;

/**
 * @ClassName Towel
 * @Description 一个毛巾类
 * @author elsdnwn  
 */
class Towel {
	public String toString() {
		return "一条毛巾";
	}
}

/**
 * @ClassName TowelFactory
 * @Description 生成毛巾的厂商
 * @author Liujy  
 */
class TowelFactory {
	/**
	 * 卖毛巾
	 */
	public Towel saleTowel() {
		return new Towel();
	}
}



package com.elsdnwn.Facade;

/**
 * @ClassName Vegetables
 * @Description 一个蔬菜类
 * @author elsdnwn  
 */
class Vegetables {
	public String toString() {
		return "一箱蔬菜";
	}
}

/**
 * @ClassName VegetablesFactory
 * @Description 种植蔬菜的厂商 
 * @author Liujy
 */
class VegetablesFactory {
	/**
	 * 卖蔬菜
	 */
	public Vegetables saleVegetables() {
		return new Vegetables();
	}
}



package com.elsdnwn.Facade;

/**
 * @ClassName Computer
 * @Description 一个电脑类
 * @author elsdnwn  
 */
class Computer {
	public String toString() {
		return "一台笔记本";
	}
}

/**
 * @ClassName ComputerFactory
 * @Description 生产电脑的厂商 
 * @author Liujy
 */
class ComputerFactory {
	/**
	 * 卖电脑
	 */
	public Computer saleComputer() {
		return new Computer();
	}
}



package com.elsdnwn.Facade;

/**
 * @ClassName Supermarket
 * @Description 超市(沃尔玛、家乐福、丹尼斯) ，专门销售厂商的物品，供顾客购买
 * @author Liujy  
 */
public class Supermarket {

	/**
	 * 超市销售毛巾
	 */
	public Towel saleTowel() {
		TowelFactory mTowelFactory = new TowelFactory();
		return mTowelFactory.saleTowel();
	}

	/**
	 * 超市销售蔬菜
	 */
	public Vegetables saleVegetables() {
		VegetablesFactory mVegetablesFactory = new VegetablesFactory();
		return mVegetablesFactory.saleVegetables();
	}

	/**
	 * 超市销售电脑
	 */
	public Computer saleComputer() {
		ComputerFactory mComputerFactory = new ComputerFactory();
		return mComputerFactory.saleComputer();
	}
}




package com.elsdnwn.Facade;

/**
 * @ClassName Test
 * @Description 顾客A来到超市购买所需物品，不需要跑到生产厂商那里，也不用管物品是怎么生产的
 * @author Liujy  
 */
public class CustomerA {

	public static void main(String[] args) {
		Supermarket mSupermarketA = new Supermarket();
		// 买毛巾
		System.out.println("顾客A买了：" + mSupermarketA.saleTowel());
		// 买蔬菜
		System.out.println("顾客A买了：" + mSupermarketA.saleVegetables());
		// 买电脑
		System.out.println("顾客A买了：" + mSupermarketA.saleComputer());

	}

}

输出结果：

顾客A买了：一条毛巾
顾客A买了：一箱蔬菜
顾客A买了：一台笔记本



package com.elsdnwn.Facade;

/**
 * @ClassName Test
 * @Description 顾客B来到超市购买所需物品，不需要跑到生产厂商那里，也不用管物品是怎么生产的
 * @author Liujy  
 */
public class CustomerB {

	public static void main(String[] args) {
		Supermarket customerB = new Supermarket();
		// 买毛巾
		System.out.println("顾客B买了：" + customerB.saleTowel());
		// 买蔬菜
		System.out.println("顾客B买了：" + customerB.saleVegetables());
		// 买电脑
		System.out.println("顾客B买了：" + customerB.saleComputer());

	}

}

输出结果：

顾客B买了：一条毛巾
顾客B买了：一箱蔬菜
顾客B买了：一台笔记本


``` 


## 4. 杂谈
### 优点与缺点
#### 优点  
* 使用方便，使用外观模式客户端完全不需要知道子系统的实现过程；
* 降低客户端与子系统的耦合；

#### 缺点 
* 减少了可变性和灵活性；
* 在不引入抽象外观类的情况下，增加新的子系统可能需要修改外观类或客户端的源代码，违背了“开闭原则”；



