import java.util.*;
import java.time.LocaDate;

public class RefundGenerator{

    public static final Map<String, Integer> METHOD_PRIORITY = new HashMap<>();
    static{
        METHOD_PRIORITY.put("Credit", 0);
        METHOD_PRIORITY.put("Creidt_Card", 1);
        METHOD_PRIORITY.put("PayPal", 2);
    }

    public static class Payment{

        int id;
        double amount;
        String method;
        LocalDate date;

        public Payment(int id, double amount, String method, String date){
            this.id = id;
            this.amount = amount;
            this.method = method;
            this.date = LocaDate.parse(date);
        }
    }

    public static class Refund{
        int paymentId;
        double refundAmount;

        public Refund(int paymentId, double refundAmount){
            this.paymentId = paymentId;
            this.refundAmount = refundAmount;
        }

        @Override
        public String toString() {
            return "{payment_id: " + paymentId + ", refund_amount: " + refundAmount + "}";
        }
    }

    // Support for PART-2 : tracks how much has already been refunded per payment
    public static class PaymentState{
        Payment payment;
        double refundedsoFar;

        public PaymentState(Payment payment){
            this.payment = payment;
            refundedsoFar = 0;
        }

        public double remaining(){
            payment.amount - refundedsoFar;
        }
    }

    // Part 2: holds state across multiple refund requests
    public static class BookingRefundManager{

        Map<Integer, PaymentState> paymentStates = new LinkedHashMap<>();
        public BookingRefundManager(List<Payment> payments) {
            for (Payment p : payments) {
                paymentStates.put(p.id, new PaymentState(p));
            }
        }
        public List<Refund> generateRefunds(double refundAmount) {

            // Step 1: Validate against remaining refundable amount
            double totalRemaining = 0;
            for (PaymentState ps : paymentStates.values()) {
                totalRemaining += ps.remaining();
            }
            if (refundAmount > totalRemaining) {
                throw new IllegalArgumentException(
                    "Refund amount " + refundAmount + " exceeds remaining refundable amount " + totalRemaining
                );
            }

            // Step 2: Sort by priority and date (same as Part 1)
            List<PaymentState> sorted = new ArrayList<>(paymentStates.values());
            Collections.sort(sorted, (ps1, ps2) -> {
                int methodCompare = METHOD_PRIORITY.getOrDefault(ps1.payment.method, 99)
                                  - METHOD_PRIORITY.getOrDefault(ps2.payment.method, 99);
                if (methodCompare != 0) return methodCompare;
                return ps2.payment.date.compareTo(ps1.payment.date);
            });

            // Step 3: Greedy allocation on REMAINING amount (not original)
            List<Refund> refunds = new ArrayList<>();
            double remaining = refundAmount;

            for (PaymentState ps : sorted) {
                if (remaining <= 0) break;
                if (ps.remaining() <= 0) continue;   // skip fully refunded payments

                double refund = Math.min(ps.remaining(), remaining);
                refunds.add(new Refund(ps.payment.id, refund));

                ps.refundedSoFar += refund;           // ← update state for next request
                remaining -= refund;
            }

            return refunds;
        }
    }

    // Below is for PART 1

    public static List<Refund> generateRefunds(List<Payment> payments, double refundAmount){
        double totalPayments = 0;
        for(Payment p : payments){
            totalPayments += p.amount;
        }
        if(refundAmount > totalPayments){
            throw new IllegalArgumentException(
                "Refund amount " + refundAmount + " exceeds total payments " + totalPayments
            );
        }

        List<Payment>sorted = new ArrayList<>(payments);
        Collections.sort(sorted, (p1, p2) -> {
            int methodCompare = METHOD_PRIORITY.getOrDefault(p1.method, 99)
                              - METHOD_PRIORITY.getOrDefault(p2.method, 99);
            if(methodCompare != 0) return methodCompare;
            return p2.date.compareTo(date1);
        });

        List<Refund> refunds = new ArrayList<>();
        double remaining = refundAmount;
        for(Payment payment : sorted){
            if(remaining <= 0) break;
            double refund = Math.min(payment.amount, remaining);
            refunds.add(new Refund(payment.id, refund));
            remaining -= refund;
        }
    return refunds;
    }
}