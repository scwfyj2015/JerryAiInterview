package org.Interview.Invitation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class IntensityNodeNumUtils {


    /**
     * 参考四舍五入的方式，找到这个整数value左右两侧可整除step的最近的值，可以是value本身。
     * eg:
     * 1. value=7,step=4 return:8
     * 2. value=6,step=4 return:4
     * 3. value=4,step=4 return:4
     *
     * @param value 一个整数，正负都有可能
     * @param step  除数
     * @return 可被step整除的距离value最近的整数(可以是value本身)
     */
    public static int findNearestTimesNumByStep(Integer value, Integer step) {
        Integer symbol = value < 0 ? -1 : 1;
        Integer postiveValue = Math.abs(value);
        //商
        int quotient = postiveValue / step;

        Integer halfStep = Math.round((float) step / 2);

        //余数
        int remainder = postiveValue % step;

        //余数超过一半，说明该范围集开始位置距离左侧最近的区间分段点超过了一半，即最左端的分区覆盖没有超过一半，不算有效区间。
        //eg：如果范围集为[7, 18]的开始位置是7，分区步长是10，左侧最近的分段是[0,10), 7开始的位置在该分区的中间点后段，
        // [7,10)没有覆盖分区段一半的范围，则最左侧的有效分区标识为从10开始。[10,18]是右侧的范围集，覆盖了分区段[10,20)的一半，算有效分区
        if (Math.abs(remainder) >= halfStep) {
            return (quotient + 1) * step * symbol;
        } else {
            return quotient * step * symbol;
        }
    }


    /**
     * 找到在左闭右开的范围区间[from,to)内可以被step整除的所有数值集合
     *
     * @param from 开始整数(包含在内)
     * @param to   结束整数(不包含在内)
     * @param step 计算的步长
     * @return 可以被step整除的所有整数集合
     */
    public static List<Integer> listAllTimesNumInRange(int from, int to, int step) {
        IntensityCheckUtils.validCheck(from, to, step);
        List<Integer> value = new ArrayList<>();
        for (int start = from + step; start < to; start = start + step) {
            value.add(start);
        }

        return value;
    }


}
