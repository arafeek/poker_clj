(ns poker.utils-test
  (:require [clojure.test :refer :all]
            [poker.utils :refer :all]))


(deftest utils-test
  (testing "find-straights:"
    (testing "should work with empty list"
      (is (= (find-straights []) [])))
    (testing "should work with lists of length 1"
      (is (= (find-straights [[:ace :spades]]) [[:ace :spades]])))
    (testing "should work with longer lists"
      (is (= (find-straights [
                              [2 :hearts]
                              [4 :diamonds]
                              [8 :hearts]
                              [10 :diamonds]
                              [9 :spades]])
             [[[2 :hearts]]
              [[4 :diamonds]]
              [[8 :hearts] [9 :spades] [10 :diamonds]]]))
      )))
