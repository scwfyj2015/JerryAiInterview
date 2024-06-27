package org.Interview.Invitation.utils;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public enum JerriErrorCode {
    /**
     * 无效的数值范围区间
     */
    INVALID_NUM_RANGE("INVALID_NUM_RANGE", "The argument-to should be greater " +
            "than or equal to the argument-from"),
    /**
     * 无效的数值区间长度
     */
    INVALID_DISTANCE("INVALID_DISTANCE", "The length of the value interval should be " +
            "greater than or equal to the partition step");

    public String errorCode;

    public String errorDesc;

    JerriErrorCode(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
