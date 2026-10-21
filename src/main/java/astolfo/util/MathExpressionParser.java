package astolfo.util;

public final class MathExpressionParser {
   public int pos;
   public int ch;
   public final String expression;

   public MathExpressionParser(String string) {
      this.expression = string;
      this.pos = -1;
   }

   public void nextChar() {
      this.ch = ++this.pos < this.expression.length() ? this.expression.charAt(this.pos) : -1;
   }

   public boolean eat(int i) {
      while (this.ch == 32) {
         this.nextChar();
      }

      if (this.ch == i) {
         this.nextChar();
         return true;
      } else {
         return false;
      }
   }

   public double parse() {
      this.nextChar();
      double d0 = this.parseExpression();
      if (this.pos < this.expression.length()) {
         throw new RuntimeException("Unexpected: " + (char)this.ch);
      } else {
         return d0;
      }
   }

   public double parseExpression() {
      double d0 = this.parseTerm();

      while (true) {
         while (!this.eat(43)) {
            if (!this.eat(45)) {
               return d0;
            }

            d0 -= this.parseTerm();
         }

         d0 += this.parseTerm();
      }
   }

   public double parseTerm() {
      double d0 = this.parseFactor();

      while (true) {
         while (!this.eat(42)) {
            if (!this.eat(47)) {
               return d0;
            }

            d0 /= this.parseFactor();
         }

         d0 *= this.parseFactor();
      }
   }

   public double parseFactor() {
      boolean flag = this.eat(43);
      if (flag) {
         return this.parseFactor();
      } else {
         boolean flag1 = this.eat(45);
         if (flag1) {
            return -this.parseFactor();
         } else {
            int i = this.pos;
            boolean flag2 = this.eat(40);
            double d0;
            if (flag2) {
               d0 = this.parseExpression();
               this.eat(41);
            } else {
               label96: {
                  int j = this.ch;
                  if (j < 48 || this.ch > 57) {
                     int k = this.ch;
                     if (k != 46) {
                        int l = this.ch;
                        if (l < 97) {
                           throw new RuntimeException("Unexpected: " + (char)this.ch);
                        }

                        int i1 = this.ch;
                        if (i1 > 122) {
                           throw new RuntimeException("Unexpected: " + (char)this.ch);
                        }

                        while (this.ch >= 97 && this.ch <= 122) {
                           this.nextChar();
                        }

                        String s = this.expression.substring(i, this.pos);
                        d0 = this.parseFactor();
                        int b0 = -1;
                        int j1 = s.hashCode();
                        switch (j1) {
                           case 98695:
                              if (s.equals("cos")) {
                                 b0 = 2;
                              }
                              break;
                           case 113880:
                              if (s.equals("sin")) {
                                 b0 = 1;
                              }
                              break;
                           case 114593:
                              if (s.equals("tan")) {
                                 b0 = 3;
                              }
                              break;
                           case 3538208:
                              if (s.equals("sqrt")) {
                                 b0 = 0;
                              }
                        }

                        switch (b0) {
                           case 0:
                              d0 = Math.sqrt(d0);
                              break label96;
                           case 1:
                              d0 = Math.sin(Math.toRadians(d0));
                              break label96;
                           case 2:
                              d0 = Math.cos(Math.toRadians(d0));
                              break label96;
                           case 3:
                              d0 = Math.tan(Math.toRadians(d0));
                              break label96;
                           default:
                              throw new RuntimeException("Unknown function: " + s);
                        }
                     }
                  }

                  while (this.ch >= 48 && this.ch <= 57 || this.ch == 46) {
                     this.nextChar();
                  }

                  d0 = Double.parseDouble(this.expression.substring(i, this.pos));
               }
            }

            if (this.eat(94)) {
               d0 = Math.pow(d0, this.parseFactor());
            }

            return d0;
         }
      }
   }
}
