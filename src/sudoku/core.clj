(ns sudoku.core)


(def possible-numbers #{1 2 3 4 5 6 7 8 9})


(defn transform-line
  "transforms a vector of numbers into a vector of sets"
  [line]
  (mapv (fn [n] (if (zero? n) possible-numbers #{n})) line))


(defn singleton?
  "determines whether the argument is a singleton"
  [s]
  (and (number? (first s)) (set? s) (= 1 (count s))))


(defn -main [& args]
  (println "Hello, World!"))
