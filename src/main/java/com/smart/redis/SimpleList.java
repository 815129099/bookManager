package com.smart.redis;

public class SimpleList<T> {
    private Object[] elementData ;
    private int size=0;

    public int size(){
        return size;
    }

    public SimpleList(){
if(size==0){
    elementData = new Object[10];
}
    }

    public boolean isEmpty(){
        return (elementData.length==0)?false:true;
    }

    public boolean add(T e){
        if(size==0){
            elementData[0] = e;
        }
            elementData[size++]=e;
            return true;
    }

    public boolean remove(Object o){

        int index = 0;
        for(int i=0;i<elementData.length;i++){
            if(elementData[i]==o){
                index = i;
            }
        }

    for(int i=index;i<size;i++){
        elementData[i] = elementData[i+1];
    }
    elementData[size-1]=null;

    return true;

    }

    public T get(int index){
        return (T)elementData[index];
    }
}
