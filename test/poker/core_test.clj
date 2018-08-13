(ns poker.core-test
  (:require [clojure.test :refer :all]
            [poker.core :refer :all]))

(def straight-flush [[4 :clubs] [5 :clubs] [6 :clubs] [7 :clubs] [8 :clubs]])
(def quads [[:ace :hearts] [:ace :diamonds] [:ace :clubs] [:ace :spades] [10 :spades]])
(def quad-2s [[2 :clubs] [2 :diamonds] [:king :spades] [2 :spades] [2 :hearts]])
(def full-house [[2 :hearts] [2 :spades] [10 :diamonds] [10 :clubs] [2 :diamonds]])
(def reg-flush [[4 :clubs] [10 :clubs] [:ace :clubs] [7 :clubs] [8 :clubs]])
(def ak-flush [[:king :clubs] [10 :clubs] [:ace :clubs] [7 :clubs] [8 :clubs]])
(def straight [[4 :clubs] [5 :spades] [6 :clubs] [7 :diamonds] [8 :hearts]])
(def three-of-a-kind [[6 :spades] [8 :diamonds] [8 :clubs] [:jack :clubs] [8 :hearts]])
(def two-pair [[10 :clubs] [:jack :hearts] [9 :spades] [9 :clubs] [:jack :spades]])
(def one-pair [[10 :clubs] [:jack :hearts] [9 :spades] [:ace :clubs] [:jack :spades]])
(def high-card [[10 :clubs] [:jack :hearts] [9 :spades] [:ace :clubs] [2 :spades]])
(def k-high-card [[10 :clubs] [:jack :hearts] [9 :spades] [:king :clubs] [2 :spades]])

(deftest rank-hand-tests
  (testing "Testing: rank-hand"
    (is (= (rank-hand straight-flush) :straight-flush) "Straight flush")
    (is (= (rank-hand quads) :four-of-a-kind) "Four of a kind")
    (is (= (rank-hand full-house) :full-house) "Full house")
    (is (= (rank-hand reg-flush) :flush) "Flush")
    (is (= (rank-hand straight) :straight) "Straight")
    (is (= (rank-hand three-of-a-kind) :three-of-a-kind) "Three of a kind")
    (is (= (rank-hand two-pair) :two-pair) "Two pair")
    (is (= (rank-hand one-pair) :one-pair) "One Pair")
    (is (= (rank-hand high-card) :high-card) "High card")))

(deftest compare-hand-tests
  (testing "Testing: compare-hands"
    (is (= (compare-hands straight-flush quads) 1) "Straight flush > quads")
    (is (= (compare-hands quads straight-flush) -1) "quads < Straight flush")
    (is (= (compare-hands quads quads) 0) "quads = quads")
    (is (= (compare-hands quads quad-2s) 1) "AAAAT > 2222K")
    (is (= (compare-hands k-high-card high-card) -1) "K-high < A-high")
    (is (= (compare-hands reg-flush ak-flush) -1) "AT flush < AK flush")))
