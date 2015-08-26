(def v [1 2 3])

(conj v 4)

(conj v 4 5)
;;Maps
(def m {:a 5 :b 6})

(conj m [:c 7])

(seq m)

;;Sets

(def s #{1 2 3})

(conj s 10)

(conj s 3 4)

(seq s)

;;lists

(def lst '(1 2 3 ))

(conj lst 0)

(conj lst 0 -1)

(seq lst)

;;into function madness here
(into v [4 5])

(into m [[:c 7 ][:d 8]])

(into #{1 2} [2 3 4 5 3 3 2])

(into [1]{:a 1 :b 2 })


;;empty

(defn swap-pairs [sequential]
  (into (empty sequential)
        (interleave
         (take-nth 2 (drop 1 sequential))
         (take-nth 2 sequential))))

(swap-pairs (apply list (range 10)))

(swap-pairs (apply vector (range 10)))

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))


(map-map inc (hash-map :z 5 :c 6 :a 0))
