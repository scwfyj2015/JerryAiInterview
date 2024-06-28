package org.Interview.Invitation.model;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yujie.fan
 * @description TODO
 * @date 2024-06-27
 */
public class IntensitySegmentsTest {

    @Test
    public void IntensitySegmentsTest() {

        IntensitySegments intensitySegmentA = new IntensitySegments();
        Assert.assertTrue(intensitySegmentA.getStep().equals(10));
        Assert.assertTrue(intensitySegmentA.getSegmentToAmountMap() != null);

        IntensitySegments intensitySegmentB = new IntensitySegments(5);
        Assert.assertTrue(intensitySegmentB.getStep().equals(5));

        Map<Integer, Integer> segmentToAmountMap = new TreeMap<>();

        IntensitySegments intensitySegmentC = new IntensitySegments(segmentToAmountMap, 4);
        Assert.assertTrue(intensitySegmentC.getStep().equals(4));
        Assert.assertTrue(null != intensitySegmentC.getSegmentToAmountMap());
        Assert.assertTrue(intensitySegmentC.getSegmentToAmountMap().isEmpty());

        IntensitySegments intensitySegmentD = new IntensitySegments(segmentToAmountMap);
        Assert.assertTrue(intensitySegmentD.getStep().equals(10));
        Assert.assertTrue(null != intensitySegmentD.getSegmentToAmountMap());
        Assert.assertTrue(intensitySegmentD.getSegmentToAmountMap().isEmpty());

    }

    @Test
    public void testAdd() {

        IntensitySegments segments = new IntensitySegments();
        segments.add(-30, -10, 1);
        String str = segments.toString(); // Should be "[[10,-1],[20,0],[30,-1],[40,0]]"
        String expected = "[[-30,1],[-10,0]]";
        Assert.assertTrue(expected.equals(str));


        segments = new IntensitySegments();
        str = segments.toString(); // Should be "[]"
        expected = "[]"; // Should be "[]"
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 30, 1);
        str = segments.toString(); // Should be: "[[10,1],[30,0]]"
        expected = "[[10,1],[30,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(20, 40, 1);
        str = segments.toString(); // Should be: "[[10,1],[20,2],[30,1],[40,0]]"
        expected = "[[10,1],[20,2],[30,1],[40,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 40, -2);
        str = segments.toString();
        expected = "[[10,-1],[20,0],[30,-1],[40,0]]";
        Assert.assertTrue(expected.equals(str));


        // Another example sequence:
        segments = new IntensitySegments();
        str = segments.toString(); // Should be "[]"
        expected = "[]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 30, 1);
        str = segments.toString(); // Should be "[[10,1],[30,0]]"
        expected = "[[10,1],[30,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(20, 40, 1);
        str = segments.toString(); // Should be "[[10,1],[20,2],[30,1],[40,0]]"
        expected = "[[10,1],[20,2],[30,1],[40,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 40, -1);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[20,1],[30,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 40, -1);
        str = segments.toString(); // Should be "[[10,-1],[20,0],[30,-1],[40,0]]"
        expected = "[[10,-1],[20,0],[30,-1],[40,0]]";
        Assert.assertTrue(expected.equals(str));


    }

    @Test
    public void testSet() {
        // Another example sequence:
        IntensitySegments segments = new IntensitySegments();
        String str = segments.toString(); // Should be "[]"
        String expected = "[]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 30, 1);
        str = segments.toString(); // Should be "[[10,1],[30,0]]"
        expected = "[[10,1],[30,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(20, 40, 1);
        str = segments.toString(); // Should be "[[10,1],[20,2],[30,1],[40,0]]"
        expected = "[[10,1],[20,2],[30,1],[40,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.add(10, 40, -2);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[10,-1],[20,0],[30,-1],[40,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.set(20, 90, 1);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[10,-1],[20,1],[90,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.set(30, 60, 2);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[10,-1],[20,1],[30,2],[60,1],[90,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.set(60, 90, -1);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[10,-1],[20,1],[30,2],[60,-1],[90,0]]";
        Assert.assertTrue(expected.equals(str));

        segments.set(70, 90, 0);
        str = segments.toString(); // Should be "[[20,1],[30,0]]"
        expected = "[[10,-1],[20,1],[30,2],[60,-1],[70,0]]";
        Assert.assertTrue(expected.equals(str));
    }


    @Test
    public void  removeInvalidSegment(){
        IntensitySegments segments = new IntensitySegments();
        Map<Integer, Integer> amountMap = new HashMap<>();

        amountMap.put(10,0);
        amountMap.put(20,0);
        amountMap.put(30,0);
        amountMap.put(40,-1);
        amountMap.put(50,0);
        amountMap.put(60,3);
        amountMap.put(90,0);
        amountMap.put(120,0);

        segments.setSegmentToAmountMap(amountMap);
        String stri=segments.toString();
        System.out.println(stri);
        String expected = "[[40,-1],[50,0],[60,3],[90,0]]";
        Assert.assertTrue(stri.equals(expected));

    }

}