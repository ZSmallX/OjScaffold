package oj.scaffold;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OjUtilsTest {
    public static final boolean DEBUG = false;

    @Test
    public void testToIntMatrix_empty1dMatrix() {
        Assert.assertArrayEquals(new int[][]{}, OjUtils.toIntMatrix("[]"));
    }

    @Test
    public void testToIntMatrix_empty2dMatrix() {
        Assert.assertArrayEquals(new int[][]{{}}, OjUtils.toIntMatrix("[[]]"));
    }

    @Test
    public void testToIntMatrix_singleElement2d() {
        Assert.assertArrayEquals(new int[][]{{1}}, OjUtils.toIntMatrix("[[1]]"));
    }

    @Test
    public void testToCharMatrix_2d() {
        Assert.assertArrayEquals(new char[][]{{'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'},}, OjUtils.toCharMatrix(
                "[[\"1\",\"0\",\"1\",\"0\",\"0\"]," +
                        "[\"1\",\"0\",\"1\",\"1\",\"1\"]," +
                        "[\"1\",\"1\",\"1\",\"1\",\"1\"]," +
                        "[\"1\",\"0\",\"0\",\"1\",\"0\"]]"));
    }

    @Test
    public void testToCharMatrix_singleElement2d() {
        Assert.assertArrayEquals(new char[][]{{'1'}}, OjUtils.toCharMatrix("[[\"1\"]]"));
    }

    @Test
    public void testToCharMatrix_empty2d() {
        Assert.assertArrayEquals(new char[][]{{}}, OjUtils.toCharMatrix("[[]]"));
    }

    @Test
    public void testToListList_emptyList() {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        lists.add(list);
        Assert.assertEquals(lists, OjUtils.toListList("[[]]", OjUtils.INTEGER_CREATOR));
    }

    @Test
    public void testToListList_singleElement() {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        lists.add(list);
        Assert.assertEquals(lists, OjUtils.toListList("[[1]]", OjUtils.INTEGER_CREATOR));
    }

    @Test
    public void testToListList_stringCreator() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add(".Q..");
        list1.add("...Q");
        list1.add("Q...");
        list1.add("..Q.");
        List<String> list2 = new ArrayList<>();
        list2.add("..Q.");
        list2.add("Q...");
        list2.add("...Q");
        list2.add(".Q..");
        lists.add(list1);
        lists.add(list2);
        Assert.assertEquals(lists, OjUtils.toListList("[\n" +
                " [\".Q..\",  \n" +
                "  \"...Q\",\n" +
                "  \"Q...\",\n" +
                "  \"..Q.\"],\n" +
                "\n" +
                " [\"..Q.\",  \n" +
                "  \"Q...\",\n" +
                "  \"...Q\",\n" +
                "  \".Q..\"]\n" +
                "]", OjUtils.STRING_CREATOR));
    }

    @Test
    public void testToListList_stringCreator_singleElement() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add(".");
        list1.add("...Q");
        list1.add("Q...");
        list1.add("..Q.");
        List<String> list2 = new ArrayList<>();
        list2.add("..Q.");
        list2.add("Q...");
        list2.add("...Q");
        list2.add(".Q..");
        lists.add(list1);
        lists.add(list2);
        Assert.assertEquals(lists, OjUtils.toListList("[\n" +
                " [\".\",  \n" +
                "  \"...Q\",\n" +
                "  \"Q...\",\n" +
                "  \"..Q.\"],\n" +
                "\n" +
                " [\"..Q.\",  \n" +
                "  \"Q...\",\n" +
                "  \"...Q\",\n" +
                "  \".Q..\"]\n" +
                "]", OjUtils.STRING_CREATOR));
    }

    @Test
    public void testToListList_stringCreator_emptyQuotes() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("");
        list1.add("...Q");
        list1.add("Q...");
        list1.add("..Q.");
        List<String> list2 = new ArrayList<>();
        list2.add("..Q.");
        list2.add("Q...");
        list2.add("...Q");
        list2.add(".Q..");
        lists.add(list1);
        lists.add(list2);
        Assert.assertEquals(lists, OjUtils.toListList("[\n" +
                " [\"\",  \n" +
                "  \"...Q\",\n" +
                "  \"Q...\",\n" +
                "  \"..Q.\"],\n" +
                "\n" +
                " [\"..Q.\",  \n" +
                "  \"Q...\",\n" +
                "  \"...Q\",\n" +
                "  \".Q..\"]\n" +
                "]", OjUtils.STRING_CREATOR));
    }

    @Test
    public void testToListList_stringCreator_empty() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("...Q");
        list1.add("Q...");
        list1.add("..Q.");
        List<String> list2 = new ArrayList<>();
        list2.add("..Q.");
        list2.add("Q...");
        list2.add("...Q");
        list2.add(".Q..");
        lists.add(list1);
        lists.add(list2);
        Assert.assertEquals(lists, OjUtils.toListList("[\n" +
                " [,  \n" +
                "  \"...Q\",\n" +
                "  \"Q...\",\n" +
                "  \"..Q.\"],\n" +
                "\n" +
                " [\"..Q.\",  \n" +
                "  \"Q...\",\n" +
                "  \"...Q\",\n" +
                "  \".Q..\"]\n" +
                "]", OjUtils.STRING_CREATOR));
    }

    @Test
    public void testToListList_matrix() {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(7);
        list.add(11);
        list.add(15);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(5);
        list2.add(8);
        list2.add(12);
        list2.add(19);
        List<Integer> list3 = new ArrayList<>();
        list3.add(3);
        list3.add(6);
        list3.add(9);
        list3.add(16);
        list3.add(22);
        List<Integer> list4 = new ArrayList<>();
        list4.add(10);
        list4.add(13);
        list4.add(14);
        list4.add(17);
        list4.add(24);
        List<Integer> list5 = new ArrayList<>();
        list5.add(18);
        list5.add(21);
        list5.add(23);
        list5.add(26);
        list5.add(30);
        lists.add(list);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);
        Assert.assertEquals(lists, OjUtils.toListList("[\n" +
                "     [1,   4,  7, 11, 15],\n" +
                "     [2,   5,  8, 12, 19],\n" +
                "     [3,   6,  9, 16, 22],\n" +
                "     [10, 13, 14, 17, 24],\n" +
                "     [18, 21, 23, 26, 30]\n" +
                "     ]", OjUtils.INTEGER_CREATOR));
        if (DEBUG) {
            System.out.println(OjUtils.toListList("[\n" +
                    "     [1,   4,  7, 11, 15],\n" +
                    "     [2,   5,  8, 12, 19],\n" +
                    "     [3,   6,  9, 16, 22],\n" +
                    "     [10, 13, 14, 17, 24],\n" +
                    "     [18, 21, 23, 26, 30]\n" +
                    "     ]", OjUtils.INTEGER_CREATOR));
        }
    }
}
