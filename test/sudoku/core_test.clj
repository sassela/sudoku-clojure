(ns sudoku.core-test
  (:require [clojure.test :refer :all]
            [sudoku.core :refer :all]))


(deftest transform-line-test
  (testing "function transforms a vector of numbers into a vector of sets"
    (let [transformed-line [#{7 1 4 6 3 2 9 5 8} #{2} #{5} #{7 1 4 6 3 2 9 5 8}]]
          (is (= transformed-line (transform-line [0 2 5 0]))))))

(deftest singleton?-test
  (testing "function determines whether the argument is a singleton"
    (is (true? (singleton? #{3})))
    (is (false? (singleton? [3])))
    (is (false? (singleton? #{"3"})))
    (is (false? (singleton? #{3 5})))))
