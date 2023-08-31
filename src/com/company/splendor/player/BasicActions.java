package com.company.splendor.player;

import com.company.splendor.card.Noble;
import com.company.splendor.card.cards.Card;
import com.company.splendor.crystal.crystals.Gold;

public interface BasicActions {
    void showStatus();
    void canInviteNobles(Noble[] nobles);
    boolean isWinner();
    boolean canBuyACard(Card card);
    Card buyACard(int difficulty,int pos);
    void borrow(int difficulty,int pos);
    Gold repay(int pos);
}
