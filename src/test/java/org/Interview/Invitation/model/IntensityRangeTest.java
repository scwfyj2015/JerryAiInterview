package org.Interview.Invitation.model;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class IntensityRangeTest extends TestCase {

    public void testIsEmpty() {
        IntensityRange intensityRange = new IntensityRange(1, 11, 10);

        Assert.assertTrue(intensityRange.isEmpty());

        intensityRange.findSegPosInRange();
        Assert.assertTrue(intensityRange.isEmpty());

        Assert.assertTrue(intensityRange.getStartSegPosInclude().equals(0));
        Assert.assertFalse(intensityRange.getSegmentPosList().size() == 1);
    }

    public void testGetStartSegPosInclude() {

        IntensityRange intensityRange = new IntensityRange(1, 11, 10);
        intensityRange.calculateTerminalSegPos();
        Assert.assertTrue(intensityRange.getStartSegPosInclude() == 0);
        Assert.assertTrue(intensityRange.getEndSegPosExclude() == 10);

    }

    public void testGetEndSegPosExclude() {
        IntensityRange intensityRange = new IntensityRange(1, 11, 10);
        intensityRange.calculateTerminalSegPos();
        Assert.assertTrue(intensityRange.getEndSegPosExclude() == 10);
    }

    public void testGetSegmentPosList() {
        IntensityRange intensityRange = new IntensityRange(1, 11, 10);
        intensityRange.findSegPosInRange();
        Assert.assertTrue(intensityRange.isEmpty());
    }
}