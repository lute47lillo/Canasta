package edu.up.cs301.canasta;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class CanastaLocalGame extends LocalGame {

    CanastaGameState state;

    public CanastaLocalGame() {
        state = new CanastaGameState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if (state == null) {
            return;
        }

        CanastaGameState stateForPlayer = new CanastaGameState(state);
        p.sendInfo(stateForPlayer);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        if (state.getPlayerTurnID() == playerIdx) {
            return true;
        }
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }





    /**
     * Takes two cards from deck; checks if it is a red three and
     * handles it accordingly
     * @param p (The player the action is from)
     */
    private void drawFromDeck(CanastaPlayer p, CanastaGameState state) {
        p.getHand().add(state.deck.remove(0));
        p.getHand().add(state.deck.remove(0));
        removeRedThree(p,state);
    }

    /**
     * Removes red three from hand and replaces it with something else
     * @param p (The player the action is from)
     */
    private void removeRedThree(CanastaPlayer p, CanastaGameState state) {
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(0).getValue() == 3 && (p.getHand().get(0).getSuit() == 'H' || p.getHand().get(0).getSuit() == 'D')) {
                p.getHand().remove(i);
                p.getHand().add(state.deck.remove(0));
                i = 0;  //resets loop if a red three has been found. Checks if new card is a red three
                p.setScore(p.getScore() + 100);
            }
        }
    }

    /**
     * Helper method to find the number of wild
     * cards in a meld
     * @param cards (The deck of cards to be searched)
     * @param value (Which meld is being considered)
     * @return (Returns the number of wild cards found)
     */
    public int countWildCards(ArrayList<Card> cards, int value) {
        int wildCount = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() != value) {
                wildCount++;
            }
        }
        return wildCount;
    }

    /**
     * Checks if all melds are of three or more cards
     * and more than half of the cards are not a wild cards or not empty
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean checkValidMeld(CanastaPlayer p) {
        if (!((p.getMeldedAce().size() >= 3 && countWildCards(p.getMeldedAce(), 1) <= p.getMeldedAce().size()/2) || p.getMeldedAce().size() == 0)) {
            return false;
        }
        if (!((p.getMelded4().size() >= 3 && countWildCards(p.getMelded4(), 4) <= p.getMelded4().size()/2) || p.getMelded4().size() == 0)) {
            return false;
        }
        if (!((p.getMelded5().size() >= 3 && countWildCards(p.getMelded5(), 5) <= p.getMelded5().size()/2) || p.getMelded5().size() == 0)) {
            return false;
        }
        if (!((p.getMelded6().size() >= 3 && countWildCards(p.getMelded6(), 6) <= p.getMelded6().size()/2) || p.getMelded6().size() == 0)) {
            return false;
        }
        if (!((p.getMelded7().size() >= 3 && countWildCards(p.getMelded7(), 7) <= p.getMelded7().size()/2) || p.getMelded7().size() == 0)) {
            return false;
        }
        if (!((p.getMelded8().size() >= 3 && countWildCards(p.getMelded8(), 8) <= p.getMelded8().size()/2) || p.getMelded8().size() == 0)) {
            return false;
        }
        if (!((p.getMelded9().size() >= 3 && countWildCards(p.getMelded9(), 9) <= p.getMelded9().size()/2) || p.getMelded9().size() == 0)) {
            return false;
        }
        if (!((p.getMelded10().size() >= 3 && countWildCards(p.getMelded10(), 10) <= p.getMelded10().size()/2) || p.getMelded10().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedJack().size() >= 3 && countWildCards(p.getMeldedJack(), 11) <= p.getMeldedJack().size()/2) || p.getMeldedJack().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedQueen().size() >= 3 && countWildCards(p.getMeldedQueen(), 12) <= p.getMeldedQueen().size()/2) || p.getMeldedQueen().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedKing().size() >= 3 && countWildCards(p.getMeldedKing(), 13) <= p.getMeldedKing().size()/2) || p.getMeldedKing().size() == 0)) {
            return false;
        }

        if (!(p.getMeldedWild().size() == 0 || p.getMeldedWild().size() >= 3)) {
            return false;
        }
        return true;
    }

    /**
     * Searches hand for selected card and returns index
     * @param p (The player the action is from)
     * @param n (The value being searched for)
     * @return (Returns the index of the value in the hand)
     */
    public int searchHand(CanastaPlayer p, int n) {
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(i).getValue() == n) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Selects card
     * @param p (The player the action is from)
     * @param card (The card that is selected)
     * @return (Returns whether the action was successful or not)
     */
    public boolean selectCard(CanastaPlayer p, int card, CanastaGameState state) {
        if (state.getPlayerTurnID() == p.getPlayerNum()) {
            state.setSelectedCard(card);
            return true;
        }
        return false;
    }

    /**
     * Adds a selected card to the player's meld
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean meldCard(CanastaPlayer p, CanastaGameState state) {
        int pos = searchHand(p, state.getSelectedCard());

        if (pos == -1) {
            return false;
        }
        switch (state.getSelectedCard()) {
            case -1:
                return false;
            case 1:
                p.getPlayerMoves().add(1);
                p.getMeldedAce().add(p.getHand().remove(pos));
                break;
            case 2:
                p.getPlayerMoves().add(2);
                p.getMeldedWild().add(p.getHand().remove(pos));
                break;
            case 3:
                p.getPlayerMoves().add(3);
                p.getMelded3().add(p.getHand().remove(pos));
                break;
            case 4:
                p.getPlayerMoves().add(4);
                p.getMelded4().add(p.getHand().remove(pos));
                break;
            case 5:
                p.getPlayerMoves().add(5);
                p.getMelded5().add(p.getHand().remove(pos));
                break;
            case 6:
                p.getPlayerMoves().add(6);
                p.getMelded6().add(p.getHand().remove(pos));
                break;
            case 7:
                p.getPlayerMoves().add(7);
                p.getMelded7().add(p.getHand().remove(pos));
                break;
            case 8:
                p.getPlayerMoves().add(8);
                p.getMelded8().add(p.getHand().remove(pos));
                break;
            case 9:
                p.getPlayerMoves().add(9);
                p.getMelded9().add(p.getHand().remove(pos));
                break;
            case 10:
                p.getPlayerMoves().add(10);
                p.getMelded10().add(p.getHand().remove(pos));
                break;
            case 11:
                p.getPlayerMoves().add(11);
                p.getMeldedJack().add(p.getHand().remove(pos));
                break;
            case 12:
                p.getPlayerMoves().add(12);
                p.getMeldedQueen().add(p.getHand().remove(pos));
                break;
            case 13:
                p.getPlayerMoves().add(13);
                p.getMeldedKing().add(p.getHand().remove(pos));
                break;
        }
        return true;
    }


    /**
     * Searches through hand for selected card to move to
     * discard pile
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean addToDiscard(CanastaPlayer p, CanastaGameState state) {
        if (!(checkValidMeld(p))) {
            return false;
        }
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(i).getValue() == state.getSelectedCard()) {
                state.discardPile.add(p.getHand().remove(i));
                state.setSelectedCard(-1);
                return true;
            }
        }
        return false;
    }


    /**
     * Allows player to unmeld a card based on their previous moves list
     * @param p (The player the action is from)
     * @return (Returns if the action was successful or not)
     */
    public boolean undo(CanastaPlayer p) {
        if(p.getPlayerMoves().size() == 0) {
            return false;
        }
        int value = p.getPlayerMoves().get(p.getPlayerMoves().size()-1);

        switch (value) {
            case 1:
                p.getHand().add(p.getMeldedAce().remove(p.getMeldedAce().size()-1));
                break;
            case 3:
                p.getHand().add(p.getMelded3().remove(p.getMelded3().size()-1));
                break;
            case 4:
                p.getHand().add(p.getMelded4().remove(p.getMelded4().size()-1));
                break;
            case 5:
                p.getHand().add(p.getMelded5().remove(p.getMelded5().size()-1));
                break;
            case 6:
                p.getHand().add(p.getMelded6().remove(p.getMelded6().size()-1));
                break;
            case 7:
                p.getHand().add(p.getMelded7().remove(p.getMelded7().size()-1));
                break;
            case 8:
                p.getHand().add(p.getMelded8().remove(p.getMelded8().size()-1));
                break;
            case 9:
                p.getHand().add(p.getMelded9().remove(p.getMelded9().size()-1));
                break;
            case 10:
                p.getHand().add(p.getMelded10().remove(p.getMelded10().size()-1));
                break;
            case 11:
                p.getHand().add(p.getMeldedJack().remove(p.getMeldedJack().size()-1));
                break;
            case 12:
                p.getHand().add(p.getMeldedQueen().remove(p.getMeldedQueen().size()-1));
                break;
            case 13:
                p.getHand().add(p.getMeldedKing().remove(p.getMeldedKing().size()-1));
                break;
        }

        p.getPlayerMoves().remove(p.getPlayerMoves().size()-1);
        return true;
    }

}