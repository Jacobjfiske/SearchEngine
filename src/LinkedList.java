public class LinkedList<E> {//builds own linked list adds at either head or tail

    public static void main(String[] args) {
        LinkedList<String> strings = new LinkedList();

        strings.addAtTail("hello");
        strings.addAtTail("world");
        strings.addAtHead("sudo");

        for (int i = 0; i < strings.getCount(); i++){
            System.out.println(strings.get(i));
        }

        LinkedList<Integer> ints = new LinkedList();
        for (int i = 0; i < 500; i++){
            ints.addAtHead(i);
        }
        for (int i = 0; i < ints.getCount(); i++){
            System.out.print(ints.get(i) + " ");
        }
        for (int i = ints.getCount()-1; i >= 0; i--){
            System.out.print(ints.get(i) + "");
        }
    }

    private static class Node<E>{
        public E data = null;
        public Node<E> next = null;
        public Node<E> prev = null;
    }

    private Node<E> head;
    private Node<E> tail;
    private int count;

    public LinkedList(){//creates LL with head,tail,count
        head = null;
        tail = null;
        count = 0;
    }

    public void addAtTail(E item){//adds new node at tail
        if (count == 0) {
            head = tail = new Node();
            head.data = item;
            count++;
        }
        else {
            Node<E> added = new Node();
            added.data = item;

            tail.next = added;
            added.prev = tail;

            tail = added;
            count++;
        }
    }

    public void addAtHead(E item) {//adds new node at head
        if (count == 0) {
            head = tail = new Node();
            head.data = item;
            count++;
        } else {
            Node<E> added = new Node();
            added.data = item;

            head.prev = added;
            added.next = head;

            head = added;
            count++;
        }
    }

    public E get(int index) {//gives index for LL
        if (index < 0){
            return null;
        }
        if (index >= count) {
            return null;
        }
        Node<E> trace = (index < count/2) ? head : tail;
        int at  = (index < count/2) ? 0 : count-1;
        int dir = (index < count/2) ? +1 : -1;

        while (at != index) {
            trace = (dir > 0) ? trace.next : trace.prev;
            at += dir;
        }
        return trace.data;

    }

    public int getCount() {//gets size of linked list
        return count;
    }

    public E removeHead() {//removes head
        if (count == 0) {
            return null;
        }
        Node<E> removed = head;
        if (count == 1) {
            head = tail = null;
        }
        else {
            head = head.next;
            removed.next = null;
        }

        count--;
        return removed.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
