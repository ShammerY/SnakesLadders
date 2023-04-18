package model;
public class TravelSquare {
    private Square head;
    private Square tail;
    public TravelSquare(Square aHead, Square aTail){
        this.head = aHead;
        this.tail = aTail;
    }
    public Square getHead(){
        return head;
    }
    public Square getTail(){
        return tail;
    }
}
