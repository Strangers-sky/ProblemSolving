

{一、 web.xml中的配置项：

1.load—on—startup元素可以用来标记容器是否应该在web应用程序启动的时候就加载这个servlet，（实例化并调用其初始化方法init()方法）

2.它的值必须是一个整数，表示servlet被加载的先后顺序

3.如果该元素的值为负数，或没有设置，则容器会当servlet被请求时再加载时

4.如果值为正数或为0时，表示容器在应用启动时就加载并初始化这个servlet，值越小，servlet的优先级越高，就越先被加载；值相同时，容器就会自己选择顺序来加载

# GET方法

1.get方法向页面请求发送已编码的用户信息，页面和已编码的信息之间用？分隔；
			//Servlet 使用 doGet() 方法处理这种类型的请求

# POST方法

2.POST方法打包信息的方式基本与get方法相同，但是post方法不是把信息作为URL中？后面的文本字符进行发送，而是把这些信息作为一个单独的消息，消息已标准输出的形式传到后台程序，可以解析和使用这些标准输出
			//Servlet 使用 doPost() 方法处理这种类型的请求。
}


{二、识别返回用户包括三个步骤：

1.服务器脚本向浏览器发送一组Cookie。例如：姓名、年龄或识别号码等。
    
2.浏览器将这些信息存储在本地计算机上，以备将来使用。

3.当下一次浏览器向Web服务器发送任何请求时，浏览器会把这些Cookie信息发送到服务器，服务器将使用这些信息来识别用户。

}


{三、MySQL中按月，按年，按季度统计数据：
	
	mysql有个字段是DATETIME类型
	
	按月：select DATE_FORMAT(f1,'%m')  from tt group by DATE_FORMAT(f1,'%m')
	
	比如数据库的为2008-01-15 12：10：00 
	则DATE_FORMAT的参数格式分别得到的结果为： 
	'%Y'  2008 
	'%Y-%m'  2008-01 
	'%Y-%c'  2008-1 
	'%m'  01 
	'%c'  1
	
	DateFormat可以使用的格式：https://blog.csdn.net/wgllz/article/details/5334764
	
}