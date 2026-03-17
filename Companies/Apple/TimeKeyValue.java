class TimeStamp{

HashMap<String, List<Pair>>map;

    class Pair{
        int timeStamp;
        String value;

        Pair(int t, String v){
            timeStamp = t;
            value = v;
        }
    }

    public TimeStamp(){
        map = new HashMap<>();
    }

    public void set(String key, String value, int timeStamp){
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new Pair(timeStamp, value));
    }

    public String get(String key, int timeStamp){
        if(!map.containsKey(key)){
            return "";
        }
        List<Pair>list = map.get(key);
        int left = 0, right = list.size()-1;
        String ans = "";
        while(left <= right){
            int mid = left+(right-left)/2;
            if(list.get(mid).timeStamp <= timeStamp){
                left = mid+1;
                ans = list.get(mid).value;
            }else{
                right = mid-1;
            }
        }
    return ans;
    }
}