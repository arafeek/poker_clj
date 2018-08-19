(ns perf.core
  (:require [poker.core :as p]
            [deck.core :as d]
            [poker.omaha :as o]
            [poker.utils :as u]))

(defn profile-get-best-hand []
  (let [deck (d/make-deck)]
    (loop [x 100]
      (if (= x 0)
        nil
        (let [cards (shuffle deck)
               hand (take 4 cards)
               board (take-last 5 cards)]
          (do
            (time (o/get-best-hand hand board))
            (recur (dec x))))))))

(defn -main []
  (do 
    (println "Profiling omaha/get-best-hand ...")
    (profile-get-best-hand)
    (println "Done.")))
