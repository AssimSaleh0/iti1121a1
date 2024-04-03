public class ITIStringBuffer {

    private SinglyLinkedList<String> list;

    public ITIStringBuffer() {
        this.list = new SinglyLinkedList<>();
    }

    public void append(String s) {
        if (s == null) throw new NullPointerException("Cannot append null String");
        list.add(s);
    }

    public String toString() {
        if (list.size() == 1) {
            return list.get(0);
        }
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append(str);
        }
        return result.toString();
    }

}