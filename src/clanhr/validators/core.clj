(ns clanhr.validators.core
  "Utility validators that are compatible with validateur"
  (import org.joda.time.DateTime)
  (require [clj-time.coerce :as c]
           [clj-time.core :as t]
           [validateur.validation :refer :all]
           [email-validator.core :as email-validator]))

(defn valid-date?
  "Verifies if a date is valid"
  [date]
  (or (instance? org.joda.time.DateTime date)
      (instance? java.util.Date date)
      (and (string? date) (c/to-date date))))

(defn date-validator
  "Validates dates"
  [field]
  (fn [data]
    (let [value (field data)
          empty-value? (nil? value)]
      (if (or empty-value? (valid-date? value))
        [true {}]
        [false {field #{"invalid date"}}]))))

(defn valid-email?
  "Verifies that en email is valid"
  [email]
  (email-validator/is-email? email))

(defn email-validator
  "Validates email format"
  [field]
  (fn [data]
    (let [email (field data)
          empty-email? (empty? email)]
      (if (or empty-email? (valid-email? (field data)))
        [true {}]
        [false {field #{"invalid email format"}}]))))

(defn valid-currency?
  "Checks if a currency is valid"
  [raw]
  (re-find #"\d+(.\d+)?" raw))

(defn currency-validator
  "Validates currency format"
  [field]
  (fn [data]
    (let [value (field data)]
      (if (or (number? value) (empty? value) (valid-currency? (field data)))
        [true {}]
        [false {field #{"invalid currency"}}]))))


(defn presence-of-if
  "Runs a presence-of validator if the given function passes"
  [field if-fn]
  (fn [data]
    (if (if-fn data)
      ((presence-of field) data)
      [true {}])))
