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