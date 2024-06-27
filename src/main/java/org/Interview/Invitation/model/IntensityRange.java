package org.Interview.Invitation.model;

import lombok.Data;

import java.util.List;

import static org.Interview.Invitation.utils.IntensityNodeNumUtils.findNearestTimesNumByStep;
import static org.Interview.Invitation.utils.IntensityNodeNumUtils.listAllTimesNumInRange;

/**
 * @author yujie.fan
 * @description 打标的范围区间集对象
 * @date 2024-06-27
 */
@Data
public class IntensityRange {
    /**
     * 范围集开始位置
     */
    Integer from;
    /**
     * 范围集结束位置
     */
    Integer to;
    /**
     * 覆盖有效分区的开始位置
     */
    Integer startSegPosInclude;
    /**
     * 覆盖有效分区的结束位置
     */
    Integer endSegPosExclude;

    /**
     * 切分步长
     */
    Integer step;
    /**
     * 根据指定步长切分后的分区标识集合
     * 计算 [起点,终点) 区间内，所有的分组point，起点-包含，终点-不包含
     */
    List<Integer> segmentPosList;

    public IntensityRange(Integer from, Integer to, Integer step) {
        this.from = from;
        this.to = to;
        this.step = step;
    }

    /**
     * 计算from,to压盖的两端的有效分区标识
     */
    public void calculateTerminalSegPos() {
        //计算起点
        int startNum = findNearestTimesNumByStep(from, step);

        this.startSegPosInclude = startNum;

        //计算终点
        int endNum = findNearestTimesNumByStep(to, step);
        this.endSegPosExclude = endNum;
    }

    /**
     * 计算当前范围内from-to的有效区间标识的清单
     */
    public void findSegPosInRange() {
        calculateTerminalSegPos();
        List<Integer> values = listAllTimesNumInRange(this.startSegPosInclude, this.endSegPosExclude, step);
        this.segmentPosList = values;
    }

    /**
     * 判断当前范围区间涉及的有效分区是否为空
     *
     * @return
     */
    public boolean isEmpty() {

        return null == this.segmentPosList || this.segmentPosList.isEmpty();

    }

}
