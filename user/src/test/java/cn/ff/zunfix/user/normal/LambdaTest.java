package cn.ff.zunfix.user.normal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * _
 *
 * @author fengfan 2020/9/14
 */
public class LambdaTest {


    public static void main(String[] args) {
        /*List list = new ArrayList(){{
            new Base("aa");
            new Base("bb");
            new Base("cc");
        }};*/

  /*      List<Base> list = new ArrayList<>();
        list.add(new Base("a"));
        list.add(new Base("b"));

        List ll = list.stream().map(e -> {
            e.setName(e.getName() + e.getName());
            return e;
        }).collect(Collectors.toList());


        list.forEach(System.out::println);
        ll.forEach(System.out::println);*/


        List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(999));
        list.add(Integer.valueOf(1000));

        List<Integer> r = list.stream().map(e -> {
            e += 2;
            return e;
        }).collect(Collectors.toList());
        list.forEach(System.out::println);
        r.forEach(System.out::println);


    }

    public String repeat(String c) {
        return c + c;
    }


}

@Getter
@Setter
class Base {
    private String name;

    public Base(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
