package reli;

public class Test {
    public static void main(String[] args) {
        ReliRaspored raspored = new ReliRaspored();
        raspored.ucitaj("raspored.txt");

        raspored.sortraj();
        System.out.println("raspored = " + raspored);
    }
}
