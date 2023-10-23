import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QueuesAndStacks {

    /**
     * Return a string representation of a {@link Stack}. In contrast to the {code toString()} method
     * of {@link Stack}, the elements are listed in the actual 'stack order', that in the order in
     * which they are pop'ed from the stack.
     *
     * @param stack The stack that is to be converted to a string.
     * @param <E> The type of elements.
     * @return String representation of the stack.
     */
    public static <E> String stackToString(Stack<E> stack) {
        StringBuilder out = new StringBuilder();
        Stack<?> stackCopy = (Stack<?>) stack.clone();
        out.append("[");
        while (!stackCopy.isEmpty()) {
            out.append(stackCopy.pop());
            if (!stackCopy.isEmpty()) {
                out.append(", ");
            }
        }
        out.append("]");
        return out.toString();
    }

    /**
     * Return a string representation of a {@link Queue}. This is the same output as for the
     * {@code toString()} method of {@link Queue}. This method is therefore redundant. It is
     * provided nevertheless as a complement to {@code stackToString()}.
     *
     * @param queue The queue that is to be converted to a string.
     * @param <E> The type of elements.
     * @return String representation of the queue.
     */
    public static <E> String queueToString(Queue<E> queue) {
        StringBuilder out = new StringBuilder();
        Queue<E> queueCopy = new LinkedList<>(queue);
        out.append("[");
        while (!queueCopy.isEmpty()) {
            out.append(queueCopy.poll());
            if (!queueCopy.isEmpty()) {
                out.append(", ");
            }
        }
        out.append("]");
        return out.toString();
    }

    /**
     * Reverse the given {@link Stack}.
     *
     * @param stack The stack that is reversed.
     * @param <E> The type of elements.
     */
    public static <E> void reverse(Stack<E> stack) {
        Queue<E> queue = new LinkedList<>();
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
    }

    /**
     * Reverse the given {@link Queue}.
     *
     * @param queue The queue that is reversed.
     * @param <E> The type of elements.
     */
    public static <E> void reverse(Queue<E> queue) {
        Stack<E> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int k = 0; k < 6; k++) {
            stack.push(k);
            queue.add(k);
        }
        // Same example, but with Strings as elements of the stack / queue
//        Stack<String> stack = new Stack<>();
//        Queue<String> queue = new LinkedList<>();
//        for (int k = 0; k < 10; k++) {
//            stack.push("Number " + k);
//            queue.add("Number " + k);
//        }

//        System.out.println("Original Stack with Stack.toString(): " + stack);
        System.out.println("Original Stack: " + stackToString(stack));
        reverse(stack);
        System.out.println("Reversed Stack: " + stackToString(stack));

//        System.out.println("Original Queue with Queue.toString(): " + queue);
        System.out.println("Original Queue: " + queueToString(queue));
        reverse(queue);
        System.out.println("Reversed Queue: " + queueToString(queue));
    }
}
