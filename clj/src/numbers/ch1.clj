(ns numbers.core
  (:require [clojure.set :as s]
            [numbers.bases :as b]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Good ol' roman numerals, strict flavor (e.g. MCMXCIX [not MIM] for
;;; 1999)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def val->roman
  ^{:private true}
  (sorted-map-by >
                 1000 "M"
                 900  "CM"
                 500  "D"
                 400  "CD"
                 100  "C"
                 90   "XC"
                 50   "L"
                 40   "XL"
                 10   "X"
                 9    "IX"
                 5    "V"
                 4    "IV"
                 1    "I"))

(def roman->val
  ^{:private true}
  (->> val->roman
       (vec)
       (map reverse)))

(defn n->roman
  [n]
  (loop [n n, m val->roman, s []]
    (let [[k v] (first m)
          m' (dissoc m k)]
      (cond
       (= n 0) (apply str s)
       (< n k) (recur n, m', s)
       :else   (recur (- n k), m, (conj s v))))))

(defn roman->n
  [s]
  (loop [n 0, pos 0, m roman->val]
    (let [[k v] (first m)
          l  (count k)
          m' (rest m)]
      (cond
       (>= pos (count s)) n
       (.regionMatches s pos k 0 l) (recur (+ n v) (+ pos l) m)
       :else (recur n pos m')))))

;;;TODO: Look up greek numerals and create some roundtrip functions
;;; Potential advantages of a non-directional, non-positional number
;;; system, like Greek numerals appear to be:
;;;   - easy in-place updates on an IRL surface (like tallies with a
;;;     short-hand for large batches). c.f. 80 => 81 in Hindu-Arabic.
;;;   - Immutable representation of historical values where diff is
;;;     most important, not accumulated value. (closely related).

;;; Question: based on our grouped notation in bases.clj, are there
;;; any interesting uses for supernumerary integers, e.g. 10:11,11
;;; which equals 10:1,2,1 aka 121?
