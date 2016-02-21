(ns sudoku.core)


(def possible-numbers #{1 2 3 4 5 6 7 8 9})


(defn transform-line
  "transforms a vector of numbers into a vector of sets"
  [line]
  (mapv (fn [n] (if (zero? n) possible-numbers #{n})) line))


(defn -main [& args]
  (println "Hello, World!"))
