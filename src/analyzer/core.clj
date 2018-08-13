(ns analyzer.core
  (:require [poker.core :as p]
            [deck.core :as d]
            [poker.omaha :as o]
            [poker.utils :as u]))

(defn eval-runouts
  [p1 p2 board runouts]
  (loop [to-eval runouts
         p1-wins 0
         p2-wins 0
         ties 0]
    (if (empty? to-eval)
      (list p1-wins p2-wins ties)
      (let [h1 (o/get-best-hand p1 (concat board (first to-eval)))
            h2 (o/get-best-hand p2 (concat board (first to-eval)))
            result (p/compare-hands h1 h2)]
        (cond
          (= result 1) (recur (rest to-eval) (inc p1-wins) p2-wins ties)
          (= result -1) (recur (rest to-eval) p1-wins (inc p2-wins) ties)
          :else (recur (rest to-eval) p1-wins p2-wins (inc ties)))))))

(defn get-hand-equities
  "Takes two hands and a board(0 - 5 cards) and evaluates all possible runouts
  keeping track of the outcomes. Returns a 3-tuple containing the equity results
  for the two hands in the form (<h1-win> <h2-win> <tie>)"
  [p1 p2 board]
  (let [deck (d/remove-cards
                          (d/make-deck)
                          (concat p1 p2 board))
        num-cards-to-deal (- 5 (count board))]
    (if (= 0 num-cards-to-deal) ;; if we are already at the river, just compare the hands
      (let [winner (p/compare-hands
                     (o/get-best-hand p1 board)
                     (o/get-best-hand p2 board))]
        (cond
          (= winner 1) '(1 0 0)
          (= winner -1) '(0 1 0)
          :else '(0 0 1)))
      (eval-runouts p1 p2 board (u/all-n-elem-subsets num-cards-to-deal deck)))))
