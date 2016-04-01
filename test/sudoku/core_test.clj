(ns sudoku.core-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]))


(def starting-data [[0 2 5 0 0 1 0 0 0]
                    [1 0 4 2 5 0 0 0 0]
                    [0 0 6 0 0 4 2 1 0]
                    [0 5 0 0 0 0 3 2 0]
                    [6 0 0 0 2 0 0 0 9]
                    [0 8 7 0 0 0 0 6 0]
                    [0 9 1 5 0 0 6 0 0]
                    [0 0 0 0 7 8 1 0 3]
                    [0 0 0 6 0 0 5 9 0]])


(def transformed-data [[#{7 1 4 6 3 2 9 5 8}                 #{2}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{1} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
                       [                #{1} #{7 1 4 6 3 2 9 5 8}                 #{4}                 #{2}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
                       [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{4}                 #{2}                 #{1} #{7 1 4 6 3 2 9 5 8}]
                       [#{7 1 4 6 3 2 9 5 8}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{3}                 #{2} #{7 1 4 6 3 2 9 5 8}]
                       [                #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{2} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{9}]
                       [#{7 1 4 6 3 2 9 5 8}                 #{8}                 #{7} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8}]
                       [#{7 1 4 6 3 2 9 5 8}                 #{9}                 #{1}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
                       [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{7}                 #{8}                 #{1} #{7 1 4 6 3 2 9 5 8}                 #{3}]
                       [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{5}                 #{9} #{7 1 4 6 3 2 9 5 8}]])


(deftest transform-row-test
  (testing "function transforms a vector of numbers into a vector of sets"
    (let [transformed-row [#{7 1 4 6 3 2 9 5 8} #{2} #{5}]]
          (is (= transformed-row (transform-row [0 2 5]))))))


(deftest transform-test
  (testing "functions transforms a vector of vectors of numbers into a vector of vector of sets"
    (is (= transformed-data (transform starting-data)))))


(deftest singleton?-test
  (testing "function determines whether the argument is a singleton"
    (is (true? (singleton? #{3})))
    (is (false? (singleton? [3])))
    (is (false? (singleton? #{"3"})))
    (is (false? (singleton? #{3 5})))))


(deftest remove-singleton-row-test
  (testing "function removes a given singleton from a row of sets"
    (let [data [[#{7 1 4 6 3 2 9 5 8} #{5} #{9}]]]
      (is (= [[#{7 1 4 6 3 2 5 8} #{5} #{9}]] (remove-singleton-row data #{9} 0))))))


(deftest remove-singleton-col-test
  (testing "function removes a given singleton from the nth element in each row of data"
    (let [data [[#{7 1 4 6 3 2 9 5 8}][#{2}][#{5 2}]]]
      (is (= [[#{7 1 4 6 3 9 5 8}][#{2}][#{5}]] (remove-singleton-col data #{2} 0))))))


(deftest remove-singleton-box-test
  (testing "function removes a given singleton from a 3x3 box of sets"
    (let [box [[#{7 1 4 6 3 2 9 5 8}                 #{9}                 #{1}]
               [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
               [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]]
          correctly-transformed-box [[#{7 1 4 6 3 2 5 8}               #{9}               #{1}]
                                     [#{7 1 4 6 3 2 5 8} #{7 1 4 6 3 2 5 8} #{7 1 4 6 3 2 5 8}]
                                     [#{7 1 4 6 3 2 5 8} #{7 1 4 6 3 2 5 8} #{7 1 4 6 3 2 5 8}]]]
      (is (= correctly-transformed-box (remove-singleton-box box #{9} 0 0))))))


(deftest remove-singletons-test
  (testing "function removes a given singleton from the same row, the same column, or the same 3x3 box (if present)"
    (let [data transformed-data
          one-singleton-removed [[  #{7 1 4 6 3 9 5 8}               #{2}                 #{5}   #{7 1 4 6 3 9 5 8}   #{7 1 4 6 3 9 5 8}                 #{1}   #{7 1 4 6 3 9 5 8}   #{7 1 4 6 3 9 5 8}   #{7 1 4 6 3 9 5 8}]
                                 [                #{1} #{7 1 4 6 3 9 5 8}                 #{4}                 #{2}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
                                 [  #{7 1 4 6 3 9 5 8} #{7 1 4 6 3 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{4}                 #{2}                 #{1} #{7 1 4 6 3 2 9 5 8}]
                                 [#{7 1 4 6 3 2 9 5 8}               #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{3}                 #{2} #{7 1 4 6 3 2 9 5 8}]
                                 [                #{6} #{7 1 4 6 3 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{2} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{9}]
                                 [#{7 1 4 6 3 2 9 5 8}               #{8}                 #{7} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8}]
                                 [#{7 1 4 6 3 2 9 5 8}               #{9}                 #{1}                 #{5} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}]
                                 [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 9 5 8} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{7}                 #{8}                 #{1} #{7 1 4 6 3 2 9 5 8}                 #{3}]
                                 [#{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{6} #{7 1 4 6 3 2 9 5 8} #{7 1 4 6 3 2 9 5 8}                 #{5}                 #{9} #{7 1 4 6 3 2 9 5 8}]]]
      (is (= one-singleton-removed (remove-singletons data #{2} 1 0))))))


(deftest coordinates-test
  (testing "function returns all coordinates of the data"
    (let [data transformed-data]
      (is (= (* (count data) (count data)) (count (coordinates data)))))))


(deftest remove-all-singletons-test
  (testing "function removes singletons for every singleton in the data"
    (let [data transformed-data
          all-singletons-removed [[  #{7 3 9 8}     #{2}     #{5}   #{7 3 9 8}   #{6 3 9 8}       #{1} #{7 4 9 8} #{7 4 3 8} #{7 4 6 8}]
                                  [        #{1}   #{7 3}     #{4}         #{2}         #{5} #{7 6 3 9}   #{7 9 8}   #{7 3 8}   #{7 6 8}]
                                  [  #{7 3 9 8}   #{7 3}     #{6}   #{7 3 9 8}     #{3 9 8}       #{4}       #{2}       #{1}   #{7 5 8}]
                                  [      #{4 9}     #{5}     #{9} #{7 1 4 9 8} #{1 4 6 9 8}   #{7 6 9}       #{3}       #{2} #{7 1 4 8}]
                                  [        #{6} #{1 4 3}     #{3} #{7 1 4 3 8}         #{2}   #{7 3 5}   #{7 4 8} #{7 4 5 8}       #{9}]
                                  [  #{4 3 2 9}     #{8}     #{7}   #{1 4 3 9}   #{1 4 3 9}   #{3 9 5}       #{4}       #{6}   #{1 4 5}]
                                  [#{7 4 3 2 8}     #{9}     #{1}         #{5}       #{4 3}     #{3 2}       #{6}   #{7 4 8} #{7 4 2 8}]
                                  [    #{4 2 5}   #{4 6}     #{2}       #{4 9}         #{7}       #{8}       #{1}       #{4}       #{3}]
                                  [#{7 4 3 2 8} #{7 4 3} #{3 2 8}         #{6}     #{1 4 3}     #{3 2}       #{5}       #{9} #{7 4 2 8}]]]
      (is (= all-singletons-removed (remove-all-singletons data))))))


(deftest adjacent-sets-test
  (testing "function gets set of items in the same row, column and box as the set at the given coordinate"
    (let [data transformed-data]
      (is (= #{#{6} #{7} #{2} #{8} #{7 1 4 6 3 2 9 5 8} #{1} #{5} #{4}}
            (adjacent-sets data 5 5))))))
