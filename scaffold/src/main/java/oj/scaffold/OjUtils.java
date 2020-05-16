package oj.scaffold;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

/**
 * Utils for OJ(Online Judge).
 * Handles inputs:
 * {@link #newTreeFromArrays(Integer...)}
 * {@link #newListNodeFromArrays(Integer...)}
 * {@link #toIntMatrix(String)}
 * {@link #toListList(String, Creator)}
 * <p>
 * Handles outputs:
 * {@link #convertTreeAsArrays(TreeNode)}
 * {@link #travelListNode(ListNode, int)}
 * <p>
 * debug improvement:
 * <p>
 * unit test judge:
 * {@link ListListMatcher}
 *
 * @author zsmallx
 * @since 2020/03/15
 */
public class OjUtils {
    public static final Creator<Integer> INTEGER_CREATOR = new Creator<Integer>() {
        @Override
        public Integer create(String text) {
            try {
                return Integer.valueOf(text);
            } catch (NumberFormatException e) {
                // TODO: 2020/3/21 maybe a obvious exception here.
                return Integer.MIN_VALUE;
            }
        }
    };

    public static final Creator<String> STRING_CREATOR = new Creator<String>() {
        @Override
        public String create(String text) {
            return removeQuotesIfNeeded(text);
        }
    };

    private static String removeQuotesIfNeeded(String text) {
        if (Pattern.matches("\".*\"", text)) {
            // length >= 2 now.
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    public static String arraysToString(Integer[] integers) {
        StringBuilder builder = new StringBuilder();
        for (Integer integer : integers) {
            builder.append(integer).append(" ");
        }
        return builder.toString();
    }

    public static TreeNode newTreeFromArrays(Integer... values) {
        ArrayList<TreeNode> tree = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                tree.add(new TreeNode(values[i]));
            } else {
                tree.add(null);
            }
        }

        for (int i = 0; i < values.length / 2; i++) {
            if (tree.get(2 * i + 1) != null) {
                tree.get(i).left = tree.get(2 * i + 1);
            }
            if (tree.get(2 * i + 2) != null) {
                tree.get(i).right = tree.get(2 * i + 2);
            }
        }
        return tree.get(0);
    }

