(ns numbers.bases-test
  (:require [clojure.test :refer :all]
            [numbers.bases :refer :all]))

(deftest sum-of-digits-test
  (testing "`sum-of-digits` works for base-10 number or string -> [Character] -> [Long] -> Long"
    (are [n sum] (= (sum-of-digits str base-10 n) sum)
         1234 10
         0 0
         999 27
         "101010101" 5
         123456789 45))
  (testing "`sum-of-digits` works for base-n version of arbitrary notation"
    (are [n sum] (= (sum-of-digits (second arbitrary-base-and-digits) arbitrary-base n) sum)
         "1234:12,13,14" 39
         "123:100,0,0,100" 200
         "256:255,255,255,255" 1020)))
