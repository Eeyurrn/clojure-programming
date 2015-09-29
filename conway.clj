(defn empty-board
  "Creates a rectangular empty board of the specified width and height"
  [w h]
  (vec (repeat w (vec (repeat h nil)))))

(defn populate
  "Turns :on each of the cells specified as [y,x] coordinates."
  [board living-cells]
  (reduce (fn [board coordinates]
            (assoc-in board coordinates :on))
          board
          living-cells))

(def glider (populate (empty-board 6 6) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(pprint glider)

(defn neighbours ;;gets the neighbour cell for a set of coordinates
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
    [(+ dx x)(+ dy y)]))



(defn count-neighbours
  [board loc]
  (count (filter #(get-in board %)(neighbours loc))))

(defn indexed-step
  "Yields the next state of the board, using indices to determine neighbours, liveness, etc."
  [board]
  (let [w (count board)
        h (count (first board))]
   (loop [new-board board x 0 y 0]
    (cond
     (>= x w) new-board))))