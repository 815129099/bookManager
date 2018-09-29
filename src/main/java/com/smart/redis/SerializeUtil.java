package com.smart.redis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    /**
     *
     * 序列化对象
     */
    public static byte[] serializeObject(Object obj) {
        if(obj == null){
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     *
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unSerializeObject(byte[] bytes) {
        if(bytes == null){
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化为对象
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(bais);
            close(ois);
        }
        return null;
    }

    /**
     *
     * 序列化
     */
    public static byte[] serializeList(List<?> list) {
        if(list==null || list.isEmpty()){
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            for(Object o : list){
                oos.writeObject(o);
            }
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(oos);
            close(baos);
        }
        return bytes;
    }

    /**
     *
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static List<?> unSerializeList(byte[] bytes) {
        if(bytes == null){
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化为对象
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            while (bais.available()>0){
                Object o = (Object)ois.readObject();
                if(o == null){
                    break;
                }
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(bais);
            close(ois);
        }
        return list;
    }

    //关闭IO流对象
    public static void close(Closeable closeable){
        if(closeable != null){
            try{
                closeable.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}