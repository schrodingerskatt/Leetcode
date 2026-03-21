import java.util.*;

class ListNode{

    int val;
    ListNode next;

    ListNode(int val){
        this.val = val;
        this.next = next;
    }
}
public class OverlappingListsWithCycles {

public static boolean overlap(ListNode headA, ListNode headB){

    ListNode cycleA = getCycleStart(headA);
    ListNode cycleB = getCycleStart(headB);


    // Case 1: both acyclic
    if(cycleA == null && cycleB == null){
        return noCycleOverlapp(headA, headB);
    }

    // Case 2: one cyclic, one not
    if((cycleA == null) != (cycleB == null)){
        return false;
    }

    // Case 3: both cyclic
    if(cycleA == cycleB){
        return true;
    }

    /*
    cycleA != null && cycleB != null   // both lists have cycles and
    cycleA != cycleB                   // cycle starts are different
    This condition is covered by below code
    */

    ListNode temp = cycleA.next;
    while(temp != cycleA){
        if(temp == cycleB) return true;
        temp = temp.next;
    }
    return false;
}

// Detect cycle and return cycle start
private static ListNode getCycleStart(ListNode head) {

    ListNode slow = head, fast = head;

    while(fast != null && fast.next != null){
        slow = slow.next;
        fast = fast.next.next;

        if(slow == fast){
            slow = head;
            while(slow != fast){
                slow = slow.next;
                fast = fast.next;
            }
        return slow;
        }
    }
return null;
}

public static boolean noCycleOverlapp(ListNode a, ListNode b){

    int lenA = getlength(a);
    int lenB = getlength(b);

    if(lenA > lenB){
        a = advance(a, lenA-lenB);
    }else{
        b = advance(b, lenB-lenA);
    }
    while(a != null && b != null){
        if(a == b) return true;
        a = a.next;
        b = b.next;
    }
    return false;
}

private static int getlength(ListNode head){
    int len = 0;
    while(head != null){
        head = head.next;
        len++;
    }
    return len;
}

private static ListNode advance(ListNode head, int steps) {
    while (steps-- > 0) {
        head = head.next;
    }
    return head;
}

public static void main(String[] args) {
        Map<Integer, Integer> map1 = new HashMap<>();
        map1.put(1, 2);
        map1.put(2, 3);
        map1.put(3, null);
        map1.put(4, 5);
        map1.put(5, 3);

        ListNode headA = buildList(map1, 1);
        ListNode headB = buildList(map1, 4);

        System.out.println(overlap(headA, headB)); // true


        Map<Integer, Integer> map2 = new HashMap<>();
        map2.put(1, 2);
        map2.put(2, 3);
        map2.put(3, 2);
        map2.put(4, 5);
        map2.put(5, 6);
        map2.put(6, 5);

        headA = buildList(map2, 1);
        headB = buildList(map2, 4);

        System.out.println(overlap(headA, headB)); // false
    }
}