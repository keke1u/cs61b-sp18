public class HelloWorld {
   public static void main(String[] args) {
      int x = 0;
      int cu = 0;
      while (x < 10) {
         cu = cu + x;
         System.out.print(cu + " ");
         x = x + 1;
      }
      System.out.println();
   }
}