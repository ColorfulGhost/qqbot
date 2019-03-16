package cc.vimc.bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("demo")

public class DemoController {

    /**
     * @Description lambda 测试Demo 便于以后即用即查
     * @author wlwang3
     * @param
     * @return java.lang.String
     * @date 2019/3/16
     */
    @RequestMapping("test")
    @ResponseBody
    public String test() throws NoSuchFieldException {

//        PaginationUtil.partition(10,)


        IntStream.range(0,10).forEach(value -> System.out.println(value));
        IntStream.iterate(0,i->i+10).limit(10).forEach(value -> System.out.println(value));


        //拷贝对比
        var list = List.of("Java", "Python", "C#", "C++");
        var copy = List.copyOf(list);
        System.out.println(list == copy);
        //随机后add
        var intList = new ArrayList<>();
        var random = new Random(233);
        //设置随机种子
        random.setSeed(233L);
        random.ints().limit(10).forEach(intList::add);
        /**
         * 中间操作
         */
        //Map映射
        var squaresList = intList.stream()
                .map(i -> (Integer) i * (Integer) i)
                .distinct()
                .collect(Collectors.toList());
//        squaresList.forEach(System.out::println);
        //Optional类空指针

        var val = 10;
        List valNullList = new ArrayList();
        valNullList.add(1);
        valNullList.add(null);
        valNullList.add("233");
        Integer valNull = null;

        var filterNull = valNullList.stream().filter(e -> e != null).collect(Collectors.toList());


        var testListNull = List.of("10", 10, 20);
        var a = Optional.ofNullable(valNullList);
        var b = Optional.of(val);
//        var c = Optional.of(valNullList);
        var d = Optional.ofNullable(testListNull);

        var optionalList = List.of(a, b);
        optionalList.forEach(System.out::println);
        //字符串处理
        var strings = List.of("aa", "aabc", "aa", "abc", "a", "", "jdk11");
        int index = Collections.binarySearch(strings, new String("a"));
        //distinct去处重复的数据
        //filter过滤出为true的数据
        //collect规约聚合
        //sorted 排序
        //match 匹配

        boolean anyStartWith = strings.stream().anyMatch(s -> s.startsWith("aaa"));
        boolean allStartWith = strings.stream().allMatch(s -> s.startsWith("a"));
        System.out.println(anyStartWith + " " + allStartWith);
        //Reduce 规约
        var stirngList = strings.stream().sorted().distinct().reduce((s1, s2) -> {
//            System.out.println(s1+"|"+s2);
            return s1 + "|" + s2;
        });
        System.out.println(stirngList);
        var count = strings.stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        //字符串链接
        String content = strings.stream().reduce("-", String::concat);
//        System.out.println(strings);
//        System.out.println(count);
        //统计数据
        IntSummaryStatistics stats = intList.stream().mapToInt((x) -> (int) x).summaryStatistics();
//        System.out.println("最大值"+stats.getMax());
//        System.out.println("最小值"+stats.getMin());
//        System.out.println("和值"+stats.getSum());
//        System.out.println("平均值"+stats.getAverage());

        //串行运行和并行运行比较
        var bigStrings = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            UUID uuid = UUID.randomUUID();
            bigStrings.add(uuid.toString());
        }
        //串行运行
        var startTime = System.nanoTime();
        var streamCount = bigStrings.stream().sorted();
        var endTime = System.nanoTime();
        System.out.printf("串行运行时间 %d ms", endTime - startTime);

        startTime = System.nanoTime();
        streamCount = bigStrings.parallelStream().sorted();
        endTime = System.nanoTime();
        System.out.printf("并行运行时间 %d ms", endTime - startTime);


        Field field = Boolean.class.getDeclaredField("TRUE");

        field.setAccessible(true);
        return intList.toString();
    }
}