    public static Integer[] convertTreeAsArrays(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return new Integer[0];
        }
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0));
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int currentLevel = pair.getValue();
            if (currentLevel >= levels.size()) {
                levels.add(currentLevel, new ArrayList<Integer>());
            }
            if (node == null) {
                levels.get(currentLevel).add(null);
            } else {
                levels.get(currentLevel).add(node.val);
                queue.add(new Pair<>(node.left, currentLevel + 1));
                queue.add(new Pair<>(node.right, currentLevel + 1));
            }
        }
        int size = 0;
        for (int i = 0; i < levels.size() - 1; i++) { // 抛弃最后一层的null
            size += levels.get(i).size();
        }
        Integer[] result = new Integer[size];
        for (int i = 0, count = 0; i < levels.size() - 1; i++) { // 抛弃最后一层的null值
            List<Integer> level = levels.get(i);
            for (int j = 0; j < level.size(); j++) {
                result[count] = level.get(j);
                count++;
            }
        }
        return result;
    }

    /**
     * Travels the target amount of {@link ListNode}s, outputs the values of them in order.
     * Loop linkNode safe.
     *
     * @param node        {@link ListNode} we begin.
     * @param targetCount the count of {@link ListNode} we travel.
     * @return int array in traveling order, no resize when {@code count} < {@code targetCount}.
     */
    public static int[] travelListNode(ListNode node, int targetCount) {
        int[] values = new int[targetCount];
        int count = 0;
        while (node != null && count < targetCount) {
            values[count] = node.val;
            node = node.next;
            count++;
        }
        return values;
    }

    /**
     * Set the ListNode tail link to target position, as a loop.
     *
     * @param head ListNode head we begin.
     * @param pos  which ListNode we expected to set loop begin.
     */
    public static void setListNodeLoopAt(ListNode head, int pos) {
        // TODO: 2020/3/15 check pos valid? pos < 0 or pos > size?
        ListNode loopPoint = null;
        ListNode curr = head;
        while (curr.next != null) {
            if (pos-- == 0) {
                loopPoint = curr;
            }
            curr = curr.next;
        }
        if (loopPoint != null) {
            curr.next = loopPoint;
        }
    }

    public static ListNode newListNodeFromArrays(Integer... values) {
        ListNode head = new ListNode(values[0]);
        ListNode curr = head;
        for (int i = 1; i < values.length; i++) {
            ListNode node = new ListNode(values[i]);
            curr.next = node;
            curr = node;
        }
        return head;
    }

    /**
     * Handles input like this:
     * [\n
     * [1,   4,  7, 11, 15],\n
     * [2,   5,  8, 12, 19],\n
     * [3,   6,  9, 16, 22],\n
     * [10, 13, 14, 17, 24],\n
     * [18, 21, 23, 26, 30]\n
     * ]\n
     * <p>
     * or
     * <p>
     * [[1,1]]
     * <p>
     * [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]
     *
     * @param text input
     * @return result in int[][], or exception if not excepted format.
     * @throws IllegalArgumentException if there is any elements in the input can not be parsed to int.
     */
    public static int[][] toIntMatrix(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        String cleanS = text.replaceAll("\n", "").replace(" ", "");
        if (!LeetCode.check1dFormat(cleanS)) {
            return new int[0][0];
        }
        if (!LeetCode.check2dFormat(cleanS)) {
            return new int[1][0];
        }
        String[] pieces = cleanS.split("],");
        // TODO: 2020/3/15 check is a valid matrix.
        // FIXME: 2020/3/20 invalid length handle.
        int[][] result = new int[pieces.length][];
        for (int i = 0; i < pieces.length; i++) {
            String raw = pieces[i].replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .trim();
            if (raw.length() == 0) {
                result[i] = new int[0];
                continue;
            }
            String[] integers = raw.split(",");
            int[] ints = new int[integers.length];
            for (int j = 0; j < integers.length; j++) {
                // FIXME: 2020/3/20 empty String.
                try {
                    ints[j] = Integer.parseInt(
                            integers[j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Bad input can not be parsed to int, value is : " + integers[j]);
                }
            }
            result[i] = ints;
        }
        return result;
    }

    /**
     * Handles input like this:
     * [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
     *
     * TODO generic with {@link #toIntMatrix(String)}.
     *
     * @param text input
     * @return result in char[][], or exception if not excepted format.
     * @throws IllegalArgumentException if there is any elements in the input can not be parsed to int.
     */
    public static char[][] toCharMatrix(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        String oneLine = text.replaceAll("\n", "").replace(" ", "");
        if (!LeetCode.check1dFormat(oneLine)) {
            return new char[0][0];
        }
        if (!LeetCode.check2dFormat(oneLine)) {
            return new char[1][0];
        }

        String[] pieces = oneLine.split("],");
        // TODO: 2020/3/15 check is a valid matrix.
        // FIXME: 2020/3/20 invalid length handle.
        char[][] result = new char[pieces.length][];
        for (int i = 0; i < pieces.length; i++) {
            String raw = pieces[i].replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .trim();
            if (raw.length() == 0) {
                result[i] = new char[0];
                continue;
            }
            String[] charStr = raw.split(",");
            char[] chars = new char[charStr.length];
            for (int j = 0; j < charStr.length; j++) {
                // FIXME: 2020/3/20 empty String.
                if (charStr[j].length() != 3) { // eg. "1"
                    throw new IllegalArgumentException("char size not three!");
                }
                try {
                    chars[j] = charStr[j].charAt(1); // eg. "1"
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Bad input can not be parsed to int, value is : " + charStr[j]);
                }
            }
            result[i] = chars;
        }
        return result;
    }

    /**
     * Handles input like this:
     * [\n
     * [1,   4,  7, 11, 15],\n
     * [2,   5,  8, 12, 19],\n
     * [3,   6,  9, 16, 22],\n
     * [10, 13, 14, 17, 24],\n
     * [18, 21, 23, 26, 30]\n
     * ]\n
     * <p>
     * or
     * <p>
     * [[1,1]]
     * <p>
     * [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]]
     *
     * @param text    input.
     * @param creator target type creator.
     * @param <T>     type of list content.
     * @return result in List<List<T>>, or exception if not excepted format.
     */
    public static <T> List<List<T>> toListList(String text, Creator<T> creator) {
        if (text == null || text.length() == 0) {
            return null;
        }
        String cleanS = text.replaceAll("\n", "").replace(" ", "");
        if (!LeetCode.check1dFormat(cleanS)) {
            return Collections.emptyList();
        }
        List<List<T>> lists = new ArrayList<>();
        if (!LeetCode.check2dFormat(cleanS)) {
            lists.add(Collections.<T>emptyList());
            return lists;
        }
        String[] pieces = cleanS.split("],");
        // TODO: 2020/3/20 maybe generate a method of split.
        for (String piece : pieces) {
            // TODO: 2020/3/21 maybe check splits' format.
            String[] elements = piece.split(",");
            List<T> list = new ArrayList<>();
            for (String element : elements) {
                String content = element.replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .trim();
                if (content != null && content.length() != 0) {
                    list.add(creator.create(content));
                }
            }
            lists.add(list);
        }
        return lists;
    }

    public interface Creator<T> {
        T create(String text);
    }

    public static final class ListListMatcher extends BaseMatcher {
        private final List<List<Integer>> target;

        public ListListMatcher(List<List<Integer>> target) {
            this.target = target;
        }

        @Override
        public boolean matches(Object item) {
            return item instanceof List
                    && ((List) item).size() == target.size()
                    && target.containsAll(((List) item));
        }

        @Override
        public void describeTo(Description description) {
        }
    }

    static final class LeetCode {
        static boolean check1dFormat(String s) {
            return Pattern.matches("\\[.+]", s);
        }

        static boolean check2dFormat(String s) {
            return Pattern.matches("\\[\\[.+]]", s);
        }
    }
}
