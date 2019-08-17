package omok.view;

enum Dol {
   BLACK, WHITE
}

public class OmokDol {
   private int x;
   private int y;
   private Dol color;

   public OmokDol(int x, int y, Dol color) {
      super();
      this.x = x;
      this.y = y;
      this.color = color;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public Dol getColor() {
      return color;
   }

   public void setColor(Dol color) {
      this.color = color;
   }

}