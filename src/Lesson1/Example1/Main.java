package Lesson1.Example1;

public class Main {

    public static void main(String[] args) {
        GenericArray<Integer> genericArray = new GenericArray<>(0, 1, 3, 5, 7, 9);
        genericArray.showElements();
        genericArray.changePositionArray(1,8);
        genericArray.showElements();
    }


}
