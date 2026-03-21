import java.util.*;

class Solution {

    static class Job {
        long start, end;
        int reward, index;

        Job(long s, long e, int r, int i) {
            start = s;
            end = e;
            reward = r;
            index = i;
        }
    }

    public static class Result {
        long maxReward;
        List<Integer> selectedJobs;

        Result(long maxReward, List<Integer> selectedJobs) {
            this.maxReward = maxReward;
            this.selectedJobs = selectedJobs;
        }
    }

    public Result maxRewardJobs(long[] start, long[] end, int[] reward) {
        int n = start.length;

        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(start[i], end[i], reward[i], i);
        }

        // 1. Sort by end time
        Arrays.sort(jobs, Comparator.comparingLong(j -> j.end));

        // Extract end times for binary search
        long[] ends = new long[n];
        for (int i = 0; i < n; i++) {
            ends[i] = jobs[i].end;
        }

        // 2. Compute p[i]
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = binarySearch(ends, jobs[i].start);
        }

        // 3. DP
        long[] dp = new long[n];
        dp[0] = jobs[0].reward;

        for (int i = 1; i < n; i++) {
            long take = jobs[i].reward;
            if (p[i] != -1) take += dp[p[i]];

            dp[i] = Math.max(dp[i - 1], take);
        }

        // 4. Reconstruct solution
        List<Integer> result = new ArrayList<>();
        int i = n - 1;

        while (i >= 0) {
            long take = jobs[i].reward;
            if (p[i] != -1) take += dp[p[i]];

            if (i == 0 || take > dp[i - 1]) {
                result.add(jobs[i].index);
                i = p[i];
            } else {
                i--;
            }
        }

        Collections.reverse(result);

        return new Result(dp[n - 1], result);
    }

    // Find last index where end <= target
    private int binarySearch(long[] ends, long target) {
        int lo = 0, hi = ends.length - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (ends[mid] <= target) {
                ans = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }
}