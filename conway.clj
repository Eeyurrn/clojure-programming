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
     (>= x w) new-board
     (>= y h) (recur new-board (inc x) 0)
     :else
     (let [new-liveness
           (case (count-neighbours board [x y])
             2 (get-in board [x y])
             3 :on
             nil)]
       (recur (assoc-in new-board [x y] new-liveness) x (inc y)))))))

;;replace manual iteration

(defn indexed-step2
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce;;replace loop construct with reduce calls
     (fn [new-board x]
       (reduce
         (fn [new-board y]
          (let [new-liveness ;; associate the new liveness tp new-liveness var using the case,
                ;;2 nothing happens, 3 there is growth, anything else, DEATH!
                (case (count-neighbours board [x y])
                  2 (get-in board [x y])
                  3 :on
                  nil)]
            (assoc-in new-board [x y] new-liveness)))
       new-board (range h)))
     board (range w))))

;;Collapsed reductions

(defn indexed-step
  [board]
  (let [w (count board);;Grab the width and height
        h (count (first board))]
    (reduce
     (fn [new-board [x y]];;Collapse the 2 reduce calls by using the for function to generate the coordinate pairs
       (let [new-liveness
             (case (count-neighbours board [x y])
               2 (get-in board [x y])
               3 :on
               nil)]
        (assoc-in new-board [x y] new-liveness)))
     board (for [x (range h) y (range w)] [x y]);;generates the seq of [x y] to be processed by the board
     )))

(-> (iterate indexed-step glider) (nth 8) pprint)

(partition 3 1 (range 5))

(partition 3 1 (concat [nil] (range 5) [nil]))

(defn window
  "Return a lazy sequence of 3-item windows centered around each item of coll"
  [coll]
  (partition 3 1 (concat [nil] coll [nil])))

(defn cell-block
  "Creates a sequence of 3x3 windows from a triple of sequences."
  [[left mid right]]
  (window (map vector
               (or left (repeat nil)) mid (or right (repeat nil)))))


(defn window
  "Returns a lazy sequence of 3-item windows centered around
  each item of coll, padded as necessary with pad or nil"
  ([coll] (window nil coll))
   ([pad coll]
   (partition 3 1 (concat [pad] coll [pad]))))

(defn cell-block
  "Creates a sequence of 3x3 windows from a triple of sequences."
  [[left mid right]]
  (window (map vector left mid right)))

(defn liveness
  "Returns the liveness (nil or :on) of the center cell for the next step."
  [block]
  (let [[_ [_ center _] _] block]
    (case (- (count (filter #{:on} (apply concat block)))
            (if (= :on center) 1 0))
    2 center
    3 :on
    nil)))

(defn- step-row
  "Yields the next state of the center row."
  [rows-triple]
  (vec(map liveness (cell-block rows-triple))))

(defn index-free-step
  "Yields the next state of the board"
  [board]
  (vec (map step-row(window(repeat nil) board))))

(= (nth (iterate indexed-step glider) 8)
   (nth (iterate index-free-step glider) 8))


(defn step
  "Yields the next state of the world"
  [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
         :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))

(->> (iterate step #{[2 0][2 1][2 2][1 2][0 1]})
     drop 8
     first
     (populate (empty-board 6 6))
     pprint)