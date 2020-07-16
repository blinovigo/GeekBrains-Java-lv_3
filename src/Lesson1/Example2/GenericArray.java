package Lesson1.Example2;

import java.util.ArrayList;
import java.util.Arrays;

public class GenericArray<T> {
    private T[] obj;

    public GenericArray(T... obj) {
        this.obj = obj;
    }

    public ArrayList<T> toArrayList(){
        return new ArrayList<>(Arrays.asList(obj));
    }

    public void showElements(){
        for (T t : obj) {
            System.out.print(t + " ");
        }
        System.out.println("\n");
    }
}
