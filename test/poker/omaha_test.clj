(ns poker.omaha-test
  (:require [clojure.test :refer :all]
            [poker.omaha :as o]
            [poker.core :as p]))

;; Hands for testing
(def hand1 [[:ace :hearts] [:ace :diamonds] [2 :hearts] [:king :diamonds]])
(def hand2 [[7 :spades] [:queen :hearts] [10 :clubs] [:king :hearts]])

;; Boards for testing
(def heart-flush-board
  [[10 :hearts] [7 :hearts] [:queen :clubs] [8 :diamonds] [4 :hearts]])
(def four-flush-board
  [[2 :diamonds] [3 :spades] [10 :diamonds] [5 :diamonds] [:ace :diamonds]])


(deftest get-best-hand-tests
  (testing "Testing: get-hand"
    (is (= (o/get-best-hand hand1 heart-flush-board)
           [[2 :hearts] [4 :hearts] [7 :hearts] [10 :hearts] [:ace :hearts]])
        "Heart flush")
    (is (= (o/get-best-hand hand2 four-flush-board)
           [[5 :diamonds] [10 :clubs] [10 :diamonds] [:king :hearts] [:ace :diamonds]])
        "One pair")))
