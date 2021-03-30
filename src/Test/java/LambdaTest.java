import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class LambdaTest {


    int[] bubble = {2, 3, 1, 8, 4, 23, 15, 23, 54, 0};
    int[] fast = {2, 3, 1, 8, 4, 23, 15, 23, 54, 0};
    int[] merge = {2, 3, 1, 8, 4, 23, 15, 23, 54, 0};
    ;

    interface Sorter{
        void sorter(int[] array);
    }
    @Test
    public void LambdaRun() {
        Sorter bubbleSort = (bubble) -> {
            boolean isSorted = false;
            int temp;
            while (!isSorted) {
                isSorted = true;
                for (int i = 0; i < bubble.length - 1; i++) {
                    if (bubble[i] > bubble[i + 1]) {
                        temp = bubble[i];
                        bubble[i] = bubble[i + 1];
                        bubble[i + 1] = temp;
                        isSorted = false;
                    }
                }
            }
        };

        Sorter fastSort = (fast) -> {
            quickSort(fast, 0, fast.length - 1);
        };

        Sorter mergeSort = (merge) -> {
            sortUnsorted(merge, 0, merge.length - 1);
        };
        bubbleSort.sorter(bubble);
        fastSort.sorter(fast);
        mergeSort.sorter(merge);
        int[] result = {0, 1, 2, 3, 4, 8, 15, 23, 23, 54};
        Assertions.assertTrue(Arrays.equals(bubble, result) && Arrays.equals(fast, result) &&
                Arrays.equals(merge, result));
    }
    public static void sortUnsorted(int[] source, int left, int right) {
        // Выберем разделитель, т.е. разделим пополам входной массив
        int delimiter = left + ((right - left) / 2) + 1;
        // Выполним рекурсивно данную функцию для двух половинок (если сможем разбить(
        if (delimiter > 0 && right > (left + 1)) {
            sortUnsorted(source, left, delimiter - 1);
            sortUnsorted(source, delimiter, right);
        }
        int[] buffer = new int[right - left + 1];
        // Начиная от указанной левой границы идём по каждому элементу
        int cursor = left;
        for (int i = 0; i < buffer.length; i++) {
            // Мы используем delimeter чтобы указывать на элемент из правой части
            // Если delimeter > right, значит в правой части не осталось недобавленных элементов
            if (delimiter > right || source[cursor] < source[delimiter]) {
                buffer[i] = source[cursor];
                cursor++;
            } else {
                buffer[i] = source[delimiter];
                delimiter++;
            }
        }
        System.arraycopy(buffer, 0, source, left, buffer.length);
    }

    public static void quickSort(int[] source, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = source[(leftMarker + rightMarker) / 2];
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (source[leftMarker] < pivot) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (source[rightMarker] > pivot) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                    int tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                // Сдвигаем маркеры, чтобы получить новые границы
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // Выполняем рекурсивно для частей
        if (leftMarker < rightBorder) {
            quickSort(source, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            quickSort(source, leftBorder, rightMarker);
        }
    }

}
