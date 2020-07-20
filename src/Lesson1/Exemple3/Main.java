package Lesson1.Exemple3;

public class Main {

    public static void main(String[] args) {
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Box<Apple> box1 = new Box<>();
        box1.add(apple1);
        box1.add(apple2);

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Box<Orange> box2 = new Box<>();
        box2.add(orange1);
        box2.add(orange2);

        System.out.println(box1.compare(box2));

        Box<Apple> box3 = new Box<>();
        System.out.println(box1.size());
        System.out.println(box3.size());
        box1.sendFruitToBox(box3);
        System.out.println(box1.size());
        System.out.println(box3.size());
    }
}
