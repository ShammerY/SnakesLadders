package model;

public class Game {
    private Square head;
    private Square tail;
    private Player headPlayer;
    private Player currentTurn;
    private int columns;
    private int arrows;
    private Player winner;
    public Square getHead(){
        return head;
    }
    public Player getCurrentTurn(){return currentTurn;}
    public Player getWinner(){return winner;}
    public void createGame(int aArrows, int aColumns, int ladders, int snakes){
        columns = aColumns;
        arrows = aArrows;
        setSquares(1);
        setLadderHead(1,ladders);
        setSnakeHead(1,snakes);
        setPlayers();
    }
    public void setSquares(int current){
        Square square = new Square(current,(int)Math.ceil((double)current/(double)columns));
        if(head==null){
            head = square;
            tail = square;
            setSquares(current+1);
        }else if(arrows*columns == current){
            tail.setNext(square);
            square.setPrevious(tail);
            tail = square;
        }else{
            tail.setNext(square);
            square.setPrevious(tail);
            tail = square;
            setSquares(current+1);
        }
    }
    private void setLadderHead(int current, int ladders){
        if(current > ladders){
            return;
        }
        int squarePos = (int)Math.floor((Math.random()*(arrows*columns))+1);
        boolean flag = validateSnakeTail(squarePos, head);
        if(flag == true){
            setLadderHead(current,ladders);
        }else{
            Square square = getSquare(squarePos, head);
            square.setState(SquareType.LADDER_HEAD);
            setLadderTail(square, current);
            setLadderHead(current+1, ladders);
        }

    }
    private boolean validateSnakeTail(int squarePos, Square current){
        Square square = getSquare(squarePos, head);
        if(square.getId() == 1 || square.getId() == arrows*columns || square.getArrow() == arrows){
            return true;
        }
        if(!square.getState().equals(SquareType.FREE)){
            return true;
        }
        return false;
    }
    private void setLadderTail(Square squareHead, int letter){
        int num = (arrows*columns)-squareHead.getId();
        int squarePos = (int)Math.floor((Math.random()*num)+1+squareHead.getId());
        boolean flag = validateSnakeHead(squarePos, head);
        Square squareTail = getSquare(squarePos, head);
        if(flag == true || squareTail.getArrow() == squareHead.getArrow()){
            setLadderTail(squareHead, letter);
        }else{
            squareTail.setState(SquareType.LADDER_TAIL);
            Ladder ladder = new Ladder(squareHead, squareTail, letter);
            squareHead.setTravel(ladder);
            squareTail.setTravel(ladder);
        }

    }
    private Square getSquare(int pos, Square current){
        if(current.getId()==pos){
            return current;
        }
        if(current.getNext() == null){
            return null;
        }
        return  getSquare(pos, current.getNext());
    }

    private void setSnakeHead(int current, int snakes){
        String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        if(current > snakes){
            return;
        }
        int squarePos = (int)Math.floor((Math.random()*(arrows*columns))+1);
        boolean flag = validateSnakeHead(squarePos, head);
        if(flag == true){
            setSnakeHead(current,snakes);
        }else{
            Square square = getSquare(squarePos, head);
            square.setState(SquareType.SNAKE_HEAD);
            setSnakeTail(square, letters[current-1]);
            setSnakeHead(current+1, snakes);
        }

    }
    private boolean validateSnakeHead(int squarePos, Square current){
        Square square = getSquare(squarePos, head);
        if(square.getId() == 1 || square.getId() == arrows*columns || square.getArrow() == 1){
            return true;
        }
        if(!square.getState().equals(SquareType.FREE)){
            return true;
        }
        return false;
    }
    private void setSnakeTail(Square squareHead, String letter){
        int squarePos = (int)Math.floor((Math.random()*squareHead.getId())+1);
        boolean flag = validateSnakeTail(squarePos, head);
        Square squareTail = getSquare(squarePos, head);
        if(flag == true || squareTail.getArrow() == squareHead.getArrow()){
            setSnakeTail(squareHead, letter);
        }else{
            squareTail.setState(SquareType.SNAKE_TAIL);
            Snake snake = new Snake(squareHead, squareTail, letter);
            squareHead.setTravel(snake);
            squareTail.setTravel(snake);
        }

    }
    private void setPlayers(){
        headPlayer = new Player('#');
        headPlayer.setNext(new Player('&'));
        headPlayer.getNext().setPrevious(headPlayer);
        headPlayer.setPrevious(new Player('$'));
        headPlayer.getPrevious().setNext(headPlayer);
        headPlayer.getNext().setNext(headPlayer.getPrevious());
        headPlayer.getPrevious().setPrevious(headPlayer.getNext());
        currentTurn = headPlayer;
    }

