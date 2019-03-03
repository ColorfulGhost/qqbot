package util;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toMap;

/**
 * https://stackoverflow.com/questions/29273705/how-to-paginate-a-list-of-objects-in-java-8
 * 分页工具类 Map Integer是当前页数 List当前页数内容
 *
 * @author wlwang3
 * @create 2018/12/4-18:28
 **/
public class PaginationUtil {
    public static <T> Map<Integer, List<T>> partition(List<T> list, int pageSize) {
        return IntStream.iterate(0, i -> i + pageSize)
                //循环需要页面尺寸  如pageSize=10 ,它就会10 20 30 40 50的一直循环
                .limit((list.size() + pageSize - 1) / pageSize)
                //限制循环的次数  list的大小+页的大小  / 页的大小
                .boxed()
                //看实现方法 把int包装成了对象
                .collect(toMap(i -> i / pageSize,
                        //返回结构 key当前页数   i包装对象后的Integer是当前循环的页数大小
                        i -> list.subList(i, min(i + pageSize, list.size()))));
        //list分页 subList(取当前循环的指针,(a <= b)：如果是返回当前指针+分页的大小?:如果不是则当前的指针大小到最后一个指针的数据)
    }
}
