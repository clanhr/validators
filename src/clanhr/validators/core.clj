(ns clanhr.validators.core
  "Utility validators that are compatible with validateur"
  (:import org.joda.time.DateTime)
  (:require [clj-time.coerce :as c]
            [clj-time.core :as t]
            [validateur.validation :refer :all]
            [email-validator.core :as email-validator]))

(defn valid-date?
  "Verifies if a date is valid"
  [date]
  (or (instance? org.joda.time.DateTime date)
      (instance? java.util.Date date)
      (and (string? date) (c/to-date date))))

(defn cljtime
  "Gets someting and returns a clj time protocol imp"
  [obj]
  (cond
    (instance? org.joda.time.DateTime obj) obj
    (instance? java.util.Date obj) (c/from-date obj)
    (string? obj) (c/from-date (c/to-date obj))))


(defn valid-date-options?
  "Checks for specific date options and tests them"
  [value options]
  (cond
    (:lte options) (boolean (or (t/before? (cljtime value) (cljtime (:lte options)))
                                (t/equal? (cljtime value) (cljtime (:lte options)))))
    :else true))

(defn date-validator
  "Validates dates"
  [field & options]
  (fn [data]
    (let [value (field data)
          empty-value? (nil? value)]
      (if (or empty-value? (and (valid-date? value)
                                (valid-date-options? value (first options))))
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
  (re-matches #"\d+([.,]\d+)?" raw))

(defn convert-currency
  "Converts a string to currency"
  [raw]
  (when (valid-currency? raw)
    (let [raw (clojure.string/replace raw #"," ".")]
      (Double. raw))))

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
