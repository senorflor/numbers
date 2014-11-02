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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Closed form for some geometric integer sequences
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn triangularize
  [n]
  (/ (* (inc n) n) 2))

(defn square
  [n]
  (* n n))

(defn pentagonalize
  [n]
  (inc (* (/ 5 2)
          (- (* n n) n))))

(defn sexagonalize
  [n]
  (inc (* 3
          (- (* n n) n))))

(defn triagonally-pyrimadize
  [n]
  (/ (* n (inc n) (+ 2 n))
     6))

(defn squarely-pyramidize
  [n]
  (/ (* n (inc n) (inc (* 2 n)))
     6))

#_(defn pentagonally-pyramidize
  [n]
  (/ ))

#_ (defn sexagonally-pyramidize)

;;;TODO: is there a generic n-gon-alize and n-gon-ally-pyramidize for
;;; all n greater than some constant? What about extensions to
;;; non-pyramidal 3D and >3D hyper-pyramids? What is a hyper-pyramid?
;;; This is a big tangent from the book, btw.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Checks for divisibility by small primes (base-10 specific)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def sum-base-10-digits
  (partial b/sum-of-digits str b/base-10))

(defn div3?
  [n]
  "Not really terribly interesting since we can do this with `rem`,
  but the idea is that the sum of all base-10 digits of any number
  divisible by 3 is also itself divisible by 3. TODO: c.f. rem's
  implementation."
  (loop [n n]
    (cond
     (#{3 6 9} n) true
     (< n 10) false
     :else (recur (sum-base-10-digits n)))))



