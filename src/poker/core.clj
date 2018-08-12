;; Poker hand ranking and evaluation
(ns poker.core
  (:require [deck.core :as deck]
            [poker.utils :as utils]))

(defn- compare-high-cards
  "A helper function that takes two hands that are assumed to be flushes in desc sorted order
  and compares them. Returns 1 if h1 is greater, -1 if h2 is greater and 0 if h1 = h2"
  [hr1 hr2]
  (loop [r1 hr1
         r2 hr2]
    (cond
      (empty? r1) 0 ;; if we hit the end, hands must be equal
      (> (first r1) (first r2)) 1
      (< (first r1) (first r2)) -1
      :else (recur (rest r1) (rest r2)))))

(defn- compare-straights
  [hr1 hr2]
  (let [top1 (first hr1)
        second1 (second hr1)
        top2 (first hr2)
        second2 (second hr2)]
    (cond
      (and (> top1 top2) (> second1 second2)) 1
      (and (< top1 top2) (< second1 second2)) -1
      (and (> top1 top2) (<= second1 second2)) -1 ;; if hr1 is a wheel
      (and (< top1 top2) (>= second1 second2)) 1 ;; if hr2 is a wheel
      :else 0)))

(defn- compare-multiples
  "Compares 2 hands of the same rank that contain multiples.
  IE, four-of-a-kind, full-house, three-of-a-kind, two-pair, pair"
  [hr1 hr2]
  ;; order the card ranks in terms of frequency since we know they have the same
  ;; frequency structure (being of the same overall hand rank)
  ;; then make a list of those ranks and compare them
  ;; EG AAAT vs 2222A -> [12 12 12 8] [0 0 0 0 12] -> compare-high-cards([12 8], [0 12]) -> 1
  (let [op1 (map first (reverse (sort-by val (frequencies hr1)))) ;; hand ordered by frequency of cards
        op2 (map first (reverse (sort-by val (frequencies hr2))))] ;; of the form [[<rank> <freq>] ...]
    (compare-high-cards op1 op2))) ;; once the cards are ordered by frequency, we simply compare their ranks


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

(defn hand-rank-val [hand-key]
  (hand-key hand-ranks))

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

(defn compare-hands
  "Returns 1 if h1 > h2, -1 if h2 > h1 and 0 if h1 = h2 in terms of strength"
  ([h1] 1)
  ([h1 h2]
    (let [r1 (evaluate h1)
          r2 (evaluate h2)]
      (cond
        (> (r1 hand-ranks) (r2 hand-ranks)) 1
        (< (r1 hand-ranks) (r2 hand-ranks)) -1
        :else (let [s1 (reverse (map deck/rank (deck/sort-cards h1)))
                    s2 (reverse (map deck/rank (deck/sort-cards h2)))]
                (cond
                  (= r1 :straight-flush) (compare-straights s1 s2)
                  (= r1 :flush) (compare-high-cards s1 s2)
                  (= r1 :straight) (compare-straights s1 s2)
                  (= r1 :high-card) (compare-high-cards s1 s2)
                  :else (compare-multiples s1 s2))))))) ;; 4-of-kind, full house, 3-of-kind, 2-pair, pair


