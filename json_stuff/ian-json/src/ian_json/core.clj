
(ns ian-json.core
  (:require [clojure.data.json :as json])
  (:require [clojure.walk :as walk])
  (:gen-class))


(defn json-to-map
  [json-file]
(let
  [x (json/read-str (slurp json-file))]
 (walk/keywordize-keys x )))

(defn get-key-json
  [args]
  (let[[json-file & ks] args]
    (get-in (json-to-map json-file)(map keyword ks))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (get-key-json args))

(-main "ian.json" "loves")


