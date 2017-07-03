# java-zjh-utils

炸金花工具类
算法有误，停止维护

[jar文件下载](https://github.com/wdh-1025/java-zjh-utils/raw/master/jar/zjh.0.0.1.jar)

传入或者自定义牌需要遵循此规则，需花色跟牌保持跟工具类一样，通过以下方法可获得；
``` java
// 获取整副牌的花色，从大到小
String[] HS = ZJHUtils.getInstance().HS;
// 获取整副牌的牌，从大到小
String[] pat = ZJHUtils.getInstance().pat;
```
* 获取牌型的绝对大小，注意"0"为最大；PS：最好放在子线程调用；
``` java
ZJHUtils.getInstance().getPatTypePosition("黑桃A", "红桃A", "梅花A");
```
* 获取随机牌
``` java
ZJHUtils.getInstance().getPat();
```
* 判断是不是豹子
``` java
ZJHUtils.getInstance().ISBZ("黑桃A", "红桃A", "方块A");
```
* 判断是不是同花顺
``` java
ZJHUtils.getInstance().ISTHS("黑桃A", "黑桃Q", "黑桃K");
```
* 判断是不是同花
``` java
ZJHUtils.getInstance().ISTH("黑桃A", "黑桃2", "黑桃8");
```
* 判断是不是顺子
``` java
ZJHUtils.getInstance().ISSZ("黑桃J", "黑桃9", "黑桃10");
```
* 判断是不是对子
``` java
ZJHUtils.getInstance().ISDZ("黑桃2", "黑桃3", "方块3");
```
* 获取单独一张牌的大小，绝对大小，从大到小,从0开始
``` java
ZJHUtils.getInstance().getPatPositon("方块A");
```
“梭哈”类似，自行下载源码修改...
