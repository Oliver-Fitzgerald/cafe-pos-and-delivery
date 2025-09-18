public class OrderIds {

    private static long counter = 0;
    public static long next() {
       counter++;
       return counter; 
    }
}
