(ns poker.utils
  (:require [deck.core :refer :all]))

;; Returns the absolute value of a number
(defn abs [n] (max n (- n)))

(defn find-duplicates
  "Takes a collection of cards, returns a map keyed by card value"
  [cards]
  (group-by first cards))

(defn find-straights
  "Takes a colleciton of cards, returns a sequence of connected sequences"
  [cards]
  (let [sorted (sort-cards cards)]
    (loop [c (rest sorted)
           curr [(first sorted)]
           straights []]
      (if (empty? c)
        straights ;; base case
        (if (not (= (abs (- (rank (last curr)) (rank (first c)))) 1)) ;; difference of 1
          (recur (rest (rest c)) [(first c)] (conj straights curr)) ;; end this straight move onto the next
          (recur (rest c) (conj curr (first c)) straights)))))) ;; else continue on this current straight
