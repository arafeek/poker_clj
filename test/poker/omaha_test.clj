(ns poker.omaha-test
  (:require [clojure.test :refer :all]
            [poker.omaha :as o]
            [poker.core :as p]))

;; Hands for testing
(def hand1 [[:ace :hearts] [:ace :diamonds] [2 :hearts] [:king :diamonds]])

;; Boards for testing
(def heart-flush-board
  [[10 :hearts] [7 :hearts] [:queen :clubs] [8 :diamonds] [4 :hearts]])


(deftest get-best-hand-tests
  (testing "Testing: get-hand"
    (is (= :flush (p/rank-hand (o/get-best-hand hand1 heart-flush-board))) "Heart flush")))
