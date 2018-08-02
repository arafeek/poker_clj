;; Omaha hand ranking and evauluation
(ns poker.omaha
  (:require [deck.core :refer :all]))

(def hand-ranks {
                 :high-card 0
                 :one-pair 1
                 :two-pair 2
                 :three-of-kind 3
                 :straight 4
                 :flush 5
                 :full-house 6
                 :four-of-kind 7
                 :straight-flush 8
                 })

(defn evaluate
  "Takes a collection of cards, returns the best possible poker hand"
  [cards]
  )
