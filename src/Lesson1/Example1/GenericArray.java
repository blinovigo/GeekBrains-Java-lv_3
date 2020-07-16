package Lesson1.Example1;

public class GenericArray<T> {
    private T[] obj;

    public GenericArray(T... obj) {
        this.obj = obj;
    }

    public void changePositionArray(int one_element_position, int two_element_position){
        if(one_element_position < obj.length && two_element_position < obj.length) {
            T temp = obj[one_element_position];
            obj[one_element_position] = obj[two_element_position];
            obj[two_element_position] = temp;
        } else {
            System.err.println("Change position don't exist");
        }
    }

    public void showElements(){
        for (T t : obj) {
            System.out.print(t + " ");
        }
        System.out.println("\n");
    }
}
