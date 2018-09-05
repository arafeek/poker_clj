;; Poker hand ranking and evaluation
(ns poker.core
  (:require [poker.arrays :as arrays]))

;; credit to http://suffe.cool/poker/evaluator.html

;; =======================================================================================
;;
;; Card Encoding
;;
;; =======================================================================================

(def suits [2r1000 2r0100 2r0010 2r0001]) ;; clubs, diamonds, hearts, spades

;; Give each card value a prime number for fast and unique comparisons
(def primes [2 3 5 7 11 13 17 19 23 29 31 37 41]) ;; 2, 3, 4, ..., K, A
(def ranks [0 1 2 3 4 5 6 7 8 9 10 11 12])

(def value-strs ["2" "3" "4" "5" "6" "7" "8" "9" "T" "J" "Q" "K" "A"])
(def suit-str ["c" "d" "h" "s"])

;; a deck is a list of 52 cards
;; A card is an int of the form
;; xxxbbbbb|bbbbbbbb|cdhsrrrr|xxpppppp
;; b - value bit, one for each card value
;; cdhs - suit bits, one for each suit
;; r - card rank encoding
;; p - prime rank encoding
(defn make-deck []
  (for [s suits r ranks]
    (bit-or
      (primes r)
      (bit-shift-left r 8)
      (bit-shift-left s 12)
      (bit-shift-left 1 (+ 16 r)))))

(defn get-suit
  "Returns a string representing the value of the suit bits of a card int"
  [c]
  (cond
      (not (zero? (bit-and c 32768))) "c" ;; 15th bit
      (not (zero? (bit-and c 16384))) "d" ;; 14th bit
      (not (zero? (bit-and c 8192))) "h"  ;; 13th bit
      (not (zero? (bit-and c 4096))) "s"  ;; 12th bit
      :else "--NIL--")) ;; litmus test for an error in our encoding

;; =======================================================================================
;;
;; Evaluating
;;
;; =======================================================================================

(defn combine-vals
  [c1 c2 c3 c4 c5]
  (bit-shift-right (bit-or c1 c2 c3 c4 c5) 16))

(defn same-suit? [c1 c2 c3 c4 c5]
  (not (zero? (bit-and c1 c2 c3 c4 c5 2r1111000000000000))))

(defn eval-hand
  "Evaluates a poker hand, returns its value"
  [c1 c2 c3 c4 c5]
  (let [q (combine-vals c1 c2 c3 c4 c5)]
    (cond
      (same-suit? c1 c2 c3 c4 c5) (arrays/flushes q)
      (not (zero? (arrays/unique5 q))) (arrays/unique5 q)
      :else (let [prod (* (bit-and c1 0xFF) ;; 2r11111111 (turn on prime encoding only)
                          (bit-and c2 0xFF)
                          (bit-and c3 0xFF)
                          (bit-and c4 0xFF)
                          (bit-and c5 0xFF))]
              (arrays/hand-values (.indexOf arrays/products prod))))))


(defn rank-hand
  "Takes a poker hand, returns its rank"
  [hand-val]
  (cond
    (> hand-val 6185) :high-card
    (> hand-val 3325) :one-pair
    (> hand-val 2467) :two-pair
    (> hand-val 1609) :three-of-a-kind
    (> hand-val 1599) :straight
    (> hand-val 322) :flush
    (> hand-val 166) :full-house
    (> hand-val 10) :four-of-a-kind
    :else :straight-flush))

;; =======================================================================================
;;
;; PARSING
;;
;; =======================================================================================

(defn parse-card-int
  "Returns the string representation of a card int"
  [c]
  (let [r (bit-and (bit-shift-right c 8) 2r1111)
        s (get-suit c)]
    (str (value-strs r) s)))

;; TODO
(defn parse-card-str
  "Returns the int represenation of a card string"
  [cs]
)
