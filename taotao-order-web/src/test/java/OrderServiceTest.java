import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by 李壮壮 on 2018/9/13.
 */

@RunWith(Parameterized.class)
public class OrderServiceTest {

    private Integer a;
    private Integer b;
    private Integer sum;
    @Parameterized.Parameters
    public static Collection<Integer[]> testData() {
        return Arrays.asList(new Integer[][] { { 1, 10, 12},
                { 2, 20, 22}
        });
    }

    public OrderServiceTest(Integer a, Integer b, Integer sum) {
        this.a = a;
        this.b = b;
        this.sum = sum;
    }

    //@Test
    public void add(){
        Integer c = a + b;
        System.out.println(c);
        System.out.println(sum);
        assert (sum == c);
    }

}
