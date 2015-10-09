;;Destructuring

(def v [42 "foo" 99.2 [5 12]])

;;first item in the collection
(first v)
;;Second
(second v)
;;last
(last v)
;;nth
(nth v 2)
;;vector as a function which takes the index
(v 2)
;;Invocation of the List interface, all clojure seq collections implement this
(.get v 2)

;;Sequential destructuring


(let [[x y z] v] ;; passing [x y z] causes the vector to be destructured in sequence, first x then y then z
  (+ x z))

;;access nested vector
(let [[x _ _ [y z]] v];;drop into the vector with y and z bindings
  (+ x y z))
;;variable arguments
(let [[x & rest] v];; use the & character to define the varargs at the end to the rest form
  rest)

;;retaining the original value using :as

(let [[x _ z :as original-vector] v]
  (conj original-vector (+ x z)))

;;Map destructuring

(def m {:a 5 :b 6
        :c [7 8 9]
        :d {:e 10 :f 11}
        "foo" 88
        42 false})

;;visually mirror the input map
(let [{a :a b :b} m]
  (+ a b))

;;any value may be used for lookup, using "foo"
(let [{f "foo"} m]
  (+ f 12))

;;same with 42
(let [{v 42} m]
  (if v 1 0))

;;can use the map destructuring on a vector due to the associative abstraction
;; treats vector like a map with integers as keys
(let [{x 3 y 8}[12 0 0 -18 44 6 0 0 1]]
  (+ x y))


(let [{{e :e} :d} m];; :d extracts to the map which further extracts :e to bind with e
  (* 2 e))

(let [{[x _ y] :c } m];;referencing the internal vector inside the map
  (+ x y))

(def map-in-vector ["James" {:birthday (java.util.Date. 73 1 6)}])

(let [[name {bd :birthday}] map-in-vector];;references vector and then references the inner map
  (str name " was born on " bd))

(let [{r1 :x r2 :y :as randoms}
      (zipmap [:x :y :z] (repeatedly (partial rand-int 10)))]
  (assoc randoms :sum (+ r1 r2)));; assigns :x :y :z to values which are randomly generated

;;default values using the :or

(let [{k :unknown x :a
       :or {k 50}} m];; the :or value is a map with the defaults
  (+ k x))

(let [{opt1 :option} {:option false}
      opt1 (or opt1 true)
      {opt2 :option :or {opt2 true}}{:option false}]
  {:opt1 opt1 :opt2 opt2})


;;Binding values to keys names
(def chas {:name "Chas" :age 31 :location "Massachusetts"})
;;Verbose version
(let [{name :name age :age location :location} chas]
  (format "%s is %s years old and lives in %s." name age location))

;;use the :keys
(let [{:keys [name age location]} chas];; {:keys followed by vector of symbols with the same names as the keywords to map to the pre-specified symbols}
  (format "%s is %s years old and lives in %s." name age location))