;; Poker hand ranking and evaluation
(ns poker.core)

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
      :else "--NIL--"))

(defn parse-card-int
  "Returns the string representation of a card int"
  [c]
  (let [r (bit-and (bit-shift-right c 8) 2r1111)
        s (get-suit c)]
    (str (value-strs r) s)))
