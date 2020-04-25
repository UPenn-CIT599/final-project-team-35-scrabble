package scrabble.events;

public interface GameEventListener {
    public void onClickBoardEvent(BoardEvent event);
    public void onLetterTrayEvent(LetterEvent event);
}