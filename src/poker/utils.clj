(ns poker.utils
  (:require [deck.core :as deck]))

(defn straight?
  "Takes a collection of cards, returns true if it represents a 5 card straight"
  [cards]
  (if (empty? cards)
    false 
    (let [ranks (map deck/rank (deck/sort-cards cards))]
      (or (= ranks (take 5 (range (first ranks) (+ (first ranks) 5))))
          (= ranks [0 1 2 3 12]))))) ;; ranks for what a wheel looks like: (2 3 4 5 A) in asc order

(defn flush?
  "Takes a collection of cards, returns true if they are all the same suit"
  [cards]
  (if (not (= (count cards) 5))
    false
    (every? #(= (deck/suit %) (deck/suit (first cards))) cards)))

(defn value-histogram
  "Takes a collection of cards, returns a map of the frequencies that the card ranks appear"
  [cards]
  (frequencies (map deck/value cards)))
