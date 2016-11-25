package com.zjh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Wdh
 */

public class ZJHUtils {

	private static ZJHUtils mZJHUtils;
	/**
	 * 花色 遵循从大到小
	 */
	public String[] HS = new String[] { "黑桃", "红桃", "梅花", "方块" };
	/**
	 * 加入游戏的牌 遵循从大到小
	 */
	public String[] pat = new String[] { "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" };
	/**
	 * 所有牌 52张除去老鬼
	 */
	private List<String> pats = new ArrayList<>();
	/**
	 * 存储52张牌的大小位置
	 */
	private Map<String, Integer> patPosition = new HashMap<>();
	/**
	 * 所有结果牌 3张为一个结果，并按从大到小顺序排列好的牌，全部为22100个结果
	 */
	private Map<String, Integer> resultsPosition = new HashMap<>();// 牌型结果位置
	/**
	 * 有多少种类型
	 */
	private Integer typeSize = 0;
	/**
	 * 豹子集合，从大到小
	 */
	public List<String> BZ = new ArrayList<>();
	/**
	 * 同花顺集合，从大到小
	 */
	public List<String> THS = new ArrayList<>();
	/**
	 * 同花集合，从大到小
	 */
	public List<String> TH = new ArrayList<>();
	/**
	 * 顺子集合，从大到小
	 */
	public List<String> SZ = new ArrayList<>();
	/**
	 * 对子集合，从大到小
	 */
	public List<String> DZ = new ArrayList<>();
	/**
	 * 散牌集合，从大到小
	 */
	public List<String> SP = new ArrayList<>();
	/**
	 * 生成随机数获取棋牌相关
	 */
	private int max = 0;
	private int min = 0;
	private Random random = new Random();

	public ZJHUtils() {
		// 初始化，生成52张带花色的牌型，按从大到小的顺序
		for (int i = 0; i < pat.length; i++) {
			for (int j = 0; j < HS.length; j++) {
				pats.add(HS[j] + pat[i]);
				patPosition.put(HS[j] + pat[i], i * 4 + j);
			}
		}
	}

	public static ZJHUtils getInstance() {
		if (mZJHUtils == null) {
			mZJHUtils = new ZJHUtils();
		}
		return mZJHUtils;
	}

	/**
	 * 获取22100种牌型 52*51*50/(3*2*1) 五张的话则是52*51*50*49*48/(5*4*3*2*1)
	 * 耗时操作，调用者自己加入子线程
	 */
	private Map<String, Integer> getResultsPats() {
		if (resultsPosition.size() > 0) {
			return resultsPosition;
		} else {
			for (int i = 0; i <= 49; i++) {
				for (int j = i + 1; j <= 50; j++) {
					for (int k = j + 1; k <= 51; k++) {
						String pata = pats.get(i);
						String patb = pats.get(j);
						String patc = pats.get(k);
						if (ISBZ(pata, patb, patc)) {
							BZ.add(pata + "-" + patb + "-" + patc);
						} else if (ISTHS(pata, patb, patc)) {
							THS.add(pata + "-" + patb + "-" + patc);
						} else if (ISTH(pata, patb, patc)) {
							TH.add(pata + "-" + patb + "-" + patc);
						} else if (ISSZ(pata, patb, patc)) {
							SZ.add(pata + "-" + patb + "-" + patc);
						} else if (ISDZ(pata, patb, patc)) {
							DZ.add(pata + "-" + patb + "-" + patc);
						} else {
							SP.add(pata + "-" + patb + "-" + patc);
						}
					}
				}
			}
			// 按照游戏规则
			for (int i = 0; i < BZ.size(); i++) {
				resultsPosition.put(BZ.get(i), typeSize);
				typeSize++;
			}
			for (int i = 0; i < THS.size(); i++) {
				resultsPosition.put(THS.get(i), typeSize);
				typeSize++;
			}
			for (int i = 0; i < TH.size(); i++) {
				resultsPosition.put(TH.get(i), typeSize);
				typeSize++;
			}
			for (int i = 0; i < SZ.size(); i++) {
				resultsPosition.put(SZ.get(i), typeSize);
				typeSize++;
			}
			for (int i = 0; i < DZ.size(); i++) {
				resultsPosition.put(DZ.get(i), typeSize);
				typeSize++;
			}
			for (int i = 0; i < SP.size(); i++) {
				resultsPosition.put(SP.get(i), typeSize);
				typeSize++;
			}
			return resultsPosition;
		}
	}

	/**
	 * 下面是判断牌型的方法
	 * --------------------------------------华丽的分割线--------------------------------------
	 * --------------------------------------华丽的分割线--------------------------------------
	 * --------------------------------------华丽的分割线--------------------------------------
	 */

