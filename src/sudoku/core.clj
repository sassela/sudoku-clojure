(ns sudoku.core
  (require [clojure.pprint :refer [pprint]])
  (:gen-class))


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
  ; TODO plumatic schema / regex?
  (and (number? (first s)) (set? s) (= 1 (count s))))


(defn remove-singleton
  [d s]
  (if (singleton? d) d (set (remove s d))))


(defn remove-singleton-row
  "removes a given singleton from a row of sets"
  [data s y]
  (update data y (fn [row] (mapv #(remove-singleton % s) row))))


(defn remove-singleton-col
  "removes a given singleton from the nth element in each row of data"
  [data s x]
  (mapv (fn [row] (update row x #(remove-singleton % s))) data))


(defn remove-singleton-box [data s x y]
  ;TODO use core.matrix? irange? https://cloojure.github.io/doc/core.matrix/clojure.core.matrix.select.html
  (let [box-size 3
        box-start #(* (quot % box-size) box-size)
        x-start (box-start x)
        y-start (box-start y)
        remove-singleton-box-row (fn [r] (loop [i x-start row r]
                                           (if (>= i (+ x-start box-size)) row (recur (inc i) (update row i (fn [d] (remove-singleton d s))))))) ;TODO move
        ]
    (loop [row y-start d data]
      (if (>= row (+ y-start box-size)) d (recur (inc row) (update d row remove-singleton-box-row))))))


(defn remove-all-singletons
  "for every other set in the same row, the same column, or the same 3x3 box, removes that number (if present)."
  [data s x y]
  (-> data
    (remove-singleton-row data s y)
    (remove-singleton-col data s x)
    (remove-singleton-box data s x y)))


(defn solve
  [data]
  (-> data
    transform)

  )


(defn -main [& args]
  (println "Hello, World!"))
