import java.util.*;

class ChatBackend {

    static class Group {
        String groupId;
        Set<String> members;
        List<String> messages;
        int count;

        Group(String groupId, List<String> members) {
            this.groupId = groupId;
            this.members = new HashSet<>(members);
            this.messages = new ArrayList<>();
            this.count = 0;
        }
    }

    private Map<String, Group> groups;

    private TreeSet<Group> sortedGroups;

    public ChatBackend() {
        groups = new HashMap<>();

        sortedGroups = new TreeSet<>((a, b) -> {
            if (a.count != b.count) {
                return b.count - a.count; // higher count first
            }
            return a.groupId.compareTo(b.groupId);
        });
    }

    // CREATE_GROUP O(log n)
    public void createGroup(String groupId, List<String> members) {
        if (groups.containsKey(groupId)) return;

        Group g = new Group(groupId, members);
        groups.put(groupId, g);
        sortedGroups.add(g);
    }

    // SEND O(log n)
    public void send(String groupId, String userId, String text) {
        if (!groups.containsKey(groupId)) return;

        Group g = groups.get(groupId);
        if (!g.members.contains(userId)) return;

        // remove before update ⚠️
        sortedGroups.remove(g);

        g.messages.add(text);
        g.count++;

        // reinsert after update
        sortedGroups.add(g);
    }

    // GET_RECENT O(k)
    public List<String> getRecent(String groupId, int k) {
        if (!groups.containsKey(groupId)) return new ArrayList<>();

        List<String> msgs = groups.get(groupId).messages;
        int start = Math.max(0, msgs.size() - k);
        return new ArrayList<>(msgs.subList(start, msgs.size()));
    }

    // TOP_N O(n)
    public List<String> topN(int n) {
        List<String> result = new ArrayList<>();

        int count = 0;
        for (Group g : sortedGroups) {
            if (count == n) break;
            result.add(g.groupId);
            count++;
        }

        return result;
    }
}