(ns clanhr.validators.core-test
  (use clojure.test)
  (require [clanhr.validators.core :as validate]))

(deftest valid-date-test
  (is (validate/valid-date? "2015-03-03T00:00:00.000Z"))
  (is (not (validate/valid-date? "")))
  (is (not (validate/valid-date? nil)))
  (is (not (validate/valid-date? "hello"))))

(deftest date-validator-test
  (let [validator (validate/date-validator :date)]
    (is (first (validator {:date "2015-03-03T00:00:00.000Z"})))
    (is (not (first (validator {:date ""}))))))

(deftest email-validator-test
  (let [validator (validate/email-validator :email)]
    (is (first (validator {:email "donbonifacio@gmail.com"})))
    (is (not (first (validator {:email "hello"}))))))
