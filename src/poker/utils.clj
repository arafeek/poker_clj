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


;; Non poker specific util functions

(defn all-n-elem-subsets
  "Returns all subsets of size n from a list of elements"
  [n lst]
  (if (= n 1)
    (map vector lst)
    (apply concat
           (map-indexed
             #(map (fn [x] (conj x %2))
                   (all-n-elem-subsets (dec n) (drop (inc %1) lst)))
             lst))))

(defn lcross-prod
  "Returns the cross product of two lists of lists as a flat vector"
  [l1 l2]
  (for [x l1 y l2] (concat x y)))

(defn cross-prod
  "Returns the cross product of two lists"
  [l1 l2]
  (for [x l1 y l2] [x y]))
