import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by nitzan
 * 22/07/2013 17:08
 */
public class CalcTest {
    @Test
    public void testParseCalc1() throws Exception {

        InputStream is = IOUtils.toInputStream("x = 1\n");

        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(1 == result.get("x"));
    }

    @Test
    public void testParseCalc2() throws Exception {

        InputStream is = IOUtils.toInputStream(
                        "x = 1\n" +
                        "y = x + 1\n" +
                        "z = y - 1\n" +
                        "s = 10 / 5\n" +
                        "t = 2 * y\n"

        );

        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(1 == result.get("x"));
        Assert.assertTrue(2 == result.get("y"));
        Assert.assertTrue(1 == result.get("z"));
        Assert.assertTrue(2 == result.get("s"));
        Assert.assertTrue(4 == result.get("t"));
    }

    @Test
    public void testParseCalc3() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "x = 5 + 3 * 10\n" +
                "y = 3 * 10 + 5\n" +
                "z = 5 + 3 - 4 \n" +
                "s = 3 * 10 / 2\n"
        );

        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(35 == result.get("x"));
        Assert.assertTrue(35 == result.get("y"));
        Assert.assertTrue(4 == result.get("z"));
        Assert.assertTrue(15 == result.get("s"));
    }

    @Test
    public void testParseCalc4() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "x = 1\n" +
                "y = ++x\n" +
                "z = x++\n" +
                "s = x--\n" +
                "t = --x\n"
        );

        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(1 == result.get("x"));
        Assert.assertTrue(2 == result.get("y"));
        Assert.assertTrue(2 == result.get("z"));
        Assert.assertTrue(3 == result.get("s"));
        Assert.assertTrue(1 == result.get("t"));
    }

    @Test
    public void testParseCalc5() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "x = (5 + 3) * 10\n" +
                "y = 3 * (10 + 5)\n"
        );

        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(80 == result.get("x"));
        Assert.assertTrue(45 == result.get("y"));
    }

    @Test
    public void testParseCalcQuestionText() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "i = 0\n" +
                "j = ++i\n" +
                "x = i++ + 5\n" +
                "y = 5 + 3 * 10\n" +
                "i += y\n"
        );
        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(37 == result.get("i"));
        Assert.assertTrue(1 == result.get("j"));
        Assert.assertTrue(6 == result.get("x"));
        Assert.assertTrue(35 == result.get("y"));
    }


    @Test
    public void testParseCalcStress() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "i = 10 + 10 * 5 / (3 - 1) - 5 * 2 \n" +
                "j = i\n" +
                "h = i\n" +
                "j += j++ \n" +
                "h += ++h \n" +
                "x = (--i + i) /2 \n"
        );
        Map<String, Integer> result = Calc.parseCalc(is);

        Assert.assertTrue(24 == result.get("i"));
        Assert.assertTrue(50 == result.get("j"));
        Assert.assertTrue(51 == result.get("h"));
        Assert.assertTrue(24 == result.get("x"));
    }


    @Test
    public void testParseError1() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "i = j + 1 \n"
        );

        try {
            Calc.parseCalc(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; //ok
        }
        Assert.fail();
    }


    @Test
    public void testParseError2() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "i += 1 \n"
        );

        try {
            Calc.parseCalc(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; //ok
        }
        Assert.fail();
    }

    @Test
    public void testParseError3() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "1 = i \n"
        );

        try {
            Calc.parseCalc(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; //ok
        }
        Assert.fail();
    }

    @Test
    public void testParseError4() throws Exception {

        InputStream is = IOUtils.toInputStream(
                "#$#$#$FEE#$D# \n"
        );

        try {
            Calc.parseCalc(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; //ok
        }
        Assert.fail();
    }

}
