package model;

public class Square {
    private Square next;
    private Square previous;
    private int id;
    private int arrow;
    private SquareType state;
    private TravelSquare travelSquare;
    public Square(int id, int arrow){
        this.id = id;
        this.arrow = arrow;
        this.state = SquareType.FREE;
    }
    public int getId(){
        return id;
    }
    public int getArrow(){
        return arrow;
    }
    public Square getNext(){
        return next;
    }
    public Square getPrevious(){
        return previous;
    }
    public SquareType getState() {
        return state;
    }
    public void setState(SquareType aState){
        state = aState;
    }
    public void setPrevious(Square square){
        previous = square;
    }
    public void setNext(Square square){
        next = square;
    }
    public TravelSquare getTravel(){
        return travelSquare;
    }
    public void setTravel(TravelSquare aObject){
        travelSquare = aObject;
    }

}
