(ns poker.utils-test
  (:require [clojure.test :refer :all]
            [poker.utils :refer :all]))

(def two-pair [[:ace :hearts] [3 :diamonds] [:ace :spades] [2 :clubs] [3 :clubs]])
(def one-pair [[:king :clubs] [2 :diamonds] [7 :spades] [:king :hearts] [5 :clubs]])

(deftest straight-tests
  (testing "Testing: straight?"
    (is
      (true? (straight? [[:ace :hearts] [2 :diamonds] [3 :clubs] [4 :hearts] [5 :spades]]))
      "Test for wheel straight")
    (is
      (true? (straight? [[6 :spades] [7 :spades] [8 :clubs] [9 :clubs] [10 :hearts]]))
      "Test for middle straight")
    (is
      (true? (straight? [[10 :spades] [:jack :clubs] [:queen :clubs] [:king :diamons] [:ace :spades]]))
      "Test for broadway straight")
    (is
      (true? (straight? [[2 :diamonds] [:ace :hearts] [4 :hearts] [3 :clubs] [5 :spades]]))
      "Test for out of order straight")
    (is
      (false? (straight? [[2 :diamonds] [:ace :hearts] [4 :hearts] [3 :clubs]]))
      "Test for lists that are too short")
    (is
      (false? (straight? [[2 :diamonds] [:ace :hearts] [4 :hearts] [3 :clubs] [5 :spades] [6 :spades]]))
      "Test for lists that are too long")
    (is 
      (false? (straight? []))
      "Test with the empty list")))

(deftest flush-tests
  (testing "Testing: flush?"
    (is
      (true? (flush? [[2 :hearts] [5 :hearts] [:king :hearts] [:queen :hearts] [10 :hearts]]))
      "Test with a list that represents a flush")
    (is
      (false? (flush? []))
      "Test with an empty list")
    (is
      (false? (flush? [[:ace :hearts]]))
      "Test with a list that is too short")
    (is 
      (false? (flush? [[2 :hearts] [5 :hearts] [:king :clubs] [:queen :hearts] [10 :hearts]]))
      "Test with a list not containing a flush")
    (is 
      (false? (flush? [[2 :hearts] [5 :hearts] [:king :hearts] [:queen :hearts] [10 :hearts] [:jack :hearts]]))
      "Test with a list that is too long")))

(deftest value-histogram-tests
  (testing "Testing: value-histogram"
    (is
      (= (value-histogram one-pair) {:king 2, 2 1, 7 1, 5 1}))))
