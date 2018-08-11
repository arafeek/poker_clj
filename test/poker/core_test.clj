(ns poker.core-test
  (:require [clojure.test :refer :all]
            [poker.core :refer :all]))

(def straight-flush [[4 :clubs] [5 :clubs] [6 :clubs] [7 :clubs] [8 :clubs]])
(def quads [[:ace :hearts] [:ace :diamonds] [:ace :clubs] [:ace :spades] [10 :spades]])
(def full-house [[2 :hearts] [2 :spades] [10 :diamonds] [10 :clubs] [2 :diamonds]])
(def reg-flush [[4 :clubs] [10 :clubs] [:ace :clubs] [7 :clubs] [8 :clubs]])
(def straight [[4 :clubs] [5 :spades] [6 :clubs] [7 :diamonds] [8 :hearts]])
(def three-of-a-kind [[6 :spades] [8 :diamonds] [8 :clubs] [:jack :clubs] [8 :hearts]])
(def two-pair [[10 :clubs] [:jack :hearts] [9 :spades] [9 :clubs] [:jack :spades]])
(def one-pair [[10 :clubs] [:jack :hearts] [9 :spades] [:ace :clubs] [:jack :spades]])
(def high-card [[10 :clubs] [:jack :hearts] [9 :spades] [:ace :clubs] [2 :spades]])

(deftest evaluate-tests
  (testing "Testing: evaluate"
    (is (= (evaluate straight-flush) :straight-flush) "Straight flush")
    (is (= (evaluate quads) :four-of-a-kind) "Four of a kind")
    (is (= (evaluate full-house) :full-house) "Full house")
    (is (= (evaluate reg-flush) :flush) "Flush")
    (is (= (evaluate straight) :straight) "Straight")
    (is (= (evaluate three-of-a-kind) :three-of-a-kind) "Three of a kind")
    (is (= (evaluate two-pair) :two-pair) "Two pair")
    (is (= (evaluate one-pair) :one-pair) "One Pair")
    (is (= (evaluate high-card) :high-card) "High card")))

