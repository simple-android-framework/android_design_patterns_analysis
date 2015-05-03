公共技术点之面向对象六大原则
---------

## 概述
在工作初期，我们可能会经常会有这样的感觉，自己的代码接口设计混乱、代码耦合较为严重、一个类的代码过多等等，自己回头看的时候都觉得汗颜。再看那些知名的开源库，它们大多有着整洁的代码、清晰简单的接口、职责单一的类，这个时候我们通常会捶胸顿足而感叹：什么时候老夫才能写出这样的代码！     

在做开发的这些年中，我渐渐的感觉到，其实国内的一些初、中级工程师写的东西不规范或者说不够清晰的原因是缺乏一些指导原则。他们手中挥舞着面向对象的大旗，写出来的东西却充斥着面向过程的气味。也许是他们不知道有这些原则，也许是他们知道但是不能很好运用到实际代码中，亦或是他们没有在实战项目中体会到这些原则能够带来的优点，以至于他们对这些原则并没有足够的重视。     

今天，我们就是以剖析优秀的Android网络框架Volley为例来学习这六大面向对象的基本原则，体会它们带来的强大能量。         

在此之前，有一点需要大家知道，熟悉这些原则不会让你写出优秀的代码，只是为你的优秀代码之路铺上了一层栅栏，在这些原则的指导下你才能避免陷入一些常见的代码泥沼，从而让你专心写出优秀的东西。另外，我是个新人，以下只是我个人的观点。如果你觉得还行，可以顶个帖支持一下；如果你觉得它烂透了，那请分享你的经验。         


## 一、单一职责原则 （ Single Responsibility   Principle ）

### 1.1 简述
单一职责原则的英文名称是Single Responsibility Principle，简称是SRP，简单来说一个类只做一件事。这个设计原则备受争议却又及其重要的原则。只要你想和别人争执、怄气或者是吵架，这个原则是屡试不爽的。因为单一职责的划分界限并不是如马路上的行车道那么清晰，很多时候都是需要靠个人经验来界定。当然最大的问题就是对职责的定义，什么是类的职责，以及怎么划分类的职责。      
     
试想一下，如果你遵守了这个原则，那么你的类就会划分得很细，每个类都有自己的职责。恩，这不就是高内聚、低耦合么！ 当然，如何界定类的职责这需要你的个人经验了。      


### 1.2 示例

在Volley中，我觉得很能够体现SRP原则的就是HttpStack这个类族了。HttpStack定义了一个执行网络请求的接口，代码如下 : 

```java
/**
 * An HTTP stack abstraction.
 */
public interface HttpStack {
    /**
     * 执行Http请求,并且返回一个HttpResponse
     */ 
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
        throws IOException, AuthFailureError;

}
```    
可以看到，HttpStack只有一个函数，清晰明了，它的职责就是执行网络请求并且返回一个Response。它的职责很单一，这样在需要修改执行网络请求的相关代码时我们只需要修改实现HttpStack接口的类，而不会影响其它的类的代码。如果某个类的职责包含有执行网络请求、解析网络请求、进行gzip压缩、封装请求参数等等，那么在你修改某处代码时你就必须谨慎，以免修改的代码影响了其它的功能。但是当职责单一的时候，你修改的代码能够基本上不影响其它的功能。这就在一定程度上保证了代码的可维护性。**注意，单一职责原则并不是说一个类只有一个函数，而是说这个类中的函数所做的工作必须要是高度相关的，也就是高内聚**。HttpStack抽象了执行网络请求的具体过程，接口简单清晰，也便于扩展。     
     
我们知道，Api 9以下使用HttpClient执行网络请求会好一些，api 9及其以上则建议使用HttpURLConnection。这就需要执行网络请求的具体实现能够可扩展、可替换，因此我们对于执行网络请求这个功能必须要抽象出来，HttpStack就是这个职责的抽象。


### 1.3 优点
*    类的复杂性降低，实现什么职责都有清晰明确的定义； 
*    可读性提高，复杂性降低，那当然可读性提高了； 
*    可维护性提高，可读性提高，那当然更容易维护了； 
*    变更引起的风险降低，变更是必不可少的，如果接口的单一职责做得好，一个接口修改只对相应的实现类有影响，对其他的接口无影响，这对系统的扩展性、维护性都有非常大的帮助。




