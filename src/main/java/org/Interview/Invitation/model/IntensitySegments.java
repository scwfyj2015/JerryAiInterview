package org.Interview.Invitation.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.Interview.Invitation.utils.ConstantUtils;
import org.Interview.Invitation.utils.IntensityCheckUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.Interview.Invitation.utils.ConstantUtils.DEFAULT_SCORE;
import static org.Interview.Invitation.utils.ConstantUtils.DEFAULT_SEGMENT_STEP;

/**
 * @author yujie.fan
 * @description 类{IntensitySegments}是一个用来描述 区间-分值 关系的对象，区间用区间标识来表示。
 * 区间范围：[负无穷，正无穷] 二维坐标系的X轴，按照指定步长从中心点0开始往左右分段，
 * 步长默认为10，每个段为一个区间，区间为前闭后开的关系，分段标识默认采用最小值表示。
 * <p>
 * 区间标识值: 二维坐标系的X轴，按照指定步长分段后，用区间内的最小的数位置代表该区间，
 * 如：10 代表[10,20)。-20代表[-20,-10)
 * <p>
 * 分值：正负整数，分值是给指定区间的打分，分值可以在已有分值基础上累计。
 * <p>
 * 有效区间集：左右两侧均需要从非负的区间开始，才算有效区间集。区间集内部可存在分值为负数的区间段，但是两端不可以为负数。
 * /todo 当前还没有考虑线程安全的问题
 * <p>
 * NOTE: Feel free to add any extra member variables/functions you like.
 * @date 2024-06-27
 */

@Data
@Accessors(chain = true)
public class IntensitySegments {

    /**
     * key: 每个分段区间的标识值，二维坐标系的X轴，按照指定步长分段后，用区间内的最小的数位置代表该区间
     * value: 每个段的分值，可累计，可为负数。
     */
    private Map<Integer, Integer> segmentToAmountMap;

    /**
     * 分区的步长
     */
    private Integer step = 10;

    /**
     * 有效区间的开始位置,存在性是考虑历史曾经处理过的区间的
     */
    private Integer validStartPos = Integer.MAX_VALUE;
    /**
     * 有效区间的结束位置,存在性是考虑历史曾经处理过的区间的
     */
    private Integer validEndPos = Integer.MIN_VALUE;


    public IntensitySegments() {
        this.segmentToAmountMap = new TreeMap<>();
        this.step = DEFAULT_SEGMENT_STEP;
    }


    public IntensitySegments(Integer step) {
        this.step = step;
        this.segmentToAmountMap = new TreeMap<>();
    }


    public IntensitySegments(Map<Integer, Integer> segmentToAmountMap, Integer step) {
        this.segmentToAmountMap = segmentToAmountMap;
        this.step = step;
    }

    public IntensitySegments(Map<Integer, Integer> segmentToAmountMap) {
        this.segmentToAmountMap = segmentToAmountMap;
        this.step = DEFAULT_SEGMENT_STEP;
    }


    /**
     * 给指定区间[from,to]打分，给该范围覆盖的已存在的区间累计分值。
     * 该范围覆盖的区间，对于两头的，只有超过一半才算有效区间。
     * 该区间按照步长可能被切分为多个指定段。切分时，需要考虑两头的有效性，从中心点0开始，若超过步长的一半，按照有效区间算。
     * 如：
     * (from=1,to=12),默认步长为10, 按照过半则有效的原则，该范围覆盖的有效区间标识集为[0,10)
     * (from=1,to=17),默认步长为10, 按照过半则有效的原则，该范围覆盖的有效区间标识集为[0,10,20)
     * (from=8,to=17),默认步长为10, 按照过半则有效的原则，该范围覆盖的有效区间标识集为[10,20)
     * (from=4,to=13),默认步长为10, 按照过半则有效的原则，该范围覆盖的有效区间标识集为[],无有效区间
     *
     * @param from   打分开始位置
     * @param to     打分结束位置
     * @param amount 该区间需要累计的分值
     */
    public void add(Integer from, Integer to, Integer amount) { // TODO: implement this
        //有效性校验
        IntensityCheckUtils.validCheck(from, to, step);

        //找到有效覆盖分区
        IntensityRange intensityRange = splitSegmentByStep(from, to);

        //如果没有有效覆盖分区，就不处理了
        if (intensityRange.isEmpty()) {
            return;
        }

        if (null == this.segmentToAmountMap) {
            this.segmentToAmountMap = new TreeMap<>();
        }

        //给中间的已存在分区增加分值
        List<Integer> existPos = addMidSegmentAmount(amount, intensityRange);

        //给开始分区赋值
        addStartSegmentAmount(amount, intensityRange, existPos);

        //给结束分区赋值
        addEndSegmentAmount(intensityRange);


        //记录累计的曾经处理过的最小区间
        this.validStartPos = Math.min(this.validStartPos, intensityRange.getStartSegPosInclude());

        //记录累计的曾经处理过的最大区间
        this.validEndPos = Math.max(this.validEndPos, intensityRange.getEndSegPosExclude());

        //删除无效区间，范围集两端的有效区间应该是从非负的得分开始
        removeInvalidSegment();

    }

