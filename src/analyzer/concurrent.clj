(ns analyzer.concurrent)

(defn calc-hand-equities-c
  [p1 p2 board]
  (let [run1 (future (calc-hand-equities p1 p2 board))
        run2 (future (calc-hand-equities p1 p2 board))
        run3 (future (calc-hand-equities p1 p2 board))]
    (list (+ (first @run1) (first @run2) (first @run3))
          (+ (second @run1) (second @run2) (second @run3))
          (+ (last @run1) (last @run2) (last @run3)))))
