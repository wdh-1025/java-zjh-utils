package com.zjh;

import java.util.HashSet;
import java.util.Set;

/**
 * 避免多线程同步问题，而且还能防止反序列化重新创建新的对象 1.5才加入了enum特性
 * 所以很少看见有人这么写的，那我就这么写
 * @author Wdh
 *
 */
public enum StringsUtils {
	INSTANCE;
	
	/**
	 * 检查数组中是否包含某个值
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	public boolean checkValueExist(String[] arr, String value) {
		for (String s : arr) {
			if (s.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查数组中是否包含某个值
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	public boolean checkValueExist(Integer[] arr, int value) {
		for (int s : arr) {
			if (s == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查值是否相同
	 * @param strings
	 * @return
	 */
	public boolean checkValueSame(String... strings) {
		for (int i = 0; i < strings.length; i++) {
			if (i + 1 < strings.length) {
				if (!strings[i].equals(strings[i + 1])) {
					return false;
				}
			}

		}
		return true;
	}
	
	/**
	 * 检查是否存在相同的值
	 * @param strings
	 * @return
	 */
	public static boolean checkRepeat(Integer... pats) {
		Set<Integer> set = new HashSet<Integer>();
		for (int str : pats) {
			set.add(str);
		}
		if (set.size() != pats.length) {
			return true;// 有重复
		} else {
			return false;// 不重复
		}
	}
}
