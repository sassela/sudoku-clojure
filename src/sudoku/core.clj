(ns sudoku.core
  (require [clojure.pprint :refer [pprint]]
    [clojure.set :refer [difference]]
    [clojure.core.matrix :refer [pm]])
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
  [data s]
  (if (singleton? data) data (set (remove s data))))


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
                                           (if (>= i (+ x-start box-size))
                                             row
                                             (recur (inc i) (update row i (fn [d] (remove-singleton d s))))))) ;TODO move
        ]
    (loop [row y-start d data]
      (if (>= row (+ y-start box-size)) d (recur (inc row) (update d row remove-singleton-box-row))))))


(defn remove-singletons
  "for every other set in the same row, the same column, or the same 3x3 box, removes that number (if present)."
  [data s x y]
  (-> data
    (remove-singleton-row s y)
    (remove-singleton-col s x)
    (remove-singleton-box s x y)))


(defn coordinates
  "returns all coordinates of the data"
  [data]
  (let [n-cols (range (count data))
        n-rows (range (count (first data)))]
    (into (sorted-set) (for [x n-cols y n-rows] (vector x y)))))

(defn set-at
  [data x y]
  (-> data (nth y) (nth x)))

(defn every-set-is-singleton?
  [data]
  (every? true? (map
                  #(let [x (first %)
                         y (second %)]
                     (singleton? (set-at data x y)))
                  (coordinates data))))

(defn remove-all-singletons
  "removes singletons for every singleton in the data"
  [data]
  (loop [d data c (coordinates data)]
    (if (or (nil? c) (empty? c))
      d
      (recur
        (let [xy (first c)
              x (first xy)
              y (second xy)
              s (set-at data x y)]
          (if (singleton? s) (remove-singletons d s x y) d))
        (rest c)))))

(defn unique-singleton
  [s other-sets]
  (when singleton? (difference s (set other-sets)) (difference s (set other-sets))))

(defn reduce-unique-singletons
  [data]
  (let [row #(nth data %)
        col #(map (fn [row] (nth row %)) data)
        box (fn [x y] (flatten (map #(subvec % (quot x 3) (+ (quot x 3) 3)) (subvec data y (+ y 3)))))]
    (loop [d data c (coordinates data)]
      (if (or (nil? c) (empty? c))
        d
        (recur (let [xy (first c)
                     x (first xy)
                     y (second xy)
                     s (set-at data x y)]
                 (print (difference )))
          (rest c))))
    ))


(defn solve
  [data]
  (let [transformed-data (transform data)]
    (loop [d transformed-data]
      (if (every-set-is-singleton? d)
        d
        (recur (do
                 (print d)
                 (remove-all-singletons d)))))
    ))


(defn -main [& args]
  (println "Hello, World!"))