## 里氏替换原则 （ Liskov Substitution Principle）

### 2.1 简述
面向对象的语言的三大特点是继承、封装、多态，里氏替换原则就是依赖于继承、多态这两大特性。里氏替换原则简单来说就是所有引用基类的地方必须能透明地使用其子类的对象。通俗点讲，只要父类能出现的地方子类就可以出现，而且替换为子类也不会产生任何错误或异常，使用者可能根本就不需要知道是父类还是子类。但是，反过来就不行了，有子类出现的地方，父类未必就能适应。     


### 2.2 示例
还是以HttpStack为例，Volley定义了HttpStack来表示执行网络请求这个抽象概念。在执行网络请求时，我们只需要定义一个HttpStack对象，然后调用performRequest即可。至于HttpStack的具体实现由更高层的调用者给出。示例如下 :       

```java

    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
  		// 代码省略
		// 1、构造HttpStack对象
        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }
		// 2、将HttpStack对象传递给Network对象
        Network network = new BasicNetwork(stack);
		// 3、将network对象传递给网络请求队列
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }
```   

BasicNetwork的代码如下:      

```java
/**
 * A network performing Volley requests over an {@link HttpStack}.
 */
public class BasicNetwork implements Network {
	// HttpStack抽象对象
    protected final HttpStack mHttpStack;

    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack httpStack) {

        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }
    
    
    public BasicNetwork(HttpStack httpStack, ByteArrayPool pool) {
        mHttpStack = httpStack;
        mPool = pool;
    }
}
```       

上述代码中，BasicNetwork构造函数依赖的是HttpStack抽象接口，任何实现了HttpStack接口的类型都可以作为参数传递给BasicNetwork用以执行网络请求。这就是所谓的里氏替换原则，任何父类出现的地方子类都可以出现，这不就保证了可扩展性吗？！ 此时，用手撑着你的小脑门作思考状...  任何实现HttpStack接口的类的对象都可以传递给BasicNetwork实现网络请求的功能，这样Volley执行网络请求的方法就有很多种可能性，而不是只有HttpClient和HttpURLConnection。       
  
喔，原来是这样！此时我们放下装逼的模样，呈现一副若有所得的样子。细想一下，框架可不就是这样吗？ 框架不就是定义一系列相关的逻辑骨架与抽象，使得用户可以将自己的实现传递进给框架，从而实现变化万千的功能。    

### 2.3 优点
*    代码共享，减少创建类的工作量，每个子类都拥有父类的方法和属性；
*    提高代码的重用性； 
*    提高代码的可扩展性，实现父类的方法就可以“为所欲为”了，很多开源框架的扩展接口都是通过继承父类来完成的； 
*    提高产品或项目的开放性。

### 2.4 缺点
*    继承是侵入性的。只要继承，就必须拥有父类的所有属性和方法；
*    降低代码的灵活性。子类必须拥有父类的属性和方法，让子类自由的世界中多了些约束；
*    增强了耦合性。当父类的常量、变量和方法被修改时，必需要考虑子类的修改，而且在缺乏规范的环境下，这种修改可能带来非常糟糕的结果——大片的代码需要重构。


##  依赖倒置原则 （Dependence Inversion Principle）

### 3.1 简述
依赖倒置原则这个名字看着有点不好理解，“依赖”还要“倒置”，这到底是什么意思？       
依赖倒置原则的几个关键点如下:      

*    高层模块不应该依赖低层模块，两者都应该依赖其抽象；
*    抽象不应该依赖细节； 
*    细节应该依赖抽象。       

在Java语言中，抽象就是指接口或抽象类，两者都是不能直接被实例化的；细节就是实现类，实现接口或继承抽象类而产生的类就是细节，其特点就是可以直接被实例化，也就是可以加上一个关键字 new 产生一个对象。依赖倒置原则在 Java 语言中的表现就是：**模块间的依赖通过抽象发生，实现类之间不发生直接的依赖关系，其依赖关系是通过接口或抽象类产生的。**软件先驱们总是喜欢将一些理论定义得很抽象，弄得神不楞登的，其实就是一句话:面向接口编程，或者说是面向抽象编程，这里的抽象指的是接口或者抽象类。面向接口编程是面向对象精髓之一。


