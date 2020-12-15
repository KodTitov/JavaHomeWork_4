package org.itstep;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StringCalculatorTest {

    @Test
    public void kata01(){
        Assert.assertEquals(0, StringCalculator.add(null));
        Assert.assertEquals(0, StringCalculator.add(""));
        Assert.assertEquals(1, StringCalculator.add("1"));
        Assert.assertEquals(3, StringCalculator.add("1,2"));
        Assert.assertEquals(4, StringCalculator.add("4,"));
    }

    @Test
    public void kata02() {
        Assert.assertEquals(6, StringCalculator.add("1,2,3"));
        Assert.assertEquals(4, StringCalculator.add("1,1,1,1"));
        Assert.assertEquals(3, StringCalculator.add(" 1,2 "));
        Assert.assertEquals(66, StringCalculator.add("11,22,33"));
    }

    @Test
    public void kata03(){
        Assert.assertEquals(6, StringCalculator.add("1 \n2,3"));
        Assert.assertEquals(15, StringCalculator.add("4\n5\n6"));
        Assert.assertEquals(77, StringCalculator.add("77\n"));
    }

    @Test
    public void kata04(){
        Assert.assertEquals(1, StringCalculator.add("1, 1001"));
        Assert.assertEquals(2, StringCalculator.add("1002 \n 2"));
        Assert.assertEquals(1001, StringCalculator.add("1, 1000"));
        Assert.assertEquals(2000, StringCalculator.add("1000, 1000"));
        Assert.assertEquals(0, StringCalculator.add("1001\n1001"));
    }

    @Test
    public void kata05(){
        Assert.assertEquals(3, StringCalculator.add("//;\n1; 2"));
        Assert.assertEquals(5, StringCalculator.add("//* \n2,3"));
        Assert.assertEquals(7, StringCalculator.add("//#\n3# 4"));
        ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bufferedOutputStream));
        StringCalculator.add("//1\n1 1 1");
        Assert.assertEquals("throw SpliterFormatException\r\n", bufferedOutputStream.toString());
        bufferedOutputStream.reset();
        StringCalculator.add("//;\n1#2");
        Assert.assertEquals("throw SpliterFormatException\r\n", bufferedOutputStream.toString());
        System.setOut(null);
    }

    @Test
    public void kata06(){
        Assert.assertEquals(6, StringCalculator.add("//[***]\n1 *** 2 *** 3"));
        Assert.assertEquals(20, StringCalculator.add("//[xy]\n3xy4xy5xy8"));
    }

    @Test
    public void kata07(){
        Assert.assertEquals(6, StringCalculator.add("// [*] [%] \n1*2%3"));
        Assert.assertEquals(8, StringCalculator.add("// [&&] [|||] \n3|||2&&3"));
    }

    @Test
    public void kata08(){
        Assert.assertEquals(14, StringCalculator.add("//[#$]\n1#$2 #$ 3", "// [&&] [||| \n3 |||2 &&3"));
        Assert.assertEquals(10, StringCalculator.add("1", "1,2", "1\n2,3"));
    }

    @Test
    public void kata09(){
        ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bufferedOutputStream));
        StringCalculator.add("1, – 1");
        Assert.assertEquals("– 1\nthrow NumberNegativException\r\n", bufferedOutputStream.toString());
        bufferedOutputStream.reset();
        StringCalculator.add(" – 1, 1");
        Assert.assertEquals("– 1\nthrow NumberNegativException\r\n", bufferedOutputStream.toString());
        bufferedOutputStream.reset();
        StringCalculator.add(" – 1, – 1");
        Assert.assertEquals("– 1 – 1\nthrow NumberNegativException\r\n", bufferedOutputStream.toString());
        bufferedOutputStream.reset();
        StringCalculator.add(" – 1,1\n – 1");
        Assert.assertEquals("– 1 – 1\nthrow NumberNegativException\r\n", bufferedOutputStream.toString());
        System.setOut(null);
    }

    @Test
    public void kata10(){
        Assert.assertEquals(6, StringCalculator.add("// [*] [%] \n1 * 2% 3"));
        Assert.assertEquals(8, StringCalculator.add("// [&&] [|||]\n3 |||2&&3"));
        Assert.assertEquals(7, StringCalculator.add("// [//] \n 1//2//4"));
        Assert.assertEquals(1998, StringCalculator.add("999,999"));
        Assert.assertEquals(3, StringCalculator.add("3,1001"));
        Assert.assertEquals(6, StringCalculator.add("// [*] [%][] \n1 * 2% 3"));
    }

    @Test
    public void kata11(){
        Assert.assertEquals(6, StringCalculator.add("2^, 2"));
        Assert.assertEquals(10, StringCalculator.add("3^\n1"));
        Assert.assertEquals(8, StringCalculator.add("2^2, 2^2"));
    }

    @Test
    public void kata12(){
        Assert.assertEquals(10, StringCalculator.add("2^3, 2"));
        Assert.assertEquals(31, StringCalculator.add("3^3\n2^2"));
        Assert.assertEquals(257, StringCalculator.add("//;\n1; 2^8"));
    }
}
