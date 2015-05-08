(ns clanhr.validators.errors
  "Utilities for operating on errors")

(defn- stringify
  "Transforms in string an array of properties"
  [array]
  (->> (map #(name %) array)
       (clojure.string/join ".")))

(defn make-errors-friendly
  "Transforms user errors in a more friendly format"
  [data]
  (reduce (fn [errors [k v]]
            (assoc errors (stringify k) v)) {} data))

(defn make-errors-friendly-in
  "Transforms property names, if arrays, in strings"
  [data field]
  (-> data
      (assoc field (make-errors-friendly (field data)))))

