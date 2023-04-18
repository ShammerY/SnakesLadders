package model;

public class Player {
    private char id;
    private int pos;
    private double score;
    private Player next;
    private Player previous;
    public Player(char id){
        this.id = id;
        score = 0;
        pos = 1;
    }
    public int getPos(){
        return pos;
    }
    public void setPos(int aPos){
        pos = aPos;
    }
    public char getId(){
        return id;
    }
    public double getScore(){
        return score;
    }
    public void setScore(double aScore){
        score = aScore;
    }
    public Player getNext(){
        return next;
    }
    public Player getPrevious(){
        return previous;
    }
    public void setPrevious(Player player){
        previous = player;
    }
    public void setNext(Player player){
        next = player;
    }
}
