package dio.oj.scaffolding;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.util.Pair;

/**
 * Utils for OJ(Online Judge).
 * Handles inputs:
 * {@link #newTreeFromArrays(Integer...)}
 * {@link #newListNodeFromArrays(Integer...)}
 * {@link #toIntMatrix(String)}
 * {@link #toIntListList(String)}
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
     */
    public static int[][] toIntMatrix(String text) {
        String filted = text.replaceAll("\n", "").trim();
        // TODO: 2020/3/15 check text format.
        String[] pieces = filted.split("],");
        // TODO: 2020/3/15 check is a valid matrix.
        int[][] result = new int[pieces.length][pieces[0].split(",").length];
        for (int i = 0; i < pieces.length; i++) {
            String[] integers = pieces[i].split(",");
            for (int j = 0; j < integers.length; j++) {
                result[i][j] = Integer.parseInt(
                        integers[j].replaceAll("\\[", "")
                                .replaceAll("]", "")
                                .trim());
            }
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
     * @param text input
     * @return result in List<List<Integer>>, or exception if not excepted format.
     */
    public static List<List<Integer>> toIntListList(String text) {
        int[][] matrix = toIntMatrix(text);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < matrix[i].length; j++) {
                list.add(matrix[i][j]);
            }
            result.add(list);
        }
        return result;
    }

    public static class ListListMatcher extends BaseMatcher {
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
}
