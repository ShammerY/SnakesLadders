package model;

public class Snake extends TravelSquare {
    private String id;
    public Snake(Square head, Square tail, String aId){
        super(head,tail);
        id = aId;
    }
    public String getId(){
        return this.id;
    }
}
