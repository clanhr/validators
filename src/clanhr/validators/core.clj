(ns clanhr.validators.core
  "Utility validators that are compatible with validateur"
  (require [clj-time.coerce :as c]))

(defn valid-date?
  "Verifies if a date is valid"
  [date]
  (and (string? date) (c/to-date date)))

(defn date-validator
  "Validates dates"
  [field]
  (fn [data]
    (let [value (field data)
          empty-value? (nil? value)]
      (if (or empty-value? (valid-date? value))
        [true {}]
        [false {field #{"invalid date"}}]))))
