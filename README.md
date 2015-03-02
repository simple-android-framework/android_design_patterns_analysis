# Android源码设计模式分析开源项目

## 简述
该项目通过分析Android系统中的设计模式来提升大家对设计模式的理解，从源码的角度来剖析既增加了对Android系统本身的了解，也从优秀的设计中领悟模式的实际运用以及它适用的场景，避免在实际开发中的生搬硬套。如果你对面向对象的六大开发原则还不太熟悉，那么在学习模式之前先学习一下[面向对象的六大原则](oop-principles/oop-principles.md)是非常有必要的。       

每一个模式可能在Android源码中有很多个实现，因此我们为每个模式创建一个文件夹，文件夹中为分析这个模式的markdown文件，该文件的命名规则为: 模式名-分析者.md, 例如: adapter-mr.simple.md。我们的原则是通过分析这些源码不仅要学会设计模式本身，而且要通过学习该模式深入到Android源码层的实现，这样不仅学了设计模式，也增加了我们对于Android源码的了解。

**<font color="red">QQ交流群: 413864859,希望大家踊跃参与进来。</font>**

## 编写步骤
1. 填写[任务表](#schedule);
2. 在模式对应的文件夹下以你的用户名建立一个文件夹，例如我分析的是适配器模式，那么我在adapter目录下建立一个mr.simple文件夹；
3. 将template.md拷贝一份到adapter/mr.simple目录下，并且修改为readme.md；
4. 所需图片统一放到你的用户名文件夹的images目录下,例如adapter/mr.simple/images；
5. 按照[template.md](template.md)的格式将模式分析的markdown文件编写完毕；
6. 提交本地修改，将本地的提交push线上。
 
样例大家可以参考[Mr.Simple的单例模式分析](singleton/mr.simple)。    


<b id="schedule"></b>
## 任务表
| 	模式名 		 | 		分类  	 | 		分析者    |
| ------------- |:-------------:| ------------- |
|    单例模式    |  [singleton](singleton/mr.simple)	 | [Mr.Simple](https://github.com/bboyfeiyu)
|    Builder模式    |  [builder](builder/mr.simple)	 | [Mr.Simple](https://github.com/bboyfeiyu)
|    外观模式    |  [facade](facade/elsdnwn)	 | [elsdnwn](https://github.com/elsdnwn)
|    模板方法    | [template-method](template-method/mr.simple)  | [Mr.Simple](https://github.com/bboyfeiyu) | 
|    适配器模式  | [adapter](adapter/mr.simple)  | [Mr.Simple](https://github.com/bboyfeiyu) | 


## 模式与文件夹对应列表
| 模式名        | 文件夹           |
| ------------- |:-------------:| 
|    适配器模式    |  [adapter](adapter)			|  
|    抽象工厂模式  |   [abstract-factory](abstract-factory) |    
| 	 桥接模式	     |    [bridge](bridge)	 		|    
|    Builder模式 |   		[builder](builder)	|  
|    责任链模式   |   [chain-of-responsibility](chain-of-responsibility) |    
| 	 命令模式	    |     [command](command)		 | 
|    组合模式    |  	[composite](composite)		|  
|    装饰模式  	|   [decorator](decorator)	 	|    
| 	 外观模式	     |      [facade](facade)		| 
|    工厂方法模式  |  [factory-method](factory-method) |  
|    享元模式  	 |    [flyweight](flyweight)	|    
| 	 解释器模式	 |  [interpreter](interpreter) | 
|    迭代器模式    |  [iterator](iterator)		|  
|    中介者模式   |    [mediator](mediator)		|    
| 	 备忘录模式	 |   [memento](memento)	  		| 
|    观察者模式   |  [observer](observer)		|  
|    原型模式  	|   [prototype](prototype)	 	|    
| 	 代理模式	    |     [proxy](proxy)			| 
|    单例模式    |  [singleton](singleton)		|  
|    状态模式  	|    [state](state)				|    
| 	 策略模式	     |     [strategy](strategy)	 	| 
|    模板方法模式  |   [template-method](template-method) |    
| 	 访问者模式	 |     [visitor](visitor)	 	|     

## 参考资料 
* [GOF的设计模式：可复用面向对象软件的基础](http://pan.baidu.com/s/1i3zjaIx)
* [设计模式之禅](http://pan.baidu.com/s/1sjjZCvj)
* [Java与模式](http://pan.baidu.com/s/1i3sxzyH)
* [java-design-patterns](https://github.com/iluwatar/java-design-patterns)
* [Java之美[从菜鸟到高手演变]之设计模式](http://blog.csdn.net/zhangerqing/article/details/8194653)