    public Square squareStep(Square square, int steps, int current){
        if(steps == current){
            return square;
        }
        return squareStep(square.getNext(),steps,current+1);
    }
    public String showBoard(int current, Square square, String copy){
        String list1 = "\n"+makeList(current, square, false)+copy;
        if(current == arrows){
            return list1;
        }
        copy = list1;
        return showBoard(current+1, squareStep(square,columns,0),copy);
    }
    public String makeList(int number, Square square, boolean flag){
        if((number % 2)==0){
            if(square == null){
                return "";
            }
            if(square.getPrevious().getArrow() == number-1 && flag==false){
                square = squareStep(square,(columns), 1);
                flag = true;
            }
            if(square.getPrevious().getArrow() != number && flag==true){
                if(currentTurn.getPos() == square.getId()){
                    return "["+square.getId()+currentTurn.getId()+"]";
                }else{
                    return "["+square.getId()+"]";
                }
            }
            if(currentTurn.getPos() == square.getId()){
                return "["+square.getId()+currentTurn.getId()+"]"+makeList(number, square.getPrevious(), flag);
            }
            return "["+square.getId()+"]"+makeList(number, square.getPrevious(), flag);
        }else{
            if(square.getNext() == null){
                if(currentTurn.getPos() == square.getId()){
                    return "["+square.getId()+currentTurn.getId()+"]";
                }else{
                    return "["+square.getId()+"]";
                }
            }
            if(square.getNext().getArrow() != number){
                if(currentTurn.getPos() == square.getId()){
                    return "["+square.getId()+currentTurn.getId()+"]";
                }else{
                    return "["+square.getId()+"]";
                }
            }
            if(currentTurn.getPos() == square.getId()){
                return "["+square.getId()+currentTurn.getId()+"]"+makeList(number, square.getNext(), flag);
            }else{
                return "["+square.getId()+"]"+makeList(number, square.getNext(), flag);
            }
        }
    }
    public void setNextTurn(){
        currentTurn = currentTurn.getNext();
    }
    public boolean validatePlayerTurn(){
        if(currentTurn.getPos()==tail.getId()){
            return true;
        }
        return false;
    }
    public boolean validateGameEnd(int count, boolean flag, Player current){
        if(count > 3){
            return flag;
        }
        if(current.getPos() != tail.getId()){
            return false;
        }else{
            flag = true;
        }
        return validateGameEnd(count+1, flag, current.getNext());
    }
    public void movePiece(int roll){
        int pos = currentTurn.getPos() + roll;
        if(pos > arrows*columns){
            movePiece(roll-1);
            validateWinner(currentTurn);
        }else{
            Square square = getSquare(pos,head);
            currentTurn.setPos(pos);
            if(square.getState() != SquareType.FREE){
                TravelSquare travel = square.getTravel();
                currentTurn.setPos(travel.getTail().getId());
            }
            if(currentTurn.getPos() == tail.getId()){
                validateWinner(currentTurn);
            }
        }
    }
    public void validateWinner(Player player){
        if(winner == null){
            winner = player;
        }
    }
    public String showSnakesAndLadders(int current, Square square, String copy){
        String list1 = "\n"+makeSnakesAndLadders(current, square, false)+copy;
        if(current == arrows){
            return  "Letters : Snakes\n"+
                    "Numbers : Ladders\n\n"+list1;
        }
        copy = list1;
        return showSnakesAndLadders(current+1, squareStep(square,columns,0),copy);
    }
    private Object getSquareTravelId(Square square){
        if(square.getTravel() instanceof Snake){
            Snake snake = (Snake)square.getTravel();
            return snake.getId();
        }else if(square.getTravel() instanceof Ladder){
            Ladder ladder = (Ladder)square.getTravel();
            return ladder.getId();
        }
        return null;
    }
    public String makeSnakesAndLadders(int row, Square current, boolean flag){
        if((row % 2)==0){
            if(current == null){
                return "";
            }
            if(current.getPrevious().getArrow() == row-1 && flag==false){
                current = squareStep(current,(columns), 1);
                flag = true;
            }
            if(current.getPrevious().getArrow() != row && flag==true){
                if(current.getState() != SquareType.FREE){
                    return "["+getSquareTravelId(current)+"]";
                }else{
                    return "[ ]";
                }
            }
            if(current.getState() != SquareType.FREE){
                return "["+getSquareTravelId(current)+"]"+makeSnakesAndLadders(row, current.getPrevious(),flag);
            }else{
                return "[ ]"+makeSnakesAndLadders(row, current.getPrevious(),flag);
            }
        }else{
            if(current.getNext() == null){
                if(current.getState() != SquareType.FREE){
                    return "["+getSquareTravelId(current)+"]";
                }else{
                    return "[ ]";
                }
            }
            if(current.getNext().getArrow() != row){
                if(current.getState() != SquareType.FREE){
                    return "["+getSquareTravelId(current)+"]";
                }else{
                    return "[ ]";
                }
            }
            if(current.getState() != SquareType.FREE){
                return "["+getSquareTravelId(current)+"]"+makeSnakesAndLadders(row, current.getNext(),flag);
            }else{
                return "[ ]"+makeSnakesAndLadders(row, current.getNext(),flag);
            }
        }
    }
}
