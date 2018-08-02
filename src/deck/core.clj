;; Utility class for generating / working with standard decks of cards

(ns deck.core)

;; Constants
(def suits [:spades  :hearts :diamonds :clubs]) ; uses the bridge ordering by default
(def card-vals [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def card-ranks {
                 2 0
                 3 1
                 4 2
                 5 3
                 6 4
                 7 5
                 8 6
                 9 7
                 10 8
                 :jack 9
                 :queen 10
                 :king 11
                 :ace 12
                 })



(defn make-deck
  "Returns a collection of cards representing a deck, defaults to standard 52"
  ([] (for [s suits v card-vals] [v s])) ; simple case
  ([custom-suits] (for [s custom-suits v card-vals] [v s])) ; supply custom suits
  ([custom-suits custom-vals] (for [s custom-suits v custom-vals] [v s]))) ; totally custom deck

(defn suit
  "Takes a card and returns the suit value"
  [card]
  (nth card 1))

(defn value
  "Takes a card and returns the vaue"
  [card]
  (nth card 0))

(defn rank
  "Takes a card and returns the rank of it's value, eg :ace => 12, 2 => 0 etc"
  [card]
  (let [v (value card)]
    (get card-ranks v)))

(defn sort-cards
  "Takes a collection of cards and returns them in descending order by rank, ignoring suits"
  [cards]
  (reverse (sort-by rank cards)))
