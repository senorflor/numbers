(ns numbers.bases-test
  (:require [clojure.test :refer :all]
            [numbers.bases :refer :all]))

(deftest sum-of-digits-test
  (are [n sum] (= (sum-of-digits #(Integer/parseInt (str %)) n) sum)
       1234 10
       0 0
       999 27
       "101010101" 5
       123456789 45))

