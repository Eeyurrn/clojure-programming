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

;;Indexed abstraction

 (nth [:a :b :c] 2)

 (get [:a :b :c] 2)

 (nth [:a :b :c] 3);;ArrayIndexOutOfBoundsException will occur, of course it does.

 (get [:a :b :c] 3);;gets a nil instead of exploding

 (nth [:a :b :c] -1);;ArrayIndexOutOfBoundsException again

 (get [:a :b :c]-1) ;; produces nil

 ;;behaviour is the same when provided a default value

 (nth [:a :b :c] -1 "not found")

 (get [:a :b :c] -1 "not found")

 ;;Stack Abstraction

 (conj '() 1)

 (conj '(2 1) 3)
 ;;gets the top value
 (peek '(3 2 1))
 ;;gets a collection with the top value removed
 (pop '(3 2 1))


 ;;now for vectors

 (conj [] 1)

 (conj [1 2 ]3)

 (peek [1 2 3])

 (pop [1 2 3])

 (pop [1])


 ;;Set Abstraction

 (get #{1 2 3} 2)

 (get #{1 2 3} 4)
 ;;Works with values
 (get #{1 2 3} 4 "not-found")

 ;;removing values from sets
 (disj #{1 2 3} 3 1)

 ;;Sorted Abstraction
 (def sm (sorted-map :z 5 :x 9 :y 0 :b 2 :a 3 :c 4))
 ;;sorted by key
 sm

 (rseq sm)
 ;;first use of subseq using 3 args
 (subseq sm <= :c)
 ;;second use of subseq using 5 args
 (subseq sm > :b <= :y)

 ;;Compare
 (compare 2 2)

 (compare "ab" "abc")

 (compare ["a" "b" "c"] ["a" "b"])

 (compare ["a" 2]["a" 2 0])

 (sort < (repeatedly 10 #(rand-int 100)))

 (sort-by first > (map-indexed vector "Clojure"))

 (sorted-map-by compare :z 5 :x 9 :y 0 :b 2 :a 3)
 ;;composing the - negative and compare inverts the integer
 (sorted-map-by (comp - compare) :z 5 :x 9 :y 0 :b 2 :a 3)

 (defn magnitude
   [x]
   (-> x Math/log10 Math/floor))

 (magnitude 100)

 (defn compare-magnitude
   [a b]
   (neg? (- (magnitude a) (magnitude b))))

((comparator compare-magnitude) 10 10000)

((comparator compare-magnitude) 100 10)
;;same order of magnitude
((comparator compare-magnitude) 10 75)

(def mag-set(sorted-set-by compare-magnitude 10 1000 500))
;;does not add to the set because set already has something with the same magnitude.
(conj mag-set 600)
;;removes the member with the same magnitude due to the set's comparative nature
(disj mag-set 750)
;;contains predicate works based on the set's comparator
(contains? mag-set 1239)

;;New compare magnitude
(defn compare-magnitude
   [a b]
   (let[diff (- (magnitude a) (magnitude b))]
     (if(zero? diff)
     (compare a b)
     diff)))

(def ss (sorted-set-by compare-magnitude 10 500 1000 670 1239))

(subseq ss > 500)

(subseq ss > 500 <= 1000)

(rsubseq ss > 500 <= 1000)


;;implement linear interpolation function

(defn interpolate
  "Takes a collection of points (as [x y] tuples), returning a
  function which is a linear interpolation between those points"
  [points]
  (let [results (into (sorted-map) (map vec points))]
    (fn [x];;returning a function
      (let [[xa ya](first (rsubseq results <= x))
            [xb yb](first (subseq results > x))]
        (if (and xa xb);;case with xa xb are not nil
          (/(+(* ya (- xb x))(* yb (- x xa)))
            (- xb xa))
          (or ya yb))))))

