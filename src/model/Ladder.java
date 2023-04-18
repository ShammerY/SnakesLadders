package model;
public class Ladder extends TravelSquare{
    private int id;
    public Ladder(Square head, Square tail, int aId){
        super(head,tail);
        this.id = aId;
    }
    public int getId(){
        return this.id;
    }
}
