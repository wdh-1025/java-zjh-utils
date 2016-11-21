package com.zjh;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		// 获取所有牌型组合结果，并按从大到小排序,第一次调用的时候稍微慢一点，100毫秒左右，第二次5毫秒左右
		// 不过一般只需要调用一次
		ZJHUtils.getInstance().getResultsPats(new ResultsCallBack() {

			@Override
			public void results(String resultsPats) {
			}
		});
		// 获取随机牌
		System.out.println(ZJHUtils.getInstance().getPat());
		// 判断是不是豹子
		System.out.println(ZJHUtils.getInstance().ISBZ("黑桃A", "红桃A", "方块A"));
		// 判断是不是同花顺
		System.out.println(ZJHUtils.getInstance().ISTHS("黑桃A", "黑桃Q", "黑桃K"));
		// 判断是不是同花
		System.out.println(ZJHUtils.getInstance().ISTH("黑桃A", "黑桃2", "黑桃8"));
		// 判断是不是顺子
		System.out.println(ZJHUtils.getInstance().ISSZ("黑桃J", "黑桃9", "黑桃10"));
		// 判断是不是对子
		System.out.println(ZJHUtils.getInstance().ISDZ("黑桃2", "黑桃3", "方块3"));
		// 获取单独一张牌的大小，绝对大小，从大到小,从0开始
		System.out.println(ZJHUtils.getInstance().getPatPositon("方块A"));
		// 传入或者自定义牌需要遵循此规则，需保持花色跟牌一样，通过以下方法可获得
		// 获取正副牌的花色，从大到小
		String[] HS = ZJHUtils.getInstance().HS;
		// 获取正副牌的牌，从大到小
		String[] pat = ZJHUtils.getInstance().pat;
	}

}
