package Lesson1.Exemple3;
import java.util.ArrayList;

public class Box<T extends Fruit> extends ArrayList<T> {
    private float weight;

    public float getWeight(){
        for (T obj : this) {
            weight += obj.getWeight();
        }
        return weight;
    }

    public boolean compare(Box<?> otherBox){
        return this.getWeight() == otherBox.getWeight();
    }

    public void sendFruitToBox(Box<T> otherBox){
        if (!this.isEmpty()){
            otherBox.add(this.get(0));
            this.remove(0);
        } else {
            System.err.print("Box is Empty");
        }
    }
}