### 3.2 示例
采用依赖倒置原则可以减少类间的耦合性，提高系统的稳定性，降低并行开发引起的风险，提高代码的可读性和可维护性。      
第二章节中的BasicNetwork实现类依赖于HttpStack接口( 抽象 )，而不依赖于HttpClientStack与HurlStack实现类 ( 细节 )，这就是典型的依赖倒置原则的体现。假如BasicNetwork直接依赖了HttpClientStack，那么HurlStack就不能传递给了，除非HurlStack继承自HttpClientStack。但这么设计明显不符合逻辑，HurlStack与HttpClientStack并没有任何的is-a的关系，而且即使有也不能这么设计，因为HttpClientStack是一个具体类而不是抽象，如果HttpClientStack作为BasicNetwork构造函数的参数，那么以为这后续的扩展都需要继承自HttpClientStack。这简直是一件不可忍受的事了！        


### 3.3 优点
*   可扩展性好；
*   耦合度低；



## 开闭原则 （ Open-Close Principle ）

### 4.1 简述
开闭原则是 Java 世界里最基础的设计原则，它指导我们如何建立一个稳定的、灵活的系统。开闭原则的定义是 : 一个软件实体如类、模块和函数应该对扩展开放，对修改关闭。在软件的生命周期内，因为变化、升级和维护等原因需要对软件原有代码进行修改时，可能会给旧代码中引入错误。因此当软件需要变化时，我们应该尽量通过扩展的方式来实现变化，而不是通过修改已有的代码来实现。    


### 4.2 示例

在软件开发过程中，永远不变的就是变化。开闭原则是使我们的软件系统拥抱变化的核心原则之一。对扩展可放，对修改关闭给出了高层次的概括，即在需要对软件进行升级、变化时应该通过扩展的形式来实现，而非修改原有代码。当然这只是一种比较理想的状态，是通过扩展还是通过修改旧代码需要根据代码自身来定。      

在Volley中，开闭原则体现得比较好的是Request类族的设计。我们知道，在开发C/S应用时，服务器返回的数据格式多种多样，有字符串类型、xml、json等。而解析服务器返回的Response的原始数据类型则是通过Request类来实现的，这样就使得Request类对于服务器返回的数据格式有良好的扩展性，即Request的可变性太大。

例如我们返回的数据格式是Json，那么我们使用JsonObjectRequest请求来获取数据，它会将结果转成JsonObject对象，我们看看JsonObjectRequest的核心实现。     

```java
/**
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 */
public class JsonObjectRequest extends JsonRequest<JSONObject> {

   // 代码省略
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
```       
JsonObjectRequest通过实现Request抽象类的parseNetworkResponse解析服务器返回的结果，这里将结果转换为JSONObject，并且封装到Response类中。     

例如Volley添加对图片请求的支持，即ImageLoader( 已内置 )。这个时候我的请求返回的数据是Bitmap图片。因此我需要在该类型的Request得到的结果是Request，但支持一种数据格式不能通过修改源码的形式，这样可能会为旧代码引入错误。但是你又需要支持新的数据格式，此时我们的开闭原则就很重要了，对扩展开放，对修改关闭。我们看看Volley是如何做的。        

