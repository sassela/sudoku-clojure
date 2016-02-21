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


(defn remove-singleton-row
  "removes a given singleton from a row of sets"
  [s row]
  (when (singleton? s) (mapv #(if (not= s %) (set (remove s %)) %) row)))


(defn remove-singleton-box
  "removes a given singleton from a 3x3 box of sets"
  [s box]
  ;TODO redundant?
  (mapv #(remove-singleton-row s %) box))


(defn remove-singleton-col
  "removes a given singleton from the nth element in each row of data"
  [s col n]
  (mapv #(if (not= s (nth % n)) [(set (remove s (nth % n)))] [(nth % n)]) col))


(defn box
  [data x y]
  (map #(subvec % (quot x 3) (+ (quot x 3) 3)) (subvec data y (+ y 3))))


(defn -main [& args]
  (println "Hello, World!"))
