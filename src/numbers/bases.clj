(ns ^{:doc "numbers.bases: free your mind from base 10 parochialism (pp 1-8)."}
  numbers.bases
  (:require [instaparse.core :as insta]))

;; First let's define some mappings from bases/digits to Clojure
;; numbers, to faciliate doing some math. Bases 2-36 exist as literals
;; in Clojure, but we want a more general mechanism for exploration.

(def base-5
  "Maps characters [0-4] to their expected values"
  (->> (range 5)
       (interleave "01234")
       (apply hash-map)))

(def base-10
  "Maps characters [0-9] to their expected values"
  (->> (range 10)
       (interleave "0123456789")
       (apply hash-map)))

(def base-36i
  "Maps characters [0-9a-z] to their expected values, case
  insensitively."
  (->> (range 10)
       (concat (range 10 36))
       (concat (range 10 36)) ;; repeated for upper-case letters
       (interleave "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
       (apply hash-map)))

(def base-arbitrary
  "Started to wonder why we subscribe to the one character == one
  value convention when devising notations for large bases. This
  notation uses ',' to separate individual places instead of the usual
  grouping of digits, which allows us to annotate each place as a base
  10 number, e.g.

  100:12,99,1 == 129901 in base ten
  101:12,99,1 == 132412 in base ten
      80:2,45 == 205 in base ten

  This can't be the first time someone has done this; requires a parse
  then a mapping, but is much easier on the base-10-trained
  eyes/brain. We don't worry about initial 0's meaning anything
  special in this notation.

  UPDATE: of course someone has done this before; see IP addresses."

  (insta/parser "
     Number := Base':'Digits
     Base   := num
     Digits := num(<','>num)+
     <num>  := #'[0-9]+'
     "))

(defn sum-of-digits
  "Takes a YourNumber->[digits] parser, a digit->num mapping, and a
  number, and returns the sum of all the digits' values in that
  mapping."
  [parse values s]
  (->> (parse s)
       (map values)
       (reduce +')))
