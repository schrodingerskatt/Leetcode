import java.util.*;

/**
 * Transaction Ledger System
 */
public class TransactionLedger {

    // ===================== MODELS =====================

    static class Payment {
        String id;
        String userId;
        long amount;
        long remaining;
        long timestamp;

        Payment(String id, String userId, long amount, long timestamp) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
            this.remaining = amount;
            this.timestamp = timestamp;
        }
    }

    static class Refund {
        String id;
        String userId;
        long amount;
        long timestamp;
        String paymentIdRef; // nullable

        Refund(String id, String userId, long amount, long timestamp, String paymentIdRef) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
            this.timestamp = timestamp;
            this.paymentIdRef = paymentIdRef;
        }
    }

    static class Allocation {
        String paymentId;
        long amount;

        Allocation(String paymentId, long amount) {
            this.paymentId = paymentId;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "(" + paymentId + ", " + amount + ")";
        }
    }

    // ===================== STORAGE =====================

    private final Map<String, Payment> paymentsById = new HashMap<>();
    private final Map<String, NavigableSet<Payment>> userPayments = new HashMap<>();
    private final Map<String, Long> userBalance = new HashMap<>();

    private final Comparator<Payment> comparator;

    // ===================== CONSTRUCTOR =====================

    public TransactionLedger(Comparator<Payment> comparator) {
        this.comparator = comparator;
    }

    // ===================== API =====================

    //  Add Payment
    public void addPayment(Payment p) {
        paymentsById.put(p.id, p);

        userPayments
            .computeIfAbsent(p.userId, k -> new TreeSet<>(comparator))
            .add(p);

        userBalance.put(p.userId,
            userBalance.getOrDefault(p.userId, 0L) + p.amount);
    }

    // Add Refund
    public List<Allocation> addRefund(Refund r) {
        List<Allocation> allocations = new ArrayList<>();
        long remainingRefund = r.amount;

        //  Case 1: specific payment reference
        if (r.paymentIdRef != null) {
            Payment p = paymentsById.get(r.paymentIdRef);

            if (p == null || p.remaining == 0) {
                return allocations; // nothing to allocate
            }

            long used = Math.min(p.remaining, remainingRefund);
            p.remaining -= used;
            remainingRefund -= used;

            allocations.add(new Allocation(p.id, used));

            if (p.remaining == 0) {
                userPayments.get(p.userId).remove(p);
            }
        }

        //  Case 2: dynamic allocation
        else {
            NavigableSet<Payment> set = userPayments.get(r.userId);
            if (set == null || set.isEmpty()) {
                return allocations;
            }

            Iterator<Payment> it = set.iterator();
            List<Payment> toRemove = new ArrayList<>();

            while (it.hasNext() && remainingRefund > 0) {
                Payment p = it.next();

                long used = Math.min(p.remaining, remainingRefund);
                p.remaining -= used;
                remainingRefund -= used;

                allocations.add(new Allocation(p.id, used));

                if (p.remaining == 0) {
                    toRemove.add(p);
                }
            }

            // remove exhausted payments
            for (Payment p : toRemove) {
                set.remove(p);
            }
        }

        //  update balance (only actual refunded amount)
        long refunded = r.amount - remainingRefund;

        userBalance.put(r.userId,
            userBalance.getOrDefault(r.userId, 0L) - refunded);

        return allocations;
    }

    // Get Balance
    public long getUserBalance(String userId) {
        return userBalance.getOrDefault(userId, 0L);
    }

    // ===================== SAMPLE USAGE =====================

    public static void main(String[] args) {

        // Example: newest payment first
        Comparator<Payment> recencyFirst =
            (a, b) -> Long.compare(b.timestamp, a.timestamp);

        TransactionLedger ledger = new TransactionLedger(recencyFirst);

        // Add payments
        ledger.addPayment(new Payment("p1", "u1", 100, 1));
        ledger.addPayment(new Payment("p2", "u1", 200, 2));
        ledger.addPayment(new Payment("p3", "u1", 50, 3));

        // Refund without reference (uses comparator)
        Refund r1 = new Refund("r1", "u1", 180, 4, null);
        System.out.println("Refund1 allocations: " + ledger.addRefund(r1));

        // Refund with reference
        Refund r2 = new Refund("r2", "u1", 50, 5, "p1");
        System.out.println("Refund2 allocations: " + ledger.addRefund(r2));

        // Balance
        System.out.println("Final Balance: " + ledger.getUserBalance("u1"));
    }
}