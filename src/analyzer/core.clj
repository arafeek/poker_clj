(ns analyzer.core
  (:require [poker.core :as p]
            [deck.core :as d]
            [poker.omaha :as o]
            [poker.utils :as u]))

(defn eval-n-runouts
  "Randomly evaluates n runouts"
  [n p1 p2 board deck]
  (let [num-cards-to-deal (- 5 (count board))]
    (loop [runout (u/sample num-cards-to-deal deck) ;; get a random runout from the remaining deck
           p1-wins 0
           p2-wins 0
           ties 0
           num-trials 0]
      ;; We always do n trials
      ;; NOTE: this could be inefficient if n > total number of possible runouts
      ;; since it will sample with replacement
      ;; TODO: fix that
      (if (= (inc n) num-trials)
        (list p1-wins p2-wins ties)
        (let [h1 (o/get-best-hand p1 (concat board runout))
              h2 (o/get-best-hand p2 (concat board runout))
              result (p/compare-hands h1 h2)]
          (cond
            (= result 1) (recur
                           (u/sample num-cards-to-deal deck) ;; generate a new runout
                           (inc p1-wins)
                           p2-wins
                           ties
                           (inc num-trials))
            (= result -1) (recur
                            (u/sample num-cards-to-deal deck)
                            p1-wins
                            (inc p2-wins)
                            ties
                            (inc num-trials))
            :else (recur
                    (u/sample num-cards-to-deal deck)
                    p1-wins
                    p2-wins
                    (inc ties)
                    (inc num-trials))))))))

(defn eval-runouts
  "Evaluates all possible runouts given 2 hands, a board state and a list of additional
  cards to run out"
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

(defn get-hand-equities2
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
      (eval-n-runouts 5000 p1 p2 board deck))))


;; TODO: write this
(defn calc-game-tree
  "Returns a LoL representing all posible remaining game states given a starting game state.
  Generates and returns a tree that contains all possible remaining board states"
  [board dead & hands]
  nil)
