;; Omaha hand ranking and evauluation
;;
;; In Omaha, you *must* use exactly two cards from your hand and 3 from the board
;;
;; This module handles the following:
;; It separates the player's hand into all possible 2 card hands, and the board (5 cards)
;; into all possible 3 card combinations. It then takes the cross product of these two sets
;; and returns the best possible poker hand that can be formed.
;;
;; It expects that `board` have at least 3 items in it (a flop exists)
;; It expects that `hand` has exactly 4 items in it
;;
(ns poker.omaha
  (:require [poker.core :as p]
            [deck.core :as d]
            [poker.utils :as u]))

(defn get-best-hand
  [hand board]
  (let [hand-combos (u/all-n-elem-subsets 2 hand) ;; all 2 card hands from the 4 card hand
        board-combos (u/all-n-elem-subsets 3 board) ;; all 3 card combos from the board
        valid-hands (map d/sort-cards (u/lcross-prod hand-combos board-combos))]
    (loop [hands valid-hands
           curr-best nil]
      (if (empty? hands)
        curr-best
        (if (= (p/compare-hands (first hands) curr-best) 1)
          (recur (rest hands) (first hands))
          (recur (rest hands) (curr-best)))))))
