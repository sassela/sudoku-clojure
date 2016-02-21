(ns sudoku.core)


(def possible-nums #{1 2 3 4 5 6 7 8 9})


(defn transform-row
  "transforms a vector of numbers into a vector of sets"
  [line]
  (mapv (fn [n] (if (zero? n) possible-nums #{n})) line))


(defn transform
  [data]
  ;TODO add validation schema?
  (mapv transform-row data))

(defn singleton?
  "determines whether the argument is a singleton"
  [s]
  (and (number? (first s)) (set? s) (= 1 (count s))))


(defn remove-singleton
  "removes a singleton from a line of sets"
  [s line]
  (when (singleton? s) (mapv #(if (not= s %) (set (remove s %)) %) line)))


(defn -main [& args]
  (println "Hello, World!"))
