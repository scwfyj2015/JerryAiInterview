package org.Interview.Invitation.utils;

import com.google.common.base.Preconditions;

/**
 * @author yujie.fan
 * @description 数据区间正确性校验用的工具类
 * @date 2024-06-27
 */
public class IntensityCheckUtils {


    /**
     * 区间的有效性校验, to greate than from ,且最少需要超过一半的步长，才算有效的区间。
     *
     * @param from 区间开始位置
     * @param to   区间结束位置，需要大于from
     * @param step  步长
     * @return true 参数校验通过
     */
    public static boolean validCheck(Integer from, Integer to, Integer step) {

        Integer halfStep = Math.round((float) step / 2);
        //校验区间的有效性
        Preconditions.checkArgument(to > from, JerriErrorCode.INVALID_NUM_RANGE.errorCode,
                JerriErrorCode.INVALID_NUM_RANGE.errorDesc);

        //校验步长的有效性,最少需要超过一半步长才算有效
        Preconditions.checkArgument(to - from >= halfStep, JerriErrorCode.INVALID_DISTANCE.errorCode,
                JerriErrorCode.INVALID_DISTANCE.errorDesc);

        return true;
    }
}
