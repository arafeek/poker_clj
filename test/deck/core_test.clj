(ns deck.core_test
  (:require [clojure.test :refer :all]
            [deck.core :refer :all]))

(deftest make-deck-test
  (testing "make-deck"
  (let [deck (make-deck)
        short-deck (make-deck [:hearts :spades])]
    (is (= (count deck) 52))
    (is (= (count short-deck) 26)))))

(deftest rank-test
  (testing "rank, it should work on numbers"
    (is (= (rank [2 :spades]) 0)))
  (testing "rank: it should work on keywords"
    (is (= (rank [:ace :hearts]) 12))
    (is (= (rank [:jack :spades]) 9))))

(deftest sort-test
  (testing "sort-cards, should work on empty list"
    (is (= (sort-cards []) [])))
  (testing "sort-cards, should work on lists of length 1"
    (is (= (sort-cards [[:ace :spades]]) [[:ace :spades]])))
  (testing "sort-cards, should work on lists of length > 1"
    (is (= (sort-cards [[2 :hearts] [5 :diamonds] [3 :hearts] [:ace :spades]])
           [[:ace :spades] [5 :diamonds] [3 :hearts] [2 :hearts]])))
  (testing "sort-cards, should work on lists too"
    (is (= (sort-cards '('(2 :hearts) '(5 :diamonds) '(3 :hearts) '(:ace :spades)))
           '('(:ace :spades) '(5 :diamonds) '(3 :hearts) '(2 :hearts))))))