    /**
     * 给已经存在的分区累计分值
     * @param amount  待累加的分值
     * @param intensityRange 本次处理的范围集对象
     * @return 分值被更新的区间标识集合
     */
    private List<Integer> addMidSegmentAmount(Integer amount, IntensityRange intensityRange) {
        //给存在的各个区间标识累加分值
        List<Integer> existPos = new ArrayList<>();
        for (Integer value : intensityRange.getSegmentPosList()) {
            if (this.segmentToAmountMap.containsKey(value)) {
                existPos.add(value);
                Integer newAmount = amount + this.segmentToAmountMap.get(value);
                this.segmentToAmountMap.put(value, newAmount);
            }
        }
        return existPos;
    }

    /**
     *
     * 给本次范围集的开始分区标识打分,若当前的开始分区之前没有处理过，分两种场景给初始值：
     * case1: 当前范围集的开始分区落在曾经处理过的最大分区范围内，初始值赋值为当前范围集开始分区距离当前范围集结束分区的距离。
     * case2: 当前范围集的开始分区落在曾经处理过的最小分区左侧，初始值赋值为amount。
     * @param amount  分值
     * @param intensityRange   本次处理的范围集对象
     * @param existPos 中间已经被更新过取值的区间集合
     */
    private void addStartSegmentAmount(Integer amount, IntensityRange intensityRange, List<Integer> existPos) {
        //如果没有范围集起点标识位，添加终点标识位，赋值默认得分0
        if (this.segmentToAmountMap.containsKey(intensityRange.startSegPosInclude)) {
            Integer newAmount = amount + this.segmentToAmountMap.getOrDefault(intensityRange.startSegPosInclude, 0);
            this.segmentToAmountMap.put(intensityRange.startSegPosInclude, newAmount);
        } else {
            //起点如果不存在，初始score是距离终点区间的距离
            Integer startScore = existPos.size() + 1;
            this.segmentToAmountMap.put(intensityRange.startSegPosInclude, startScore);
        }
    }

    /**
     * 给本次范围集的结束分区标识打分,若当前的结束分区之前没有处理过，赋默认值0。若已经存在，不处理。
     * @param intensityRange  本次处理的范围集对象
     */
    private void addEndSegmentAmount(IntensityRange intensityRange) {
        //如果没有范围集终点标识位，添加终点标识位，赋值默认得分0
        if (!this.segmentToAmountMap.containsKey(intensityRange.endSegPosExclude)) {
            this.segmentToAmountMap.put(intensityRange.endSegPosExclude, DEFAULT_SCORE);
        }
    }


