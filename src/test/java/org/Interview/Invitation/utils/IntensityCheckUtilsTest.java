package org.Interview.Invitation.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class IntensityCheckUtilsTest {

    @Test
    public void testValidCheck() {

        boolean result = IntensityCheckUtils.validCheck(10, 20, 10);
        Assert.assertTrue(result);

        try {
            IntensityCheckUtils.validCheck(10, 4, 10);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(JerriErrorCode.INVALID_NUM_RANGE.errorDesc));
        }

        try {
            IntensityCheckUtils.validCheck(4, 14, 10);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(JerriErrorCode.INVALID_DISTANCE.errorDesc));
        }

    }
}