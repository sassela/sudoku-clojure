(ns sudoku.core)


(defn transform-row
  "transforms a vector of numbers into a vector of sets"
  [line]
  (let [possible-nums #{1 2 3 4 5 6 7 8 9}]
    (mapv (fn [n] (if (zero? n) possible-nums #{n})) line)))


(defn transform
  [data]
  ;TODO add validation schema?
  (mapv transform-row data))


(defn singleton?
  "determines whether the argument is a singleton"
  [s]
  (and (number? (first s)) (set? s) (= 1 (count s))))


(defn nth-if-not-s
  [data n]
  (if (singleton? data) data (nth data n)))


(defn remove-singleton-row
  "removes a given singleton from a row of sets"
  [data s y]
  (update data y (fn [row] (mapv #(if (singleton? %) % (set (remove s %))) row))))


(defn box
  [data x y]
  ;TODO in remove-singleton-box let block?
  (map #(subvec % (quot x 3) (+ (quot x 3) 3)) (subvec data y (+ y 3))))


(defn blah
  [data x y]
;  (pprint (map #(assoc data % 0) (range (quot y 3) (+ (quot y 3) 3)))) ;col
  ;update x-x plus 3 in row. reduce?
;  (fn [row x] (map #(assoc row % 0) (range (quot x 3) (+ (quot x 3) 3))))

  (pprint (map (fn [row] (map #(assoc row % 0) (range (quot x 3) (+ (quot x 3) 3)))) (range (quot y 3) (+ (quot y 3) 3))))
;    (+ (quot y 3) 3)
;  (subvec data y (+ y 3))
;  (update (subvec data y (+ y 3)) x 0)
  )

(defn haveago [data x y]
  ;TODO use core.matrix? irange? https://cloojure.github.io/doc/core.matrix/clojure.core.matrix.select.html
  (let [y-start (* (quot y 3) 3)
        x-start (* (quot x 3) 3)]
    ;replaces box in row with 0s
    (loop [r y-start]
      (when (< r (+ y-start 3))
        (-> (nth data r) (assoc x-start 0) (assoc (+ x-start 1) 0) (assoc (+ x-start 2) 0) println))
      (recur (inc r)))
    ))

(defn remove-singleton-box
  "removes a given singleton from a 3x3 box of sets"
  [box s]
  ;TODO redundant?
  (mapv #(remove-singleton-row % s) box))


(defn remove-singleton-col
  "removes a given singleton from the nth element in each row of data"
  [data s x]
  (mapv (fn [column] (update column x #(if (singleton? %) % (set (remove s %))))) data))



(defn remove-all-singletons
  "for every other set in the same row, the same column, or the same 3x3 box, removes that number (if present)."
  [data s x y]
  (let [row (nth data y)
        b (box data x y)]
    ;TODO encaps ^
;    (remove-singleton-row (nth data y) s)
    (remove-singleton-col data s x)
;    (remove-singleton-box b s)
    ))


(defn solve
  [data]
  (-> data
    transform)

  )


(defn -main [& args]
  (println "Hello, World!"))