    /**
     * 给指定范围集内的区间强制赋值，忽略已有的分值
     *
     * @param from   范围集开始位置
     * @param to     范围集结束位置
     * @param amount 新的分值
     */
    public void set(Integer from, Integer to, Integer amount) { // TODO: implement this
        //set之前是否有元素
        boolean isEmptry = this.isEmpty();

        //有效性校验
        IntensityCheckUtils.validCheck(from, to, step);

        //找到有效覆盖分区
        IntensityRange intensityRange = splitSegmentByStep(from, to);

        //如果没有有效覆盖分区，就不处理了
        if (intensityRange.isEmpty()) {
            return;
        }

        if (null == this.segmentToAmountMap) {
            this.segmentToAmountMap = new TreeMap<>();
        }

        this.validStartPos = Math.min(this.validStartPos, intensityRange.getStartSegPosInclude());
        this.validEndPos = Math.max(this.validEndPos, intensityRange.getEndSegPosExclude());

        //给覆盖到的分区强制赋值
        List<Integer> segmentPosList = intensityRange.getSegmentPosList();
        segmentPosList.forEach(a -> this.segmentToAmountMap.put(a, amount));
        this.segmentToAmountMap.put(intensityRange.getStartSegPosInclude(), amount);
        addEndSegmentAmount(intensityRange);

        //删除无效区间，范围集两端的有效区间应该是从非负的得分开始
        if (!isEmptry) {
            removeInvalidSegment();
        }
    }

    private IntensityRange splitSegmentByStep(Integer from, Integer to) {

        IntensityRange intensityRange = new IntensityRange(from, to, step);

        intensityRange.findSegPosInRange();

        return intensityRange;
    }

    private boolean isEmpty() {
        return null == this.segmentToAmountMap || this.segmentToAmountMap.isEmpty();
    }


    /**
     * 左侧有效分区的开始分值不能为0，右侧的有效分区结束位置，若以连续分值为0的分区结尾，则仅保留第一个分值为0的分区，后续的舍弃
     */
    public void removeInvalidSegment() {

        if (MapUtils.isEmpty(this.segmentToAmountMap)) {
            return;
        }

        boolean firstUpdate = false;
        boolean lastUpdate = false;


        Integer preZeroSegement = null;

        List<Integer> keys = this.segmentToAmountMap.keySet().stream().sorted().collect(Collectors.toList());
        int count = keys.size();
        int mid = count % 2 == 0 ? count / 2 + 1 : count / 2;
        for (int i = 0; i <= mid; ++i) { //从两侧收敛去排除废弃数据
            if (firstUpdate && lastUpdate) {
                break;
            }

            int startValue = keys.get(i);
            int endValue = keys.get(count - i - 1);
            if (startValue == endValue) {
                break;
            }

            if (startValue >= endValue) {
                break;
            }

            if (!firstUpdate && this.segmentToAmountMap.get(startValue) != 0) {
                //标记左侧不为0的开头
                this.validStartPos = Math.max(this.validStartPos, startValue);
                firstUpdate = true;
            }

            if (!lastUpdate) {
                if (DEFAULT_SCORE.equals(this.segmentToAmountMap.get(endValue))) {
                    if (null == preZeroSegement) {//还未出现连续的0
                        this.validEndPos = endValue;
                        preZeroSegement = endValue;
                    } else { //上一个也是0
                        this.validEndPos = endValue;
                        preZeroSegement = endValue;
                    }
                } else { //当前不是0,
                    if (preZeroSegement != null) {
                        preZeroSegement = null;
                        lastUpdate = true;
                    }
                }
            }

        }

    }


    /**
     * 左侧从第一个分值!=0的分区开始，右侧若以连续分值为0的分区标识结尾，仅保留连续分值为0的最左侧分区。舍弃后续的连续0分区。
     *
     * @return 分段标识和分值的映射关系，[[分段标识,分值],[分段标识,分值]]
     */
    public String toString() { // TODO: implement this
        if (isEmpty()) {
            return ConstantUtils.EMPTY_SERI;
        }

        List<Integer> segPosList = this.segmentToAmountMap.keySet().stream().sorted().collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer pos : segPosList) {
            if (pos < this.validStartPos) {
                continue;
            }
            if (pos > this.validEndPos) {
                break;
            }
            stringBuilder.append(segmentStr(pos, this.segmentToAmountMap.get(pos))).append(",");
        }

        return String.format("[%s]", stringBuilder.substring(0, stringBuilder.length() - 1));
    }


    public String segmentStr(Integer point, Integer amount) {
        return String.format("[%d,%d]", point, amount);
    }

}