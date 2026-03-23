import java.util.*;

public class SplitStay {

    static class Result {
        int A, B, split;

        Result(int A, int B, int split) {
            this.A = A;
            this.B = B;
            this.split = split;
        }

        @Override
        public String toString() {
            return "(" + A + ", " + B + ", " + split + ")";
        }
    }

    public static List<Result> findSplitStays(List<List<Integer>> avail,
                                              int start, int end) {
        int n = avail.size();

        // Step 1: clean + sort
        List<int[]> cleanAvail = new ArrayList<>();
        for (List<Integer> list : avail) {
            TreeSet<Integer> set = new TreeSet<>(list); // remove duplicates + sort
            int[] arr = new int[set.size()];
            int i = 0;
            for (int val : set) arr[i++] = val;
            cleanAvail.add(arr);
        }

        // Step 2: precompute coverage
        Map<Integer, List<Integer>> leftHotels = new HashMap<>();
        Map<Integer, List<Integer>> rightHotels = new HashMap<>();

        for (int s = start; s < end; s++) {
            leftHotels.put(s, new ArrayList<>());
            rightHotels.put(s + 1, new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            int[] dates = cleanAvail.get(i);

            for (int s = start; s < end; s++) {

                if (canCover(dates, start, s)) {
                    leftHotels.get(s).add(i);
                }

                if (canCover(dates, s + 1, end)) {
                    rightHotels.get(s + 1).add(i);
                }
            }
        }

        // Step 3: combine
        List<Result> res = new ArrayList<>();

        for (int s = start; s < end; s++) {
            List<Integer> left = leftHotels.get(s);
            List<Integer> right = rightHotels.get(s + 1);

            for (int A : left) {
                for (int B : right) {
                    if (A != B) {
                        res.add(new Result(A, B, s));
                    }
                }
            }
        }

        return res;
    }

    // Binary search + continuity check
    private static boolean canCover(int[] dates, int L, int R) {
        int idx = Arrays.binarySearch(dates, L);
        if (idx < 0) return false;

        int needed = R - L + 1;
        if (idx + needed > dates.length) return false;

        for (int i = 0; i < needed; i++) {
            if (dates[idx + i] != L + i) return false;
        }
        return true;
    }

    // Example
    public static void main(String[] args) {
        List<List<Integer>> avail = Arrays.asList(
                Arrays.asList(10, 11, 12),
                Arrays.asList(12, 13),
                Arrays.asList(10, 11, 13)
        );

        int start = 10, end = 13;

        List<Result> res = findSplitStays(avail, start, end);
        System.out.println(res);
    }
}