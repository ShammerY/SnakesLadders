import model.*;

import java.util.Scanner;
public class Main {
    private Scanner reader;
    private Game game;
    public Main(){
        reader =  new Scanner(System.in);
        game = new Game();
    }
    public static void main(String[] args){
        Main main = new Main();
        main.startProgram();
    }
    private void print(Object text){System.out.println(text);}
    public void startProgram(){
        print(showStartMenu());
        executeStartOption();
    }
    public void executeStartOption(){
        switch(reader.next()){
            case "1":
                print(createGame());
                playGame();
                break;
            case "2":
                print("\n See you Next Time");
                System.exit(0);
                break;
            default:
                print("\n INVALID OPTION ");
                startProgram();
        }
    }
    public String createGame(){
       String[] names = {"ARROWS","COLUMNS","LADDERS","SNAKES"};
       int[] numbers = {0,0,0,0};
       for(int i = 0; i<numbers.length; i++){
           print("\n Enter number of "+names[i]+" :");
           if(reader.hasNextInt()){
               numbers[i] = reader.nextInt();
           }else{reader.next();print("\n INVAlID AMOUNT");startProgram();}
       }
       game.createGame(numbers[0],numbers[1],numbers[2],numbers[3]);
       return "\n The Game Starts!!";
    }
    public void playGame(){
        if(game.validateGameEnd(1, true, game.getCurrentTurn())){
            endGame();
        }
        if(game.validatePlayerTurn()){
            game.setNextTurn();
            playGame();
        }
        playTurn();
    }
    public void playTurn(){
        print(game.showBoard(1,game.getHead(),""));
        print(turnMenu());
        executeTurn();
    }
    public void executeTurn(){
        switch(reader.next()){
            case "1":
                print("\n You got a : \n\n ");
                int roll = (int)Math.floor(Math.random()*6+1);
                print(roll);
                print("\n\n Moving Piece...");
                game.movePiece(roll);
                print(game.showBoard(1,game.getHead(),""));
                game.setNextTurn();
                playGame();
            case "2":
                print(game.showSnakesAndLadders(1,game.getHead(),""));
                playTurn();
            default :
                print("\n Invalid Option");
                playTurn();
        }
    }
    public void endGame(){
        print("\n Game Ended\n");
        print(" WINNER : "+game.getWinner().getId()+ " !!!");
        startProgram();
    }

    public String showStartMenu(){
        return  "\n------------------------------------\n"+
                "          SNAKES AND LADDERS          \n"+
                "------------------------------------\n"+
                "(1) Start Game \n"+
                "(2) Exit Game ";
    }
    private String turnMenu(){
        return  "\n PLAYER'S < "+game.getCurrentTurn().getId()+" > TURN \n"+
                "\n (1) ROLL DICE "+
                "\n (2) SHOW SNAKES AND LADDERS ";
    }
}