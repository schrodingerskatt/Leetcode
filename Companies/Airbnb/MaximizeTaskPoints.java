import java.util.*;

class Job{

    char id;
    int deadline;
    int profit;

    job(char id, int deadline, int profit){
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing{

    public static void main(String[] args){
        Job[] jobs = {
            new Job('A', 3, 50);
            new Job('B', 3, 40);
            new Job('C', 3, 30);
            new Job('D', 3, 20);
        };
        scheduleJobs(jobs);
    }

    public static void scheduleJobs(Job[] jobs){

        Arrays.sort(jobs, (a, b) -> b.profit-a.profit);
        int maxDeadline = 0;
        for(Job job : jobs){
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }
        char[] result = new char[maxDeadline+1];
        boolean[] slot = new boolean[maxDeadline+1];

        int totalProfit = 0;
        for(Job job : jobs){
            for(int j = job.deadline; j > 0; j--){
                if(!slot[j]){
                    slot[j] = true;
                    result[j] = job.id;
                    totalProfit += job.profit;
                    break;
                }
            }
        }
        for(int i = 1; i <= maxDeadline; i++){
            if(slot[i]){
                System.out.println(result[i]+" ");
            }
        }
        System.out.println("\nMax Profit: " + totalProfit);
    }
}