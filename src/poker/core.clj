;; Poker hand ranking and evaluation
(ns poker.core
  (:require [deck.core :as deck]
            [poker.utils :as utils]))

(def hand-ranks {
                 :high-card 0
                 :one-pair 1
                 :two-pair 2
                 :three-of-a-kind 3
                 :straight 4
                 :flush 5
                 :full-house 6
                 :four-of-a-kind 7
                 :straight-flush 8
                 })

;; Expects a list containing only 5 cards
;; It is the onus of the calling function to decide which 5 cards to evaluate
(defn evaluate
  "Takes a list of 5 cards, returns the value of the best possible poker hand it forms"
  [cards]
  (let [valueCounts (sort (vals (utils/value-histogram cards))) ;; get a list that represents the number of repeated values in the hand eg [2 3] => full house
        isStraight (utils/straight? cards)
        isFlush (utils/flush? cards)]
    (cond
      (and isStraight isFlush) :straight-flush
      (= valueCounts [1 4]) :four-of-a-kind
      (= valueCounts [2 3]) :full-house
      isFlush :flush
      isStraight :straight
      (= valueCounts [1 1 3]) :three-of-a-kind
      (= valueCounts [1 2 2]) :two-pair
      (= valueCounts [1 1 1 2]) :one-pair
      :else :high-card)))


