import java.util.*;

class Solution{

    static class Payment{
        String paymentId;
        String method;
        String date;
        double amountPaid;

        Payment(String paymentId, String method, String date, double amountPaid){
            this.paymentId = paymentId;
            this.method = method;
            this.date = date;
            this.amountPaid = amountPaid;
        }
    }

    static class Refund{
        String paymentId;
        double amountRefunded;

        Refund(String id, double amountRefunded){
            this.id = id;
            this.amountRefunded = amountRefunded;
        }
    }

    static class PaymentNode{

        String paymentId;
        String method;
        String date;
        double remaining;

        PaymentNode(String id, String method, String date, double remaining){
            this.paymentId = id;
            this.method = method;
            this.date = date;
            this.remaining = remaining;
        }
    }

    static class Allocation{

        String paymentId;
        String method;
        String amount;

        Allocation(String paymentId, String method, String amount){
            this.paymentId = paymentId;
            this.method = method;
            this.amount = amount;
        }
    }

    static class Result{
        List<Allocation>allocations;
        double shortfall;

        Result(List<Allocation>allocations, double shortfall){
            this.allocations = allocations;
            this.shortfall = shortfall;
        }
    }

    public Result allocateRefund(List<Payment> payments, List<Refund> existingRefunds, 
                                 double refundRequest){

    // Aggregate refunds per payment
    Map<String, Double> refundedMap = new HashMap<>();
    for(Refund r : existingRefunds){
        refundedMap.put(r.paymentId, refundedMap.getOrDefault(r.paymentId, 0.0)+r.amountRefunded);
    }

    // Build payment nodes with remaining amount
    Map<String, List<PaymentNode>> methodMap = new HashMap<>();

    for(Payment p : payments){
        double refunded = refundedMap.getOrDefault(p.paymentId, 0.0);
        double remaining = Math.max(0, p.amountPaid-refunded);
        if(remaining <= 0) continue;
        methodMap.computeIfAbsent(p.method, k -> new ArrayList<>())
        .add(new PaymentNode(p.paymentId, p.method, p.date, remaining));
    }

    // 3. Sort each method group by date DESC (most recent first)
    for(List<PaymentNode> list : methodMap.values()){
        list.sort((a, b) -> b.date.compareTo(a.date)); // ISO date works lexicographically
    }

    // 4. Method priority Order
    List<String> priority = Arrays.asList("CREDT", "CREDIT_CARD", "PAYPAL");
    List<Allocation> result = new ArrayList<>();

    double remainingRequest = refundRequest;

    // 5. Greedy allocation
    for(String method : priority){
        if(!methodMap.containsKey(method)) continue;

        for(PaymentNode : methodMap.get(method)){
            if(remainingRequest <= 0) break;

            double allocate = Math.min(node.remaining, remainingRequest);
            result.add(new Allocation(node.paymentId, method, allocate));
            remainingRequest -= allocate;
            node.remaining -= allocate;
        }
        if(remainingRequest <= 0) break;
    }

    return new Result(result, remainingRequest);

    }
}