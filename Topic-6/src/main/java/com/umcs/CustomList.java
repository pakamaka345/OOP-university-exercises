package com.umcs;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


public class CustomList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public T get(int index){
        if(index >= size || index < 0) throw new IndexOutOfBoundsException();

        Node<T> current = head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size(){
        return size;
    }
    @Override
    public boolean add(T t){
        addLast(t);
        return true;
    }
    @Override
    public Stream<T> stream(){
        Stream.Builder<T> builder = Stream.builder();
        for (T i : this){
            builder.accept(i);
        }
        return builder.build();
    }
    @Override
    public Iterator<T> iterator(){
        return new ListIterator();
    }
    public void addLast(T value){
        Node<T> node = new Node<>(value);
        if(tail == null){
            head = tail = node;
        }else{
            tail.next = node;
            tail = node;
        }
        size++;
    }
    public T getLast(){
        return tail == null ? null : tail.value;
    }
    public void addFirst(T value){
        Node<T> node = new Node<>(value);
        if(head == null){
            head = tail = node;
        }else{
            node.next = head;
            head = node;
        }
        size++;
    }
    public T getFirst(){
        return head == null ? null : head.value;
    }
    public T removeFirst(){
        if(head == null){
            tail = null;
            return null;
        }else{
            T temp = head.value;
            head = head.next;
            size--;
            return temp;
        }
    }
    public T removeLast(){
        if(tail == null) {
            return null;
        } else if (head == tail){
            T temp = tail.value;
            head = tail = null;
            size--;
            return temp;
        }else {
            Node<T> current = head;
            while(current.next != tail){
                current = current.next;
            }
            T temp = tail.value;
            tail = current;
            tail.next = null;
            size--;
            return temp;
        }
    }
    public static <T> CustomList<T> filterByClass(CustomList<T> list, Class<?> clas){
        CustomList<T> filteredList = new CustomList<>();
        list.stream()
                .filter(obj -> obj != null && obj.getClass().equals(clas))
                .forEach(filteredList::add);
        return filteredList;
    }
    private class Node<T>{
        T value;
        Node<T> next;
        Node(T value){
            this.value = value;
            this.next = null;
        }
    }
    private class ListIterator implements Iterator<T>{
        private Node<T> current = head;
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public T next() {
            if(current == null) throw new NoSuchElementException();
            T temp = current.value;
            current = current.next;
            return temp;
        }
    }
}
