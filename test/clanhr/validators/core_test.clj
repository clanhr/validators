(ns clanhr.validators.core-test
  (use clojure.test)
  (require [clanhr.validators.core :as validate]))

(deftest valid-date
  (is (validate/valid-date? "2015-03-03T00:00:00.000Z"))
  (is (not (validate/valid-date? "")))
  (is (not (validate/valid-date? nil)))
  (is (not (validate/valid-date? "hello"))))

(deftest date-validator
  (let [validator (validate/date-validator :date)]
    (is (first (validator {:date "2015-03-03T00:00:00.000Z"})))
    (is (not (first (validator {:date ""}))))))
