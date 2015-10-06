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