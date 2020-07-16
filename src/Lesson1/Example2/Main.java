package Lesson1.Example2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GenericArray<String> genericArray = new GenericArray<>("Aa","Bb","Dd","Ee","Ff");
        genericArray.showElements();
        ArrayList<?> arrayList = genericArray.toArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i) + " ");
        }
    }


}