	/**
	 * 是不是豹子
	 * 
	 * @return
	 */
	public boolean ISBZ(String... pats) {
		try {
			if (pats.length != 3) {
				return false;
			}
			String a = pats[0].substring(2, pats[0].length());
			String b = pats[1].substring(2, pats[1].length());
			String c = pats[2].substring(2, pats[2].length());
			return StringsUtils.INSTANCE.checkValueSame(a, b, c);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是不是同花顺
	 * 
	 * @param pats
	 * @return
	 */
	public boolean ISTHS(String... pats) {
		try {
			if (pats.length != 3) {
				return false;
			}
			// 先判断是不是顺子,再判断花色
			String[] HS = getHS(pats);
			Integer[] newPats = PatToInt(pats);
			// 先找到数组中的最小值
			int min = newPats[0];
			for (int i = 0; i < newPats.length; i++) {
				if (newPats[i] < min) {
					min = newPats[i];
				}
			}
			if (!StringsUtils.INSTANCE.checkValueExist(newPats, min + 1)) {
				return false;
			} else if (!StringsUtils.INSTANCE.checkValueExist(newPats, min + 2)) {
				return false;
			} else if (!StringsUtils.INSTANCE.checkValueSame(HS)) {// 是顺子,判断是不是同花
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是不是同花
	 * 
	 * @param pats
	 * @return
	 */
	public boolean ISTH(String... pats) {
		if (pats.length != 3) {
			return false;
		}
		try {
			String[] HS = getHS(pats);
			return StringsUtils.INSTANCE.checkValueSame(HS);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是不是顺子
	 * 
	 * @param pats
	 * @return
	 */
	public boolean ISSZ(String... pats) {
		if (pats.length != 3) {
			return false;
		}
		try {
			Integer[] newPats = PatToInt(pats);
			// 先找到数组中的最小值
			int min = newPats[0];
			for (int i = 0; i < newPats.length; i++) {
				if (newPats[i] < min) {
					min = newPats[i];
				}
			}
			if (!StringsUtils.INSTANCE.checkValueExist(newPats, min + 1)) {
				return false;
			} else if (!StringsUtils.INSTANCE.checkValueExist(newPats, min + 2)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 是不是对子
	 * 
	 * @param pats
	 * @return
	 */
	public boolean ISDZ(String... pats) {
		if (pats.length != 3) {
			return false;
		}
		try {
			Integer[] newPats = PatToInt(pats);
			return StringsUtils.INSTANCE.checkRepeat(newPats);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 随机获取一张牌
	 * 
	 * @return
	 */
	public String getPat() {
		if (pats.size() > 0) {
			max = pats.size();
			int position = random.nextInt(max) % (max - min + 1) + min;
			return pats.get(position);
		} else {
			return "没有牌了";
		}
	}

	/**
	 * 获取52张牌随意一张的绝对大小位置
	 * 
	 * @param pat
	 * @return
	 */
	public int getPatPositon(String pat) {
		try {
			return patPosition.get(pat);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 获取一组牌型的绝对大小
	 * 
	 * @param pats
	 * @return
	 */
	public int getPatTypePosition(String... pats) {
		try {
			if (resultsPosition.size() == 0) {
				getResultsPats();
			}
			if (pats != null && pats.length == 3) {
				String patStr = PatSort(pats);
				return resultsPosition.get(patStr);
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 下面是获取牌的花色和牌的数字，JQKA转换成对应的11,12,13,14
	 * --------------------------------------华丽的分割线--------------------------------------
	 * --------------------------------------华丽的分割线--------------------------------------
	 * --------------------------------------华丽的分割线--------------------------------------
	 */

	/**
	 * 获取牌的所有花色
	 */
	private String[] getHS(String... pats) {
		if (pats != null && pats.length > 0) {
			String[] HS = new String[pats.length];
			for (int i = 0; i < pats.length; i++) {
				HS[i] = pats[i].substring(0, 2);
			}
			return HS;
		} else {
			return null;
		}
	}

	/**
	 * 把牌型从大到小排序并直接返回拼接好的字符串
	 * 
	 * @param pats
	 * @return
	 */
	private String PatSort(String... pats) {
		// String[] newPats = new String[pats.length];
		String patStr = "";
		Integer[] patsPositon = new Integer[pats.length];
		Map<Integer, String> patsMap = new HashMap<>();
		for (int i = 0; i < pats.length; i++) {
			patsPositon[i] = getPatPositon(pats[i]);
			patsMap.put(patsPositon[i], pats[i]);
		}
		Arrays.sort(patsPositon);// 对其从小到大排序,因为下面，遍历要返回从大到小，所以这里相反，按从小到大
		for (int i = 0; i < patsPositon.length; i++) {
			// newPats[i] = patsMap.get(patsPositon[i]);
			patStr += patsMap.get(patsPositon[i]) + "-";
		}
		patStr = patStr.substring(0, patStr.length() - 1);
		return patStr;
	}

	/**
	 * 把牌去掉花色并把J,Q,K,A转成可直接比较的INT类型
	 * 
	 * @return
	 */
	private Integer[] PatToInt(String... pats) {
		if (pats != null && pats.length > 0) {
			Integer[] newPats = new Integer[pats.length];
			for (int i = 0; i < pats.length; i++) {
				String pat = pats[i];
				switch (pat.substring(2, pat.length())) {
				case "J":
					newPats[i] = 11;
					break;
				case "Q":
					newPats[i] = 12;
					break;
				case "K":
					newPats[i] = 13;
					break;
				case "A":
					newPats[i] = 14;
					break;
				default:
					newPats[i] = Integer.valueOf(pat.substring(2, pats[i].length()));
					break;
				}
			}
			return newPats;
		} else {
			return null;
		}
	}
}