```java 

/**
 * A canned request for getting an image at a given URL and calling
 * back with a decoded Bitmap.
 */
public class ImageRequest extends Request<Bitmap> {
	// 代码省略

	// 将结果解析成Bitmap，并且封装套Response对象中
    @Override
    protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
        // Serialize all decode on a global lock to reduce concurrent heap usage.
        synchronized (sDecodeLock) {
            try {
                return doParse(response);
            } catch (OutOfMemoryError e) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", response.data.length, getUrl());
                return Response.error(new ParseError(e));
            }
        }
    }

    /**
     * The real guts of parseNetworkResponse. Broken out for readability.
     */
    private Response<Bitmap> doParse(NetworkResponse response) {
        byte[] data = response.data;
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap = null;
        if (mMaxWidth == 0 && mMaxHeight == 0) {
            decodeOptions.inPreferredConfig = mDecodeConfig;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            // If we have to resize this image, first get the natural bounds.
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;

            // Then compute the dimensions we would ideally like to decode to.
            int desiredWidth = getResizedDimension(mMaxWidth, mMaxHeight,
                    actualWidth, actualHeight);
            int desiredHeight = getResizedDimension(mMaxHeight, mMaxWidth,
                    actualHeight, actualWidth);

            // Decode to the nearest power of two scaling factor.
            decodeOptions.inJustDecodeBounds = false;
            // TODO(ficus): Do we need this or is it okay since API 8 doesn't support it?
            // decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
            decodeOptions.inSampleSize =
                findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap =
                BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);

            // If necessary, scale down to the maximal acceptable size.
            if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                    tempBitmap.getHeight() > desiredHeight)) {
                bitmap = Bitmap.createScaledBitmap(tempBitmap,
                        desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }

        if (bitmap == null) {
            return Response.error(new ParseError(response));
        } else {
            return Response.success(bitmap, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
```    
需要添加某种数据格式的Request时，只需要继承自Request类，并且实现相应的方法即可。这样通过扩展的形式来应对软件的变化或者说用户需求的多样性，即避免了破坏原有系统，又保证了软件系统的可扩展性。     

### 4.3 优点
*   增加稳定性；
*   可扩展性高。


## 接口隔离原则 （Interface Segregation Principle）

### 5.1 简述
客户端不应该依赖它不需要的接口；一个类对另一个类的依赖应该建立在最小的接口上。根据接口隔离原则，当一个接口太大时，我们需要将它分割成一些更细小的接口，使用该接口的客户端仅需知道与之相关的方法即可。    

可能描述起来不是很好理解，我们还是以示例来加强理解吧。       


### 5.2 示例
我们知道，在Volley的网络队列中是会对请求进行排序的。Volley内部使用PriorityBlockingQueue来维护网络请求队列，PriorityBlockingQueue需要调用Request类的compareTo函数来进行排序。试想一下，PriorityBlockingQueue其实只需要调用Request类的排序方法就可以了，其他的接口它根本不需要，即PriorityBlockingQueue只需要compareTo这个接口，而这个compareTo方法就是我们上述所说的最小接口。当然compareTo这个方法并不是Volley本身定义的接口方法，而是Java中的Comparable<T>接口，但我们这里只是为了学习本身，至于哪里定义的无关紧要。

```java
public abstract class Request<T> implements Comparable<Request<T>> {

    /**
     * 排序方法,PriorityBlockingQueue只需要调用元素的compareTo即可进行排序
     */
    @Override
    public int compareTo(Request<T> other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();

        // High-priority requests are "lesser" so they are sorted to the front.
        // Equal priorities are sorted by sequence number to provide FIFO ordering.
        return left == right ?
                this.mSequence - other.mSequence :
                right.ordinal() - left.ordinal();
    }
}
```    

PriorityBlockingQueue类相关代码 :     

```java 

public class PriorityBlockingQueue<E> extends AbstractQueue<E>
    implements BlockingQueue<E>, java.io.Serializable {
    
    // 代码省略
    
    	// 添加元素的时候进行排序
        public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n, cap;
        Object[] array;
        while ((n = size) >= (cap = (array = queue).length))
            tryGrow(array, cap);
        try {
            Comparator<? super E> cmp = comparator;
            // 没有设置Comparator则使用元素本身的compareTo方法进行排序
            if (cmp == null)
                siftUpComparable(n, e, array);
            else
                siftUpUsingComparator(n, e, array, cmp);
            size = n + 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return true;
    }
    
    private static <T> void siftUpComparable(int k, T x, Object[] array) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = array[parent];
            // 调用元素的compareTo方法进行排序
            if (key.compareTo((T) e) >= 0)
                break;
            array[k] = e;
            k = parent;
        }
        array[k] = key;
    }
 }
```     
从PriorityBlockingQueue的代码可知,在元素排序时，PriorityBlockingQueue只需要知道元素是个Comparable对象即可，不需要知道这个对象是不是Request类以及这个类的其他接口。它只需要排序，因此我只要知道它是实现了Comparable接口的对象即可，Comparable就是它的最小接口，也是通过Comparable隔离了PriorityBlockingQueue类对Request类的其他方法的可见性。妙哉，妙哉！      

