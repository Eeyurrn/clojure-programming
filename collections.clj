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


;;Counting various collections
(count [1 1 3])

(count {:a 1 :b 2 :c 3})

(count #{1 2 3})

(count '(1 2 3))

;;seqs
(seq "Clojure")

(seq {:a 5 :b 6})


(seq (java.util.ArrayList. (range 5)))


(seq (into-array ["Clojure" "Programming"]))

(seq [])

(seq nil)

;;Map function implicitly converts the String into a seq
(map str "Clojure")

;;So does the set function.

(set "Programming")

;;first rest and next

(first "Clojure")

(rest "Clojure")

(next "Clojure")

;;rest vs next
(rest [1])

(next [1])

;;rest will always return an empty seq and not nil

(rest nil)

(next nil)

(doseq [x (range 3)]
  (println x))

(let [r (range 3)
      rst (rest r)]
  (prn (map str rst))
  (prn (map #(+ 100 %) r))
  (prn (conj r -1)(conj rst 42)))


;;Lazy eval vs list
;;lazy
(let [s (range 1e6)]
  (time (count s)))

;;un-lazy
(let [s (apply list (range 1e6))]
  (time (count s)))

;;cons example
(cons 0 (range 1 5))

(cons :a [:b :c :d])

(cons 0 (cons 1 (cons 2 (cons 3 (range 4 10)))))

(list* 0 1 2 3 (range 4 10))


(defn random-ints
  "Returns a lazy seq of random integers in the range [0,limit"
  [limit]
  (lazy-seq
   (println "realizing random number")
   (cons (rand-int limit)
         (random-ints limit))))

(take 10 (random-ints 50))

(def rands (take 10 (random-ints 50)))
;;Lazy seq will evaluate
(first rands)
;; keeps evaluating
;;(nth 3 rands)
;;will keep the previous values and will continue from where it last evaluated
(count rands)

(repeatedly 10 (partial rand-int 50))

(def x (next (random-ints 50)))
(def x (rest (random-ints 50)))

(let [[x & rest](random-ints 50)])

(dorun (take 5 (random-ints 50)))

(apply str (remove (set "aeiouy")
                   "vowels are useless! or maybe not..."))


(split-with neg? (range -5 5))


#_(let [[t d] (split-with #(< % 12)(range 1e8))]
 [(count d) (count t)] )

;;Associative abstraction

(def m {:a 1 :b 2 :c 3})

(get m :b)

(get m :d "not found")

(assoc m :d 4)

(dissoc m :b)

(assoc m
  :x 4
  :y 5
  :z 6)

(dissoc m :a :c)

;;use associative abstraction with vectors

(def v [1 2 3])

(get v 1)

(get v 10)

(get v 10 "not-found")

(assoc v
  1 4
  0 -12
  2 :p)

(assoc v 3 10)
;;Sets

(get #{1 2 3} 2)

(get #{1 2 3} 4)

(get #{1 2 3} 4 "not-found")


;; contains predicate

(contains? [1 2 3] 0);;true when KEY is present, not value

(contains? {:a 5 :b 6} :b)

(contains? {:a 5 :b 6} 42)

(contains? #{1 2 3} 1)

;;also works for strings

(get "Clojure" 3)
;;Java hashmap
(contains? (java.util.HashMap.) "not-there")

(get (into-array [1 2 3]) 0)

;;Watch out for nil values

(get {:ethel nil} :lucy)
;;Find gives the key and the value
(find {:ethel nil} :ethel)

(if-let [e (find {:a 5 :b 6} :a)]
  (format "found %s => %s" (key e)(val e))
  "not found")

;;the de-structured version
(if-let [[k v](find {:a 5 :b 6} :a)]
  (format "found %s => %s" k v )
  "not found")
