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
         123456789 45)))

