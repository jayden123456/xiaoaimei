package com.yidan.xiaoaimei.utils;

/**
 * 金币转RMB
 * Created by jaydenma on 2017/7/24.
 */

public class MoneyTransformUtil {

    public static String goldToMoney(String gold) {
        String money;
        if (gold.length() == 1) {
            if (gold.equals("0")) {
                money = "0";
            } else {
                money = "0.0" + gold;
            }
        } else if (gold.length() == 2) {
            money = "0." + gold;
        } else {
            money = gold.substring(0, gold.length() - 2) + "." + gold.substring(gold.length() - 2, gold.length());
        }
        return money;
    }

}
