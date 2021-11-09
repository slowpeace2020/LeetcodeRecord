package edu.study.csye;

class Game{
//  Game(){
//    System.out.println("no parameter");
//  }
  Game(int i){
    System.out.println("Game constructor");
  }
}

class BoardGame extends Game{

  BoardGame(int i) {
    super(i);
    System.out.println("BoardGame constructor");

  }
}

public class Chess extends BoardGame{

  Chess() {
    super(11);
    System.out.println("Chess constructor");
  }

  public static void main(String[] args) {
    Chess chess = new Chess();
  }
}