### 5.3 优点
*   降低耦合性；
*   提升代码的可读性；
*   隐藏实现细节。


## 迪米特原则 （ Law of Demeter ）
### 6.1 简述
迪米特法则也称为最少知识原则（Least Knowledge Principle），虽然名字不同，但描述的是同一个原则：一个对象应该对其他对象有最少的了解。通俗地讲，一个类应该对自己需要耦合或调用的类知道得最少，这有点类似接口隔离原则中的最小接口的概念。类的内部如何实现、如何复杂都与调用者或者依赖者没关系，调用者或者依赖者只需要知道他需要的方法即可，其他的我一概不关心。类与类之间的关系越密切，耦合度越大，当一个类发生改变时，对另一个类的影响也越大。            

迪米特法则还有一个英文解释是: Only talk to your immedate friends（ 只与直接的朋友通信。）什么叫做直接的朋友呢？每个对象都必然会与其他对象有耦合关系，两个对象之间的耦合就成为朋友关系，这种关系的类型有很多，例如组合、聚合、依赖等。


### 6.2 示例
例如，Volley中的Response缓存接口的设计。

```java
/**
 * An interface for a cache keyed by a String with a byte array as data.
 */
public interface Cache {
    /**
     * 获取缓存
     */
    public Entry get(String key);

    /**
     * 添加一个缓存元素
     */
    public void put(String key, Entry entry);

    /**
     * 初始化缓存
     */
    public void initialize();

    /**
     * 标识某个缓存过期
     */
    public void invalidate(String key, boolean fullExpire);

    /**
     * 移除缓存
     */
    public void remove(String key);

    /**
     * 清空缓存
     */
    public void clear();

}
```    
Cache接口定义了缓存类需要实现的最小接口，依赖缓存类的对象只需要知道这些接口即可。例如缓存的具体实现类DiskBasedCache，该缓存类将Response序列化到本地,这就需要操作File以及相关的类。代码如下 :       

```java

public class DiskBasedCache implements Cache {

    /** Map of the Key, CacheHeader pairs */
    private final Map<String, CacheHeader> mEntries =
            new LinkedHashMap<String, CacheHeader>(16, .75f, true);

    /** The root directory to use for the cache. */
    private final File mRootDirectory;

    public DiskBasedCache(File rootDirectory, int maxCacheSizeInBytes) {
        mRootDirectory = rootDirectory;
        mMaxCacheSizeInBytes = maxCacheSizeInBytes;
    }

    public DiskBasedCache(File rootDirectory) {
        this(rootDirectory, DEFAULT_DISK_USAGE_BYTES);
    }

	// 代码省略
}
```     
在这里，Volley的直接朋友就是DiskBasedCache，间接朋友就是mRootDirectory、mEntries等。Volley只需要直接和Cache类交互即可，并不需要知道File、mEntries等对象的存在。这就是迪米特原则，尽量少的知道对象的信息，只与直接的朋友交互。       
     

### 6.3 优点
*   降低复杂度；
*   降低耦合度；
*   增加稳定性。


## 杂谈 
面向对象六大原则在开发过程中极为重要，如果能够很好地将这些原则运用到项目中，再在一些合适的场景运用一些前人验证过的模式，那么开发出来的软件在一定程度上能够得到质量保证。其实稍微一想，这几大原则最终就化为这么几个关键词: 抽象、单一职责、最小化。那么在实际开发过程中如何权衡、实践这些原则，笔者也在不断地学习、摸索。我想学习任何的事物莫过于实践、经验与领悟，在这个过程中希望能够与大家分享知识、共同进步。
